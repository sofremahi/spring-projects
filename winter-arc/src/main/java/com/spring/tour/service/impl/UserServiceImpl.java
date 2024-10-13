package com.spring.tour.service.impl;

import com.spring.tour.entity.JobSeekerProfile;
import com.spring.tour.entity.RecruiterProfile;
import com.spring.tour.entity.Users;
import com.spring.tour.repository.JobSeekerProfileRepo;
import com.spring.tour.repository.RecruiterProfileRepo;
import com.spring.tour.repository.UsersRepo;
import com.spring.tour.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepo usersRepo;
    private final RecruiterProfileRepo recruiterProfileRepo;
    private final JobSeekerProfileRepo jobSeekerProfileRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addNewUser(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepo.save(users);
        if (users.getUserTypeId().getUserTypeId() == 1) {
            RecruiterProfile recruiterProfile = new RecruiterProfile();
            recruiterProfile.setUserId(users);
            recruiterProfileRepo.save(recruiterProfile);
        } else {
            JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
            jobSeekerProfile.setUserId(users);
            jobSeekerProfileRepo.save(jobSeekerProfile);
        }
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return usersRepo.findByEmail(email);
    }

    @Override
    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepo.findByEmail(authentication.getName()).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("recruiter"))) {
                return recruiterProfileRepo.findById((long) user.getUserId()).orElse(new RecruiterProfile());
            } else {
                return jobSeekerProfileRepo.findById((long) user.getUserId()).orElse(new JobSeekerProfile());
            }
        }
        return null;
    }
}

