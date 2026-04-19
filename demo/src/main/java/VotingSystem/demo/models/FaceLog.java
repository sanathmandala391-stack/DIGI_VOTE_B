//package VotingSystem.demo.models;
//
//
//import jakarta.persistence.*;
//import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
//
//import java.time.LocalDateTime;
//
//@Entity
//public class FaceLog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    private User user;
//
//    private LocalDateTime loginTime;
//
//    private String status;
//
//    public FaceLog(){
//
//    }
//
//    public FaceLog(Long id, User user, LocalDateTime loginTime, String status) {
//        this.id = id;
//        this.user = user;
//        this.loginTime = loginTime;
//        this.status = status;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public LocalDateTime getLoginTime() {
//        return loginTime;
//    }
//
//    public void setLoginTime(LocalDateTime loginTime) {
//        this.loginTime = loginTime;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//}

//
//package VotingSystem.demo.models;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//// BUG FIX: Removed bogus import of
//// "org.springframework.boot.webmvc.autoconfigure.WebMvcProperties"
//// It was imported but never used — this causes a compile error in many setups
//// because WebMvcProperties is an internal autoconfigure class, not a model utility.
//
//@Entity
//public class FaceLog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private LocalDateTime loginTime;
//
//    // "SUCCESS" or "FAILED"
//    private String status;
//
//    public FaceLog() {}
//
//    public FaceLog(Long id, User user, LocalDateTime loginTime, String status) {
//        this.id = id;
//        this.user = user;
//        this.loginTime = loginTime;
//        this.status = status;
//    }
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public User getUser() { return user; }
//    public void setUser(User user) { this.user = user; }
//
//    public LocalDateTime getLoginTime() { return loginTime; }
//    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//}


package VotingSystem.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// BUG FIX: Removed bogus import of
// "org.springframework.boot.webmvc.autoconfigure.WebMvcProperties"
// It was imported but never used — this causes a compile error in many setups
// because WebMvcProperties is an internal autoconfigure class, not a model utility.

@Entity
public class FaceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime loginTime;

    // "SUCCESS" or "FAILED"
    private String status;

    public FaceLog() {}

    public FaceLog(Long id, User user, LocalDateTime loginTime, String status) {
        this.id = id;
        this.user = user;
        this.loginTime = loginTime;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getLoginTime() { return loginTime; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}