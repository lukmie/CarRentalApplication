package com.sda.carrentapp.service;

import com.sda.carrentapp.entity.EntityStatus;
import com.sda.carrentapp.entity.Role;
import com.sda.carrentapp.entity.User;
import com.sda.carrentapp.entity.UserDTO;
import com.sda.carrentapp.entity.mapper.UserMapper;
import com.sda.carrentapp.exception.UserNotFoundException;
import com.sda.carrentapp.repository.DepartmentRepository;
import com.sda.carrentapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private BCryptPasswordEncoder encoder;
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public UserService(BCryptPasswordEncoder encoder, UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAllByEntityStatus(EntityStatus.ACTIVE)
                .stream()
                .filter(u -> u.getRole().equals(Role.USER))
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User getUserByUserName(String userName) throws UserNotFoundException {
        return userRepository.findUserByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found with userName: " + userName));
    }

    public User getLoggedInUser() throws UserNotFoundException {
        return userRepository.findUserByUsername(getUserNameFromContextHolder())
                .orElseThrow(() -> new UserNotFoundException("User not found with userName: " + getUserNameFromContextHolder()));
    }

    public Optional<User> getOptionalUserByUserName(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    public void saveUser(UserDTO userDTO) {
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userDTO.setEntityStatus(EntityStatus.ACTIVE);
        departmentRepository.getDepartmentById(5L).ifPresent(userDTO::setDepartment);
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

    private String getUserNameFromContextHolder() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
