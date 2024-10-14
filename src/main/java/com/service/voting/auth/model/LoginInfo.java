package com.service.voting.auth.model;

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

import java.time.LocalDateTime;

@Entity
@Table(name = "login_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "login_date")
    private LocalDateTime loginDate;

    @Column(name = "location")
    private String location;

    @Column(name = "user_name")
    private String userName;
}