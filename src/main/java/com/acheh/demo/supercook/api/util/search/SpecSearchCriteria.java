package com.acheh.demo.supercook.api.util.search;

public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private String value;

    private boolean negation;

    public SpecSearchCriteria(String key, String operation, String value) {
        if (key.startsWith("!")) {
            this.negation = true;
            this.key = key.substring(1);
        } else {
            this.negation = false;
            this.key = key;
        }
        this.operation = SearchOperation.getSearchOperation(operation.charAt(0));
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isNegation() {
        return negation;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

}
