package com.service.voting.tps.filter;

import org.springframework.data.jpa.domain.Specification;

import com.service.voting.tps.model.TPS;

public class TPSFIlter {
    
       public Specification<TPS> byProvinceCode(String provinceCode) {
        return (root, query, criteriaBuilder) -> {
            if (provinceCode != null) {
                return criteriaBuilder.equal(root.get("tpsArea").get("provinceCode"), provinceCode);
            }
            return null;
        };
    }

    public Specification<TPS> byCityCode(String cityCode) {
        return (root, query, criteriaBuilder) -> {
            if (cityCode != null) {
                return criteriaBuilder.equal(root.get("tpsArea").get("cityCode"), cityCode);
            }
            return null;
        };
    }

    public Specification<TPS> byDistrictCode(String districtCode) {
        return (root, query, criteriaBuilder) -> {
            if (districtCode != null) {
                return criteriaBuilder.equal(root.get("tpsArea").get("districtCode"), districtCode);
            }
            return null;
        };
    }

}
