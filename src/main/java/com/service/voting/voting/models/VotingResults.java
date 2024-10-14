package com.service.voting.voting.models;

import com.service.voting.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voting_results")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotingResults extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // ID pengguna yang memberikan suara

    private Long electionId; // ID pemilihan

    private boolean vote; // Status suara (true/false)

    @Column(columnDefinition = "TEXT")
    private String comments; // Komentar tambahan

    // Tambahkan metode lain jika diperlukan
}