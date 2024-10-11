package com.service.voting.users.controller;

import com.service.voting.common.path.BasePath;
import com.service.voting.common.response.Response;
import com.service.voting.common.response.dto.GlobalDto;
import com.service.voting.common.reuse.PageConvert;
import com.service.voting.users.dto.Req.RegistrationReq;
import com.service.voting.users.models.Registrations;
import com.service.voting.users.models.User;
import com.service.voting.users.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BasePath.BASE_REGISTER)
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    private ResponseEntity<Object> buildResponse(Object data, int status, int action) {
        GlobalDto dto = new GlobalDto();
        dto.setStatus(status);
        dto.setData(data);
        return Response.buildResponse(dto, action);
    }

    @GetMapping
    public ResponseEntity<Object> getRegistrations(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "false") boolean pagination,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page) {
        if (pagination) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Registrations> pagedRegistrations = registrationService.getPagRegistrations(search, pageable);
            return buildResponse(PageConvert.convert(pagedRegistrations), 200, 1);
        } else {
            List<Registrations> registrations = registrationService.getAllRegistrations();
            return buildResponse(registrations, 200, 1);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createRegistration(@RequestBody RegistrationReq registration) {
        Registrations savedRegistration = registrationService.createRegistration(registration);
        return buildResponse(savedRegistration, 200, 1);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Object> approveRegistration(@PathVariable Long id) {
        User user = registrationService.approveRegistration(id);
        return buildResponse(user, 200, 1);
    }
}