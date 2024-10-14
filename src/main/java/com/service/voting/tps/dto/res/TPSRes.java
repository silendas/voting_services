package com.service.voting.tps.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TPSRes {
    private String name;
    private String kpu;
    private TPSAreaRes tpsArea;
}