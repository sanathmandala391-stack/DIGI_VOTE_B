package VotingSystem.demo.Service;

import VotingSystem.demo.Repository.UserRepository;
import VotingSystem.demo.config.JwtUtil;
import VotingSystem.demo.models.Role;
import VotingSystem.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> UserRegister(User u) {
        u.setEmail(u.getEmail().toLowerCase().trim());

        User userExists = userRepository.findByEmail(u.getEmail());
        if (userExists != null) {
            return ResponseEntity.badRequest().body("User email already taken");
        }

        if (u.getRole() == null) {
            u.setRole(Role.VOTER);
        }

        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setCreatedAt(LocalDateTime.now());
        u.setVerified(false);
        userRepository.save(u);

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> UserLogin(String email, String password) {
        User user = userRepository.findByEmail(email.toLowerCase().trim());

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        boolean valid = passwordEncoder.matches(password, user.getPassword());
        if (!valid) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // FIX: pass both userId AND role
        String role = user.getRole() != null ? user.getRole().name() : "VOTER";
        String token = jwtUtil.generateToken(user.getId(), role);

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token,
                "email", user.getEmail(),
                "role", role,
                "userId", user.getId(),
                "name", user.getName() != null ? user.getName() : ""
        ));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}




