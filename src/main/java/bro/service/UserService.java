package bro.service;

import bro.entity.NewUser;
import bro.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserEntity> findAll();
    UserEntity findByUsername(String username);
    UserEntity save(UserEntity userEntity);
    void delete(UserEntity userEntity);
    void saveTheNewUser(NewUser newUser);
}
