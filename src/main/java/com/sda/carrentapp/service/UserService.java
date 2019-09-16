package com.sda.carrentapp.service;

import com.sda.carrentapp.entity.EntityStatus;
import com.sda.carrentapp.entity.Role;
import com.sda.carrentapp.entity.User;
import com.sda.carrentapp.entity.UserDTO;
import com.sda.carrentapp.entity.mapper.UserMapper;
import com.sda.carrentapp.exception.UserNotFoundException;
import com.sda.carrentapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAllByEntityStatus(EntityStatus.ACTIVE);
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public void saveUser(UserDTO userDTO) {
//        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User userToSave = UserMapper.map(userDTO);
        userRepository.save(userToSave);
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setEntityStatus(EntityStatus.ARCHIVED);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username)
                .map(existingProfile -> new org.springframework.security.core.userdetails.User(
                        existingProfile.getUsername(),
                        existingProfile.getPassword(),
                        parseRoles(existingProfile.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private Collection<? extends GrantedAuthority> parseRoles(Role roles) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles.name()));

    }
}
