package com.acheh.demo.supercook.api.util;

import com.acheh.demo.supercook.api.util.search.SpecSearchCriteria;
import com.acheh.demo.supercook.api.util.search.SearchOperation;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SearchCriteriaParser {

    private static final Pattern SEARCH_CRITERIA_PATTERN = Pattern.compile("(.*)([:!><*~])(.*)");
    private static final Map<String, Operator> SEARCH_OPERATIONS
            = Map.of("AND", Operator.AND, "OR", Operator.OR, "or", Operator.OR, "and", Operator.AND);

    private enum Operator {
        OR(1), AND(2);
        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    private SearchCriteriaParser() {
        // util class
    }

    public static Deque<Object> parse(String search) {
        Deque<Object> output = new LinkedList<>();
        Deque<String> stack = new LinkedList<>();
        for (String token : search.split("\\s+")) {
            if (SEARCH_OPERATIONS.containsKey(token)) {
                parseAndOrOperators(token, output, stack);
            } else if (token.equals(SearchOperation.LEFT_PARANTHESIS)) {
                stack.push(SearchOperation.LEFT_PARANTHESIS);
            } else if (token.equals(SearchOperation.RIGHT_PARANTHESIS)) {
                while (!SearchOperation.LEFT_PARANTHESIS.equals(stack.peek())) {
                    output.push(stack.pop());
                }
                stack.pop();
            } else {
                Matcher matcher = SEARCH_CRITERIA_PATTERN.matcher(token);
                while (matcher.find()) {
                    output.push(new SpecSearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        while (!stack.isEmpty())
            output.push(stack.pop());

        return output;
    }

    /**
     * Parse and or operators
     *
     * @param token  token
     * @param output output
     * @param stack  stack
     */
    private static void parseAndOrOperators(String token, Deque<Object> output, Deque<String> stack) {
        while (!stack.isEmpty() && isHigherPrecedenceOperator(token, stack.peek())) {
            if (stack.pop().equalsIgnoreCase(SearchOperation.OR_OPERATOR)) {
                output.push(SearchOperation.OR_OPERATOR);
            } else {
                output.push(SearchOperation.AND_OPERATOR);
            }
        }
        if (token.equalsIgnoreCase(SearchOperation.OR_OPERATOR)) {
            stack.push(SearchOperation.OR_OPERATOR);
        } else {
            stack.push(SearchOperation.AND_OPERATOR);
        }
    }

    /**
     * Check if current operator has higher precedence than previous operator
     *
     * @param currOp current operator
     * @param prevOp previous operator
     * @return true if current operator has higher precedence than previous operator
     */
    private static boolean isHigherPrecedenceOperator(String currOp, String prevOp) {
        return (SEARCH_OPERATIONS.containsKey(prevOp) && SEARCH_OPERATIONS.get(prevOp).precedence >= SEARCH_OPERATIONS.get(currOp).precedence);
    }

}
