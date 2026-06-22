package com.quitqecom.dto;

import com.quitqecom.enums.Role;

public record RegisterDto (
        String username,
        String password,
        Role role
){
}
