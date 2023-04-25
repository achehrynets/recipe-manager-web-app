package com.acheh.demo.supercook.api.util.search.specification;

import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

/**
 * Specification for {@link Recipe}.
 */
public final class RecipeSpecification {

    /**
     * Utility class.
     */
    private RecipeSpecification() {
        // utility class
    }

    /**
     * Find recipes that contain all the given ingredients.
     * @param instructions list of instructions
     * @return the specification
     */
    public static Specification<Recipe> instructionsLike(List<String> instructions) {
        return (root, query, cb) -> {
            if (instructions == null || instructions.isEmpty()) {
                return cb.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Instruction> subRoot = subquery.from(Instruction.class);
            subquery.select(subRoot.get("id"));
            predicates.add(cb.equal(root.get("id"), subRoot.get("recipeId")));
            for (String instruction : instructions) {
                predicates.add(cb.like(subRoot.get("description"), "%" + instruction + "%"));
            }
            return cb.exists(subquery.where(predicates.toArray(new Predicate[0])));
        };
    }

    /**
     * Find recipes that contain all the given categories.
     * @param categoryIds list of categories
     * @return the specification
     */
    public static Specification<Recipe> hasCategories(List<Integer> categoryIds) {
        return (root, query, cb) -> {
            if (categoryIds == null || categoryIds.isEmpty()) {
                return cb.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            for (Integer categoryId : categoryIds) {
                Subquery<Integer> subquery = query.subquery(Integer.class);
                subquery.distinct(true);
                Root<Recipe> subqueryRecipeRoot = subquery.from(Recipe.class);
                Join<Object, Object> join = subqueryRecipeRoot.join("categories");
                subquery.select(subqueryRecipeRoot.get("id")).where(predicates.toArray(new Predicate[0]));
                predicates.add(cb.exists(subquery.where(
                        cb.equal(root.get("id"), subqueryRecipeRoot.get("id")),
                        cb.equal(join.get("id"), categoryId)
                )));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Find recipes that contain all the given ingredients and do not contain any of the excluded ingredients.
     *
     * @param includeIngredients the ingredients to search for
     * @param excludeIngredients the ingredients to exclude
     * @return the specification
     */
    public static Specification<Recipe> includeOrExcludeIngredients(List<Integer> includeIngredients, List<Integer> excludeIngredients) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (includeIngredients != null && !includeIngredients.isEmpty()) {
                predicates.add(root.in(createSubqueryForIngredients(query, cb, includeIngredients, true)));
            }
            if (excludeIngredients != null && !excludeIngredients.isEmpty()) {
                predicates.add(cb.not(root.in(createSubqueryForIngredients(query, cb, excludeIngredients, false))));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }

    /**
     * Create a subquery to find recipes that contain all the given ingredients.
     *
     * @param query the query
     * @param cb the criteria builder
     * @param ingredients the ingredients to search for
     * @return the subquery
     */
    private static Subquery<Integer> createSubqueryForIngredients(CriteriaQuery<?> query,
                                                                  CriteriaBuilder cb, List<Integer> ingredients,
                                                                  boolean include) {
        long ingredientsSize = include ? Long.valueOf(ingredients.size()) : 1L;
        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<RecipeIngredient> subRoot = subquery.from(RecipeIngredient.class);
        return subquery.select(subRoot.get("recipeId"))
                .where(subRoot.get("ingredientId").in(ingredients))
                .groupBy(subRoot.get("recipeId"))
                .having(cb.greaterThanOrEqualTo(cb.count(subRoot.get("recipeId")), ingredientsSize));
    }


}
