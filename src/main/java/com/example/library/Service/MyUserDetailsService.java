package com.example.library.Service;


import com.example.library.Exception.IdNotFoundException;
import com.example.library.Model.CustomUser;
import com.example.library.Repository.RoleRepository;
import com.example.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class MyUserDetailsService implements IMyUserDetailsService {

    @Autowired private UserRepository userRepo;
    @Autowired
            private RoleRepository roleRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CustomUser> userRes = userRepo.findByUsername(username);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Kullanıcı: = " + username+" bulunamadı.");
        CustomUser user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+userRepo.findByUsername(username).get().getRole().getRolename())));
    }
    public CustomUser addroletouser(Long id,Long roleid){
        Optional<CustomUser> customUser = userRepo.findById(id);

        if(customUser.isPresent()){
            customUser.get().getRole().setId(roleid);
            userRepo.save(customUser.get());
            return customUser.get();
        }
        else{
            throw new IdNotFoundException();
        }
    }

    public CustomUser getOneUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public Optional<CustomUser> getOneUserByUserName(String userName) {
        return userRepo.findByUsername(userName);
    }

    public List<CustomUser> getUsersByUserName(String userName){
         return userRepo.findUsersWithPartOfName(userName);
    }

    public void changeRole(Long userid){
        userRepo.setEditor(userid);
    }
}