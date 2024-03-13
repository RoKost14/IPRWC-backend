package com.example.iprwcserver.service;

import com.example.iprwcserver.dao.UserDAO;
import com.example.iprwcserver.model.Cart;
import com.example.iprwcserver.model.Role;
import com.example.iprwcserver.model.User;
import com.example.iprwcserver.repository.CartRepository;
import com.example.iprwcserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public Optional<String> register(String username, String password) {
        Optional<com.example.iprwcserver.model.User> foundUser = userDAO.findByUsername(username);
        if (foundUser.isPresent()) {
            return Optional.empty();
        }

        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.CUSTOMER)
                .cart(cart)
                .build();
        cart.setUser(user);
        userDAO.save(user);
        String token = jwtService.generateToken(Map.of("role", user.getRole()), user.getId() );
        return Optional.of(token);
    }
    public Role getRoleByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return user.getRole();
    }


    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userDAO.loadUserByUsername(username);
        return jwtService.generateToken(Map.of("role", user.getRole()), user.getId());
    }
}
