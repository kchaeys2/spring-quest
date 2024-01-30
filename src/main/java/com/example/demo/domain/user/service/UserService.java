package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.response.InitUserResponse.UserData;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void saveUser(List<UserData> userDatas) {
        userRepository.deleteAll();

        userDatas.stream().map(userData -> new User(userData.getId(), userData.getUsername(), userData.getEmail()))
                .forEach(userRepository::save);
    }

}
