package com.acheh.demo.supercook.api.util.search;

public enum SearchOperation {

    EQUALITY, GREATER_THAN, LESS_THAN, LIKE;

    public static final String OR_OPERATOR = "OR";

    public static final String AND_OPERATOR = "AND";

    public static final String LEFT_PARANTHESIS = "(";

    public static final String RIGHT_PARANTHESIS = ")";

    public static SearchOperation getSearchOperation(final char input) {
        switch (input) {
            case ':':
                return EQUALITY;
            case '>':
                return GREATER_THAN;
            case '<':
                return LESS_THAN;
            case '*':
                return LIKE;
            default:
                return null;
        }
    }

}
