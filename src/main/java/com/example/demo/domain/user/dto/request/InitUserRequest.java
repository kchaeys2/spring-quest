package com.example.demo.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InitUserRequest {
    private int seed;
    private int quantity;
}
