package com.example.netty.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginModel {
    private Integer userId;
    private String userName;
    private String password;
}
