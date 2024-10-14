package com.service.voting.tps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.service.voting.common.exception.ResourceNotFoundException;
import com.service.voting.common.reuse.Filter;
import com.service.voting.tps.dto.req.TPSAreaReq;
import com.service.voting.tps.dto.req.TPSReq;
import com.service.voting.tps.dto.res.TPSAreaRes;
import com.service.voting.tps.dto.res.TPSRes;
import com.service.voting.tps.filter.TPSFIlter;
import com.service.voting.tps.model.TPS;
import com.service.voting.tps.model.TPSArea;
import com.service.voting.tps.repository.TPSRepository;
import com.service.voting.tps.repository.PageTPSRepository;
import com.service.voting.tps.repository.TPSAreaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TPSService {

    @Autowired
    private TPSRepository tpsRepo;

    @Autowired
    private TPSAreaRepository tpsAreaRepo;

    @Autowired
    private PageTPSRepository pageTpsRepo;

    @Autowired
    private TPSFIlter tpsFilter;

    private final Filter<TPS> filter = new Filter<>();

    public List<TPSRes> getTPSs(String search, String provinceCode, String cityCode, String districtCode) {
        Specification<TPS> spec = Specification.where(tpsFilter.byProvinceCode(provinceCode))
                .and(tpsFilter.byCityCode(cityCode))
                .and(tpsFilter.byDistrictCode(districtCode))
                .and(filter.isNotDeleted())
                .and(filter.orderByIdDesc()); // Menambahkan filter untuk tidak menampilkan TPS yang dihapus
        List<TPS> tpsList = tpsRepo.findAll(spec);
        return tpsList.stream().map(this::convertToTPSRes).toList();
    }

    public Page<TPSRes> getPagTPSs(String search, Pageable pageable, String provinceCode, String cityCode,
            String districtCode) {
        Specification<TPS> spec = Specification.where(tpsFilter.byProvinceCode(provinceCode))
                .and(tpsFilter.byCityCode(cityCode))
                .and(tpsFilter.byDistrictCode(districtCode))
                .and(filter.isNotDeleted())
                .and(filter.orderByIdDesc()); // Menambahkan filter untuk tidak menampilkan TPS yang dihapus
        Page<TPS> tpsPage = pageTpsRepo.findAll(spec, pageable);
        return tpsPage.map(this::convertToTPSRes);
    }

    @Transactional
    public TPS createTPS(TPSReq tpsReq) {
        TPSArea tpsArea = updateOrCreateTPSArea(null, tpsReq.getTpsArea());

        TPS tps = new TPS();
        tps.setName(tpsReq.getName());
        tps.setKpu(tpsReq.getKpu());
        tps.setTpsArea(tpsArea); 
        return tpsRepo.save(tps);
    }

    @Transactional
    public TPS updateTPS(Long id, TPSReq tpsReq) {
        TPS tps = getTPS(id);
        tps.setName(tpsReq.getName());
        tps.setKpu(tpsReq.getKpu());

        updateOrCreateTPSArea(tps.getTpsArea().getId(), tpsReq.getTpsArea());

        return tpsRepo.save(tps);
    }

    @Transactional
    public void deleteTPS(Long id) {
        TPS tps = getTPS(id);
        tps.setDeleted(true);
        tpsRepo.save(tps);
    }

    private TPSRes convertToTPSRes(TPS tps) {
        return TPSRes.builder()
                .name(tps.getName())
                .kpu(tps.getKpu())
                .tpsArea(new TPSAreaRes(tps.getTpsArea().getProvinceCode(),
                        tps.getTpsArea().getProvinceName(),
                        tps.getTpsArea().getCityCode(),
                        tps.getTpsArea().getCityName(),
                        tps.getTpsArea().getDistrictCode(),
                        tps.getTpsArea().getDistrictName()))
                .build();
    }

    public TPS getTPS(Long id) {
        Optional<TPS> optionalTPS = tpsRepo.findById(id);
        return optionalTPS.orElseThrow(() -> new ResourceNotFoundException("TPS not found"));
    }

    private TPSArea updateOrCreateTPSArea(Long id, TPSAreaReq tpsAreaReq) {
        TPSArea tpsArea;
        if (id != null) {
            tpsArea = getTPSAreaById(id);
        } else {
            tpsArea = new TPSArea();
        }
        tpsArea.setProvinceCode(tpsAreaReq.getProvinceCode());
        tpsArea.setProvinceName(tpsAreaReq.getProvinceName());
        tpsArea.setCityCode(tpsAreaReq.getCityCode());
        tpsArea.setCityName(tpsAreaReq.getCityName());
        tpsArea.setDistrictCode(tpsAreaReq.getDistrictCode());
        tpsArea.setDistrictName(tpsAreaReq.getDistrictName());
        return tpsAreaRepo.save(tpsArea);
    }

    public TPSArea getTPSAreaById(Long id) {
        Optional<TPSArea> optionalTPSArea = tpsAreaRepo.findById(id);
        return optionalTPSArea.orElseThrow(() -> new ResourceNotFoundException("TPS Area not found"));
    }
}