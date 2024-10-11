package com.service.voting.common.reuse;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class Filter<T> {

    public Specification<T> orderByIdAsc() {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.conjunction();
        };
    }

    public Specification<T> orderByIdDesc() {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.conjunction();
        };
    }

    public Specification<T> isNotDeleted() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.notEqual(root.get("isDeleted"), true);
        };
    }

    public Specification<T> byColumn(String column, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value != null) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(column)), "%" + value.toLowerCase() + "%");
            }
            return null;
        };
    }

    public Specification<T> createdAtBetween(Date start, Date end) {
        return (root, query, criteriaBuilder) -> {
            if (start != null && end != null) {

                Calendar cal = Calendar.getInstance();
                cal.setTime(end);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                Date endOfDay = cal.getTime();

                return criteriaBuilder.between(root.get("createdAt"), start, endOfDay);
            } else if (start != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), start);
            } else if (end != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), end);
            }
            return null;
        };
    }
}
