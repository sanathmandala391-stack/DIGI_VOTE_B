//package VotingSystem.demo.models;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    private boolean isVerified = false;
//
//    @Column(columnDefinition = "TEXT")
//    private String faceEmbedding;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    // Default constructor
//    public User() {}
//
//    // Parameterized constructor (FIXED)
////    public User(Long id, String name, String email, String password,
////                Role role, Boolean isVerified, String faceEmbedding,
////                LocalDateTime createdAt) {
////
////        this.id = id;
////        this.name = name;
////        this.email = email;
////        this.password = password;
////        this.role = role;
////        this.isVerified = isVerified;
////        this.faceEmbedding = faceEmbedding;
////        this.createdAt = createdAt;
////    }
//
//    // Getters & Setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public boolean isVerified() {
//        return isVerified;
//    }
//
//    public void setVerified(boolean verified) {
//        isVerified = verified;
//    }
//
//    public String getFaceEmbedding() {
//        return faceEmbedding;
//    }
//
//    public void setFaceEmbedding(String faceEmbedding) {
//        this.faceEmbedding = faceEmbedding;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//}

//
//package VotingSystem.demo.models;
//
//import jakarta.persistence.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.List;
//
//// BUG FIX: User must implement UserDetails so Spring Security can use it
//// as the authentication principal properly. Without this, casting
//// auth.getPrincipal() to User in VoteController and UserController
//// works right now ONLY because JwtFilter manually sets the User object.
//// But if any Spring Security method-level auth (@PreAuthorize) is used,
//// it will fail. Implementing UserDetails makes the whole security chain solid.
//
//@Entity
//@Table(name = "users")
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    private boolean isVerified = false;
//
//    @Column(columnDefinition = "TEXT")
//    private String faceEmbedding;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    public User() {}
//
//    // ---- UserDetails implementation ----
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Returns ROLE_ADMIN or ROLE_VOTER so @PreAuthorize("hasRole('ADMIN')") works
//        return List.of(new SimpleGrantedAuthority("ROLE_" + (role != null ? role.name() : "VOTER")));
//    }
//
//    @Override
//    public String getUsername() {
//        // Spring Security uses getUsername() as the principal identifier
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() { return true; }
//
//    @Override
//    public boolean isAccountNonLocked() { return true; }
//
//    @Override
//    public boolean isCredentialsNonExpired() { return true; }
//
//    @Override
//    public boolean isEnabled() { return true; }
//
//    // ---- Getters & Setters ----
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    // NOTE: getPassword() is already required by UserDetails — returns the hashed password
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public Role getRole() { return role; }
//    public void setRole(Role role) { this.role = role; }
//
//    public boolean isVerified() { return isVerified; }
//    public void setVerified(boolean verified) { isVerified = verified; }
//
//    public String getFaceEmbedding() { return faceEmbedding; }
//    public void setFaceEmbedding(String faceEmbedding) { this.faceEmbedding = faceEmbedding; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
//}//
package VotingSystem.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Use Boolean wrapper (not primitive boolean)
    // Mapped explicitly to column "verified" with default 0
    // Boolean handles null from DB gracefully — primitive boolean crashes
    @Column(name = "verified", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean verified = false;

    @Column(columnDefinition = "TEXT")
    private String faceEmbedding;

    private LocalDateTime createdAt = LocalDateTime.now();

    public User() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isVerified() { return Boolean.TRUE.equals(verified); }
    public void setVerified(boolean verified) { this.verified = verified; }

    public String getFaceEmbedding() { return faceEmbedding; }
    public void setFaceEmbedding(String faceEmbedding) { this.faceEmbedding = faceEmbedding; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}