package com.service.voting.tps.dto.req;


import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TPSReq {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "KPU is required")
    private String kpu;

    @NotBlank(message = "TPS Area is required")
    private TPSAreaReq tpsArea;

}
