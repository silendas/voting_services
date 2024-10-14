package com.service.voting.candidates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.service.voting.common.enums.VotingCategory;
import com.service.voting.common.exception.ResourceNotFoundException;
import com.service.voting.common.path.AttachmentPath;
import com.service.voting.common.reuse.Filter;
import com.service.voting.common.utils.FileUtil;
import com.service.voting.candidates.dto.req.CandidatesReq;
import com.service.voting.candidates.dto.res.CandidatesRes;
import com.service.voting.candidates.filter.CandidatesFilter;
import com.service.voting.candidates.model.Candidates;
import com.service.voting.candidates.repository.CandidatesRepository;
import com.service.voting.candidates.repository.PagCandidatesRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CandidatesService {

    @Autowired
    private CandidatesRepository candidatesRepo;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private PagCandidatesRepository pagCandidatesRepo;

    private final Filter<Candidates> filter = new Filter<>();

    public List<CandidatesRes> getCandidatess(String search, VotingCategory category) {
        Specification<Candidates> spec = filter.isNotDeleted()
                .and(new CandidatesFilter().byCandidateOrOrganization(search))
                .and(new CandidatesFilter().byCategory(category))
                .and(filter.orderByIdDesc());
        List<Candidates> candidatess = candidatesRepo.findAll(spec);
        return candidatess.stream().map(this::convertToCandidatesRes).toList();
    }

    public Page<CandidatesRes> getPagCandidatess(String search, VotingCategory category, Pageable pageable) {
        Specification<Candidates> spec = filter.isNotDeleted()
                .and(new CandidatesFilter().byCandidateOrOrganization(search))
                .and(new CandidatesFilter().byCategory(category))
                .and(filter.orderByIdDesc());
        Page<Candidates> candidatess = pagCandidatesRepo.findAll(spec, pageable);
        return candidatess.map(this::convertToCandidatesRes);
    }

    public ResponseEntity<InputStreamResource> getPicture(String picture) {
        try {
            File file = new File(AttachmentPath.STORAGE_PATH_ATTACHMENT, picture);
            if (!file.exists()) {
                throw new ResourceNotFoundException("Picture not found with name : " + picture);
            }

            String fileType = file.getName().substring(file.getName().lastIndexOf('.') + 1);
            MediaType mediaType = MediaType.parseMediaType("image/" + fileType);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    public Candidates createCandidates(CandidatesReq candidateReq) {
        Candidates candidates = new Candidates();
        candidates.setCandidate(candidateReq.getCandidate());
        candidates.setCategory(candidateReq.getCategory());
        candidates.setOrganization(candidateReq.getOrganization()); 

        if (candidateReq.getPicture() != null && !candidateReq.getPicture().isEmpty()) {
            String fileName = fileUtil.store(candidateReq.getPicture(), AttachmentPath.STORAGE_PATH_ATTACHMENT,
                    candidateReq.getPicture().getOriginalFilename());
            candidates.setPicture(fileName);
        }

        return candidatesRepo.save(candidates);
    }

    @Transactional
    public Candidates updateCandidates(Long id, CandidatesReq candidatesReq) {
        Candidates candidates = getCandidates(id);
        candidates.setCandidate(candidatesReq.getCandidate()); 
        candidates.setCategory(candidatesReq.getCategory()); 
        candidates.setOrganization(candidatesReq.getOrganization()); 
        return candidatesRepo.save(candidates);
    }

    @Transactional
    public void deleteCandidates(Long id) {
        Candidates candidates = getCandidates(id);
        candidates.setDeleted(true);
        candidatesRepo.save(candidates);
    }

    @Transactional
    public void updatePicture(Long id, MultipartFile picture) {
        Candidates candidates = getCandidates(id);
        if (picture != null) {
            if (candidates.getPicture() != null && !candidates.getPicture().isEmpty()) {
                fileUtil.deleteFile(candidates.getPicture(), AttachmentPath.STORAGE_PATH_ATTACHMENT);
            }
            String fileName = fileUtil.store(picture, AttachmentPath.STORAGE_PATH_ATTACHMENT, picture.getOriginalFilename());
            candidates.setPicture(fileName);
            candidatesRepo.save(candidates);
        }
    }

    private CandidatesRes convertToCandidatesRes(Candidates candidates) {
        return CandidatesRes.builder()
                .candidate(candidates.getCandidate())
                .category(candidates.getCategory())
                .organization(candidates.getOrganization())
                .picture(candidates.getPicture())
                .build();
    }

    public Candidates getCandidates(Long id) {
        Optional<Candidates> optionalCandidates = candidatesRepo.findById(id);
        return optionalCandidates.orElseThrow(() -> new ResourceNotFoundException("Candidates not found"));
    }

}