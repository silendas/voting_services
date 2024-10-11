package com.service.voting.users.models;

import com.service.voting.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "registrations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Registrations extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    private String name;

    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    private boolean isRegistered;

}