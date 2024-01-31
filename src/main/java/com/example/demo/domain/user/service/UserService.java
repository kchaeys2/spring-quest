package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.response.InitUserResponse.UserData;
import com.example.demo.domain.user.dto.response.UserPageResponse;
import com.example.demo.domain.user.dto.response.UserResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public UserPageResponse findUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> userResponses = userPage.getContent().stream()
                .map(User::createUserResponse)
                .toList();
        return new UserPageResponse((int) userPage.getTotalElements(),userPage.getTotalPages(),userResponses);
    }
}
