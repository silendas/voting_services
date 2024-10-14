package com.service.voting.tps.controller;

import com.service.voting.common.path.BasePath;
import com.service.voting.common.response.Message;
import com.service.voting.common.response.Response;
import com.service.voting.common.response.dto.GlobalDto;
import com.service.voting.common.reuse.PageConvert;
import com.service.voting.tps.dto.req.TPSReq;
import com.service.voting.tps.dto.res.TPSRes;
import com.service.voting.tps.model.TPS;
import com.service.voting.tps.service.TPSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BasePath.BASE_TPS)
public class TPSController {

    @Autowired
    private TPSService tpsService;

    private ResponseEntity<Object> buildResponse(Object pageable, Object data, Message messageEnum, int action) {
        GlobalDto dto = new GlobalDto();
        dto.setStatus(messageEnum.getStatusCode());
        dto.setData(data);
        dto.setPageable(pageable);
        dto.setMessage(messageEnum.getMessage());
        return Response.buildResponse(dto, action);
    }

    @GetMapping
    public ResponseEntity<Object> getTPSs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String cityCode,
            @RequestParam(required = false) String districtCode,
            @RequestParam(defaultValue = "false") boolean pagination,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page) {
        if (pagination) {
            Pageable pageable = PageRequest.of(page, size);
            Page<TPSRes> pagedTPS = tpsService.getPagTPSs(search, pageable, provinceCode, cityCode, districtCode);
            return buildResponse(PageConvert.convert(pagedTPS), pagedTPS.getContent(), Message.SUCCESSFULLY_DEFAULT, 1);
        } else {
            List<TPSRes> tpsList = tpsService.getTPSs(search, provinceCode, cityCode, districtCode);
            return buildResponse(null, tpsList, Message.SUCCESSFULLY_DEFAULT, 1);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createTPS(@RequestBody TPSReq tpsReq) {
        TPS tps = tpsService.createTPS(tpsReq);
        return buildResponse(null, tps, Message.SUCCESSFULLY_DEFAULT, 1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTPS(@PathVariable Long id, @RequestBody TPSReq tpsReq) {
        TPS tps = tpsService.updateTPS(id, tpsReq);
        return buildResponse(null, tps, Message.SUCCESSFULLY_DEFAULT, 1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTPS(@PathVariable Long id) {
        tpsService.deleteTPS(id);
        return buildResponse(null, null, Message.SUCCESSFULLY_DEFAULT, 0);
    }
}