package com.example.library.Response;

import com.example.library.Model.Role;
import lombok.Data;

@Data
public class AuthResponse {

    String username;
    String token;
    Long id;
    byte[] pic;
    Role role;

}
