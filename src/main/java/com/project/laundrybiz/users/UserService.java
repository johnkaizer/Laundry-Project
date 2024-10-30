package com.project.laundrybiz.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        // Fetch all users and filter out admins
        return userRepository.findAll().stream()
                .filter(user -> !user.getRole().equalsIgnoreCase("admin"))
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Method to authenticate users based on username and password
    public User authenticateUser(String username, String password) {
        // Query the repository to find a user with the provided username
        User user = userRepository.findByUsername(username);

        // If user is found and password matches, return the user
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null; // Authentication failed
        }
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
