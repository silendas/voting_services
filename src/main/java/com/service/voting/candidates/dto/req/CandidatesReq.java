package com.service.voting.candidates.dto.req;

import org.springframework.web.multipart.MultipartFile;

import com.service.voting.common.enums.VotingCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatesReq {

    @NotBlank(message = "Candidate diperlukan")
    private String candidate;

    private VotingCategory category;

    private String organization;

    private MultipartFile picture;

    public void setPicture(MultipartFile picture) {
        if (picture != null) {
            String contentType = picture.getContentType();
            if (contentType != null && (contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/jpg"))) {
                this.picture = picture;
            } else {
                throw new IllegalArgumentException("Only PNG, JPG, or JPEG is allowed");
            }
        }
    }
    
}
