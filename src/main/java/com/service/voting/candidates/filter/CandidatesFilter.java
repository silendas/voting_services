package com.service.voting.candidates.filter;

import org.springframework.data.jpa.domain.Specification;

import com.service.voting.candidates.model.Candidates;
import com.service.voting.common.enums.VotingCategory;

public class CandidatesFilter {

    public Specification<Candidates> byCandidateOrOrganization(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value != null) {
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("candidate")), "%" + value.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("organization")), "%" + value.toLowerCase() + "%")
                );
            }
            return null;
        };
    }

    public Specification<Candidates> byCategory(VotingCategory category) {
        return (root, query, criteriaBuilder) -> {
            if (category != null) {
                return criteriaBuilder.equal(root.get("category"), category);
            }
            return null;
        };
    }
    
}
