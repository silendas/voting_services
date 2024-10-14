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
public class TPSAreaReq {
    @NotBlank(message = "Province code is required")
    private String provinceCode;
    @NotBlank(message = "Province name is required")
    private String provinceName;
    @NotBlank(message = "City code is required")
    private String cityCode;
    @NotBlank(message = "City name is required")
    private String cityName;
    @NotBlank(message = "District code is required")
    private String districtCode;
    @NotBlank(message = "District name is required")
    private String districtName;
}