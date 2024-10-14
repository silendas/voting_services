package com.service.voting.candidates.dto.res;

import com.service.voting.common.enums.VotingCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidatesRes {

    private String candidate;

    private VotingCategory category;

    private String organization;

    private String picture;

}
