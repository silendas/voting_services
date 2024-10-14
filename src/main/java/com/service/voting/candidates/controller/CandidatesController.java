package com.service.voting.candidates.controller;

import com.service.voting.common.enums.VotingCategory;
import com.service.voting.common.path.BasePath;
import com.service.voting.common.response.Message;
import com.service.voting.common.response.Response;
import com.service.voting.common.response.dto.GlobalDto;
import com.service.voting.common.reuse.PageConvert;
import com.service.voting.candidates.dto.req.CandidatesReq;
import com.service.voting.candidates.dto.res.CandidatesRes;
import com.service.voting.candidates.model.Candidates;
import com.service.voting.candidates.service.CandidatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(BasePath.BASE_CANDIDATES)
public class CandidatesController {

    @Autowired
    private CandidatesService candidatesService;

    private ResponseEntity<Object> buildResponse(Object pageable, Object data, Message messageEnum, int action) {
        GlobalDto dto = new GlobalDto();
        dto.setStatus(messageEnum.getStatusCode());
        dto.setData(data);
        dto.setPageable(pageable);
        dto.setMessage(messageEnum.getMessage());
        return Response.buildResponse(dto, action);
    }

    @GetMapping
    public ResponseEntity<Object> getCandidates(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) VotingCategory category,
            @RequestParam(defaultValue = "false") boolean pagination,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page) {
        if (pagination) {
            Pageable pageable = PageRequest.of(page, size);
            Page<CandidatesRes> pagedCandidates = candidatesService.getPagCandidatess(search, category, pageable);
            return buildResponse(PageConvert.convert(pagedCandidates), pagedCandidates.getContent(), Message.SUCCESSFULLY_DEFAULT, 1);
        } else {
            List<CandidatesRes> candidatesList = candidatesService.getCandidatess(search, category);
            return buildResponse(null, candidatesList, Message.SUCCESSFULLY_DEFAULT, 1);
        }
    }

    @GetMapping("/{id}/picture/{picture}")
    public ResponseEntity<InputStreamResource> getCandidatePicture(@PathVariable Long id, @PathVariable String picture) {
        return candidatesService.getPicture(picture);
    }

    @PostMapping
    public ResponseEntity<Object> createCandidate(@ModelAttribute CandidatesReq candidatesReq) {
        Candidates candidates = candidatesService.createCandidates(candidatesReq);
        return buildResponse(null, candidates, Message.SUCCESSFULLY_DEFAULT, 1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCandidate(@PathVariable Long id, @ModelAttribute CandidatesReq candidatesReq) {
        Candidates candidates = candidatesService.updateCandidates(id, candidatesReq);
        return buildResponse(null, candidates, Message.SUCCESSFULLY_DEFAULT, 1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCandidate(@PathVariable Long id) {
        candidatesService.deleteCandidates(id);
        return buildResponse(null, null, Message.SUCCESSFULLY_DEFAULT, 0);
    }

    @PutMapping("/{id}/picture")
    public ResponseEntity<Object> updateCandidatePicture(@PathVariable Long id, @RequestParam MultipartFile picture) {
        candidatesService.updatePicture(id, picture);
        return buildResponse(null, null, Message.SUCCESSFULLY_DEFAULT, 0);
    }
}