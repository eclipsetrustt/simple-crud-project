package com.eclipse.simplecrud.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eclipse.simplecrud.dto.UserDto;
import com.eclipse.simplecrud.model.User;
import com.eclipse.simplecrud.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAll() {
        return userRepository.findAllByOrderByIdAsc().stream()
            .map(u -> UserDto.builder()
                    .id(u.getId())
                    .name(u.getName())
                    .age(u.getAge())
                    .build())
            .toList();
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .build();
    }

    public UserDto createUser(UserDto userDto) {
        User user = User.builder()
            .name(userDto.getName())
            .age(userDto.getAge())
            .build();

        User saved = userRepository.save(user);
        
        return UserDto.builder()
            .id(saved.getId())
            .name(saved.getName())
            .age(saved.getAge())
            .build();
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .build();
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(user);
    }
}
