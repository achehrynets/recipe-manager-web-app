package com.acheh.demo.supercook.api.util.search.specification;

import com.acheh.demo.supercook.api.util.search.SearchOperation;
import com.acheh.demo.supercook.api.util.search.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;


/**
 * Generic specification builder class which helps to build specification from parsed search criteria
 * @param <E>
 */
class GenericSpecificationsBuilder<E> {

    Specification<E> build(Deque<?> postFixedExprStack, Function<SpecSearchCriteria, Specification<E>> converter) {
        Deque<Specification<E>> specStack = new LinkedList<>();

        Collections.reverse((List<?>) postFixedExprStack);

        while (!postFixedExprStack.isEmpty()) {
            Object mayBeOperand = postFixedExprStack.pop();

            if (!(mayBeOperand instanceof String)) {
                specStack.push(converter.apply((SpecSearchCriteria) mayBeOperand));
            } else {
                Specification<E> operand1 = specStack.pop();
                Specification<E> operand2 = specStack.pop();
                if (mayBeOperand.equals(SearchOperation.AND_OPERATOR))
                    specStack.push(Specification.where(operand1.and(operand2)));
                else if (mayBeOperand.equals(SearchOperation.OR_OPERATOR))
                    specStack.push(Specification.where(operand1.or(operand2)));
            }

        }
        return specStack.pop();
    }

}
