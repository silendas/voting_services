package com.service.voting.tps.model;

import com.service.voting.common.model.BaseEntity;

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
@Table(name = "tps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TPS extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "kpu")
    private String kpu;

    @ManyToOne
    @JoinColumn(name = "tps_area_id", nullable = false)
    private TPSArea tpsArea;

}