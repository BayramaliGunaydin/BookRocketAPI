package com.example.library.Controller;

import com.example.library.Config.JWTUtil;
import com.example.library.Exception.UsernameAlreadyExistException;
import com.example.library.Exception.UsernamePasswordNotFoundException;
import com.example.library.Model.*;
import com.example.library.Repository.RoleRepository;
import com.example.library.Repository.UserRepository;
import com.example.library.Response.AuthResponse;
import com.example.library.Service.IMyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private IMyUserDetailsService IMyUserDetailsService;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public AuthResponse registerHandler(@RequestBody RegisterCredentials registeruser) throws UsernameAlreadyExistException {

        if(userRepo.findByUsername(registeruser.getUsername()).isPresent()){

            throw new UsernameAlreadyExistException();
        }
        else{
            CustomUser user = new CustomUser();
            String encodedPass = passwordEncoder.encode(registeruser.getPassword());
            user.setPassword(encodedPass);
            Optional<Role> role =roleRepository.findByid(1L);
            user.setRole(role.get());
            user.setUsername(registeruser.getUsername());
            user.setPic(registeruser.getBase64());
            user = userRepo.save(user);
            String token = jwtUtil.generateToken(user.getUsername());
            AuthResponse ar= new AuthResponse();
            ar.setUsername(registeruser.getUsername());
            ar.setToken(token);
            ar.setId(user.getId());
            ar.setPic(registeruser.getBase64());
            ar.setRole(role.get());
            return ar;
        }

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public AuthResponse loginHandler(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getUsername());
            AuthResponse ar= new AuthResponse();
            Optional<CustomUser> user = IMyUserDetailsService.getOneUserByUserName(body.getUsername());
            ar.setUsername(user.get().getUsername());
            ar.setToken(token);
            ar.setId(user.get().getId());
            ar.setPic(user.get().getPic());
            ar.setRole(user.get().getRole());
            return ar;
        }catch (AuthenticationException authExc){
            throw new UsernamePasswordNotFoundException();
        }
    }
   


}