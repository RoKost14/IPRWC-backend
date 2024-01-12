package com.example.iprwcserver.mapper;

import com.example.iprwcserver.dto.UserCreateDTO;
import com.example.iprwcserver.dto.UserResponseDTO;
import com.example.iprwcserver.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User toEntity(UserCreateDTO userCreateDTO) {
        return User.builder()
                .username(userCreateDTO.getUsername())
                .password(userCreateDTO.getPassword())
                .build();
    }

    public UserResponseDTO fromEntity(User user) {
        return UserResponseDTO
                .builder()
                .username(user.getUsername())
                .id(user.getId())
                .cart(user.getCart())
                .role(user.getRole())

                .build();
    }
}
