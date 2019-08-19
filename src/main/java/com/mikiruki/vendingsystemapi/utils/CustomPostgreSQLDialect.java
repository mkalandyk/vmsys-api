package com.mikiruki.vendingsystemapi.utils;

import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomPostgreSQLDialect extends PostgresPlusDialect {

    public CustomPostgreSQLDialect() {
        super();
        registerFunction("string_agg", new StandardSQLFunction("string_agg", StandardBasicTypes.STRING));
    }
}
