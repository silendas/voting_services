package com.service.voting.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.service.voting.common.reuse.Filter;
import com.service.voting.users.dto.Req.UserReq;
import com.service.voting.users.dto.Res.UserRes;
import com.service.voting.users.filter.UserFilter;
import com.service.voting.users.models.User;
import com.service.voting.users.repository.PagUserRepository;
import com.service.voting.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PagUserRepository pagUserRepo;

    private final Filter<User> filter = new Filter<>();
    private PasswordEncoder passwordEncoder;

    public List<UserRes> getUsers(String search) {
        Specification<User> spec = filter.isNotDeleted()
                .and(new UserFilter().notEqualId(1l))
                .and(new UserFilter().byNameOrEmail(search));
        List<User> users = userRepo.findAll(spec);
        return users.stream().map(this::convertToUserRes).toList();
    }

    public Page<UserRes> getPagUsers(String search, Pageable pageable) {
        Specification<User> spec = filter.isNotDeleted()
                .and(new UserFilter().byNameOrEmail(search));
        Page<User> users = pagUserRepo.findAll(spec, pageable);
        return users.map(this::convertToUserRes);
    }

    @Transactional
    public User createUser(UserReq userReq) {
        User user = new User();
        user.setUsername(userReq.getUsername());
        user.setEmail(userReq.getEmail());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        user.setName(userReq.getName());
        user.setPhoneNumber(userReq.getPhoneNumber());
        user.setAddress(userReq.getAddress());
        user.setActive(true);
        user.setDeleted(false);
        return userRepo.save(user);
    }

    @Transactional
    public User updateUser(Long id, UserReq userReq) {
        User user = getUser(id);
        user.setUsername(userReq.getUsername());
        user.setEmail(userReq.getEmail());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        user.setName(userReq.getName());
        user.setPhoneNumber(userReq.getPhoneNumber());
        user.setAddress(userReq.getAddress());
        return userRepo.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUser(id);
        user.setDeleted(true);
        user.setActive(false);
        userRepo.save(user);
    }

    private UserRes convertToUserRes(User user) {
        return UserRes.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .active(user.isActive())
                .role(user.getRole()) // Pastikan User memiliki metode getRole()
                .build();
    }

    public User getUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserRes getUserByUsernameOrEmail(String usernameOrEmail) {
        User user = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return convertToUserRes(user);
    }
}