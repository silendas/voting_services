package com.service.voting.users.services;

import com.service.voting.common.exception.ResourceNotFoundException;
import com.service.voting.users.dto.Req.RegistrationReq;
import com.service.voting.users.dto.Req.UserReq;
import com.service.voting.users.models.Registrations;
import com.service.voting.users.models.User;
import com.service.voting.users.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public List<Registrations> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Page<Registrations> getPagRegistrations(String search, Pageable pageable) {
        Specification<Registrations> spec = (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction(); // return all if no search term
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("username"), "%" + search + "%"),
                    criteriaBuilder.like(root.get("email"), "%" + search + "%"));
        };
        return registrationRepository.findAll(spec, pageable);
    }

    @Transactional
    public Registrations createRegistration(RegistrationReq registration) {
        Registrations registrations = new Registrations();
        registrations.setEmail(registration.getEmail());
        registrations.setUsername(registration.getUsername());
        registrations.setPassword(registration.getPassword());
        registrations.setName(registration.getName());
        registrations.setPhoneNumber(registration.getPhoneNumber());
        registrations.setAddress(registration.getAddress());
        return registrationRepository.save(registrations);
    }

    @Transactional
    public User approveRegistration(Long id) {
        Registrations registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));

        UserReq userReq = new UserReq();
        userReq.setUsername(registration.getUsername());
        userReq.setEmail(registration.getEmail());
        userReq.setPassword(registration.getPassword());
        userReq.setName(registration.getName());
        userReq.setPhoneNumber(registration.getPhoneNumber());
        userReq.setAddress(registration.getAddress());

        User user = userService.createUser(userReq);

        registration.setRegistered(true);
        registrationRepository.save(registration);

        registrationRepository.delete(registration);

        return user;
    }
}