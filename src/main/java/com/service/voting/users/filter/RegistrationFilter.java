package com.service.voting.users.filter;

import org.springframework.data.jpa.domain.Specification;

import com.service.voting.users.models.Registrations;

import jakarta.persistence.criteria.Predicate;

public class RegistrationFilter {

    public Specification<Registrations> byNameOrEmail(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value != null) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + value.toLowerCase() + "%");
                Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        "%" + value.toLowerCase() + "%");
                return criteriaBuilder.or(namePredicate, emailPredicate);
            }
            return null;
        };
    }

}
