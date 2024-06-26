package com.xiaolin.esplus.core.es;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

public class IsNullQueryConditionStrategy implements EsQueryCondition {
    private static volatile EsQueryCondition singleton;

    private IsNullQueryConditionStrategy(){}

    public static EsQueryCondition getInstance() {
        if (singleton == null) {
            synchronized (IsNullQueryConditionStrategy.class) {
                if (singleton == null) {
                    singleton = new IsNullQueryConditionStrategy();
                }
            }
        }
        return singleton;
    }

    @Override
    public Object execute(Query.Builder queryBuilder, String fieldName, String keyword, Float boost, Object... values) {
        queryBuilder
                .bool(bb -> bb
                        .must(mb -> mb
                                .exists(eb -> eb
                                        .field(fieldName)
                                ))
                        .mustNot(mnb -> mnb
                                .wildcard(wb -> wb
                                        .field(fieldName)
                                        .wildcard("*")))
                        .boost(boost));
        return null;
    }


}
