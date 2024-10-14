package com.service.voting.users.filter;

import org.springframework.data.jpa.domain.Specification;

import com.service.voting.users.model.User;

import jakarta.persistence.criteria.Predicate;

public class UserFilter {

    public Specification<User> byNameOrEmail(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value != null) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + value.toLowerCase() + "%");
                Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + value.toLowerCase() + "%");
                return criteriaBuilder.or(namePredicate, emailPredicate);
            }
            return null;
        };
    }

    public Specification<User> notEqualId(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id != null) {
                return criteriaBuilder.notEqual(root.get("id"), id);
            }
            return null;
        };
    }
    
}
