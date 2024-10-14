package com.service.voting.voting.models;

import com.service.voting.candidates.model.Candidates; // Import entitas Candidates
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vote_candidates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteCandidate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidates candidate;

    @Column(name = "vote_count", nullable = false)
    private Integer voteCount;

}