package com.xiaolin.esplus.utils;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;

public class LambdaUtil {
    public static String getFieldName(Serializable column) {
        if (column instanceof Proxy) {
            MethodHandle dmh = MethodHandleProxies.wrapperInstanceTarget(column);
            Executable executable = MethodHandles.reflectAs(Executable.class, dmh);
            return methodToProperty(executable.getName());
        } else {
            try {
                Method method = column.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(true);
                return methodToProperty(((SerializedLambda) method.invoke(column)).getImplMethodName());
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            if (!name.startsWith("get") && !name.startsWith("set")) {
                throw new RuntimeException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }
            name = name.substring(3);
        }
        return EsToolsUtil.getFirstLowerCaseString(name);
    }

}
