package com.project.laundrybiz.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.prefs.Preferences;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST: Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // DELETE: Remove a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PUT: Update user details
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            existingUser.setFullName(user.getFullName());
            existingUser.setUsername(user.getUsername());
            existingUser.setPhone(user.getPhone());

            // Update password only if provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }

            User updatedUser = userService.saveUser(existingUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST: Handle login
    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              RedirectAttributes redirectAttributes) {
        // Authenticate user
        User user = userService.authenticateUser(username, password);
        if (user != null) {
            // Save the user ID, username, and role in preferences
            Preferences prefs = Preferences.userNodeForPackage(UserController.class);
            prefs.putLong("loggedInUserId", user.getId());
            prefs.put("loggedInUsername", username);
            prefs.put("loggedInUserRole", user.getRole());

            // Redirect based on user role
            if ("ADMIN".equals(user.getRole())) {
                return new ModelAndView(new RedirectView("/dashboard"));
            } else if ("USER".equals(user.getRole())) {
                return new ModelAndView(new RedirectView("/empdashboard"));
            } else {
                // Handle unknown roles if needed
                redirectAttributes.addFlashAttribute("errorMessage", "Access denied: Unknown role");
                return new ModelAndView(new RedirectView("/login"));
            }
        } else {
            // Invalid credentials; redirect to the login page with error
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
            return new ModelAndView(new RedirectView("/login"));
        }
    }



    // GET: Get current logged-in username
    @GetMapping("/currentUsername")
    public ResponseEntity<String> getCurrentUsername() {
        Preferences prefs = Preferences.userNodeForPackage(UserController.class);
        String loggedInUsername = prefs.get("loggedInUsername", null);
        if (loggedInUsername != null) {
            return new ResponseEntity<>(loggedInUsername, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET: Get current logged-in user details
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        Preferences prefs = Preferences.userNodeForPackage(UserController.class);
        String loggedInUsername = prefs.get("loggedInUsername", null);
        if (loggedInUsername != null) {
            User user = userService.getUserByUsername(loggedInUsername);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/currentUserId")
    public ResponseEntity<Long> getCurrentUserId() {
        Preferences prefs = Preferences.userNodeForPackage(UserController.class);
        Long loggedInUserId = prefs.getLong("loggedInUserId", -1L);
        if (loggedInUserId != -1L) {
            return new ResponseEntity<>(loggedInUserId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // POST: Handle logout
    @PostMapping("/logout")
    public ModelAndView logout() {
        // Clear stored preferences
        Preferences prefs = Preferences.userNodeForPackage(UserController.class);
        prefs.remove("loggedInUserId");
        prefs.remove("loggedInUsername");
        prefs.remove("loggedInUserRole");

        // Redirect to login page
        return new ModelAndView(new RedirectView("/"));
    }


}
