package com.xiaolin.esplus.core.es;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

public class IsNotNullQueryConditionStrategy implements EsQueryCondition {
    private static volatile EsQueryCondition singleton;

    private IsNotNullQueryConditionStrategy(){}

    public static EsQueryCondition getInstance() {
        if (singleton == null) {
            synchronized (IsNotNullQueryConditionStrategy.class) {
                if (singleton == null) {
                    singleton = new IsNotNullQueryConditionStrategy();
                }
            }
        }
        return singleton;
    }

    @Override
    public Object execute(Query.Builder queryBuilder, String fieldName, String keyword, Float boost, Object... values) {
        queryBuilder
                .wildcard(wb -> wb
                        .field(fieldName)
                        .wildcard("*")
                        .boost(boost));
        return null;
    }


}
