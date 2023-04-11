package com.acheh.demo.supercook.api.util.search.specification;

import com.acheh.demo.supercook.api.exception.BadRequestException;
import com.acheh.demo.supercook.api.util.SearchCriteriaParser;
import com.acheh.demo.supercook.api.util.search.SpecSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Deque;
import java.util.function.Function;

public class GenericSpecification<E> implements Specification<E> {

    protected final SpecSearchCriteria criteria;

    public GenericSpecification(SpecSearchCriteria criteria) {
        this.criteria = criteria;
    }

    public static <T> Specification<T> fromSearchParams(String searchParams) {
        if (StringUtils.isNotBlank(searchParams)) {
            GenericSpecificationsBuilder<T> specBuilder = new GenericSpecificationsBuilder<>();
            try {
                Deque<Object> parsedSearchCriteria = SearchCriteriaParser.parse(searchParams);
                return specBuilder.build(parsedSearchCriteria, GenericSpecification::new);
            } catch (Exception exp) {
                throw new BadRequestException(exp);
            }
        }
        return Specification.where(null);
    }

    public static <T> Specification<T> fromSearchParams(String searchParams, Function<SpecSearchCriteria, Specification<T>> function) {
        if (StringUtils.isNotBlank(searchParams)) {
            GenericSpecificationsBuilder<T> specBuilder = new GenericSpecificationsBuilder<>();
            try {
                Deque<Object> parsedSearchCriteria = SearchCriteriaParser.parse(searchParams);
                return specBuilder.build(parsedSearchCriteria, function);
            } catch (Exception exp) {
                throw new BadRequestException(exp);
            }
        }
        return Specification.where(null);
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate;
        try {
            predicate = buildPredicate(getPath(root, this.criteria.getKey()), cb);
        } catch (Exception exp) {
            throw new BadRequestException(exp);
        }
        return this.criteria.isNegation() ? cb.not(predicate) : predicate;
    }

    protected Predicate buildPredicate(Path path, CriteriaBuilder cb) {
        switch (this.criteria.getOperation()) {
            case EQUALITY:
                return cb.equal(path, this.criteria.getValue());
            case GREATER_THAN:
                return cb.greaterThan(path, this.criteria.getValue());
            case LESS_THAN:
                return cb.lessThan(path, criteria.getValue());
            case LIKE:
                return cb.like(path, "%" + criteria.getValue() + "%");
            default:
                throw new BadRequestException(String.format("Unsupported search operation in key: %s not supported", criteria.getKey()));
        }
    }

    private static <T> Path<T> getPath(final Path<?> base, final String path) {
        final Path<T> result;
        final int index = path.indexOf('.');
        if (index == -1) {
            result = base.get(path);
        } else {
            final String part = path.substring(0, index);
            final String rest = path.substring(index + 1);

            final Path<?> partPath = base.get(part);
            if (partPath.getModel() != null || partPath instanceof PluralAttributePath) {
                final Join<?, ?> join = ((From<?, ?>) base).join(part);
                result = getPath(join, rest);
            } else {
                result = getPath(partPath, rest);
            }
        }
        return result;
    }

}
