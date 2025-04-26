package bro.service;

import bro.dao.RoleDao;
import bro.entity.*;
import bro.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserEntityRepository userEntityRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleDao roleDao;

    public UserServiceImpl(UserEntityRepository userEntityRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           RoleDao roleDao){
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void delete(UserEntity userEntity) {
        userEntityRepository.delete(userEntity);
    }

    @Override
    public void saveTheNewUser(NewUser newUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(newUser.getUsername());
        userEntity.setEmail(newUser.getEmail());
        userEntity.setFirstname(newUser.getFirstname());
        userEntity.setLastname(newUser.getLastname());
        userEntity.setEnabled(true);
        userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userEntity.setProfile_photo(newUser.getProfilePhoto());

        userEntity.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_BUYER")));

        userEntityRepository.save(userEntity);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByUsername(username);
        return new CustomUserDetails(userEntity);
    }
}
