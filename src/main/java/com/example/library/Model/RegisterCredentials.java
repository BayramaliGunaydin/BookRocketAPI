package com.example.library.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterCredentials {

    private String username;
    private String password;
    private byte[] base64;
}
