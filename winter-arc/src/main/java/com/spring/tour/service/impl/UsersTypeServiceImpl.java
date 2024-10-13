package com.spring.tour.service.impl;

import com.spring.tour.entity.UsersType;
import com.spring.tour.repository.UsersTypeRepo;
import com.spring.tour.service.UsersTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class UsersTypeServiceImpl implements UsersTypeService {
    private final UsersTypeRepo usersTypeRepo;
    @Override
    public List<UsersType> findAll() {
        return usersTypeRepo.findAll();
    }
}
