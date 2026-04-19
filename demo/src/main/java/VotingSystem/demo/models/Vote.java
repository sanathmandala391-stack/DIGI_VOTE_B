//package VotingSystem.demo.models;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(
//        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "election_id"})
//)
//public class Vote {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "election_id")
//    private Election election;
//
//    @ManyToOne
//    @JoinColumn(name = "candidate_id")
//    private Candidate candidate;
//
//   private LocalDateTime timestamp=LocalDateTime.now();
//
//
//
//    public Vote(User user, Election election, Candidate candidate) {
//        this.user = user;
//        this.election = election;
//        this.candidate = candidate;
//        this.timestamp = LocalDateTime.now();
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
//    public Election getElection() {
//        return election;
//    }
//
//    public void setElection(Election election) {
//        this.election = election;
//    }
//
//    public Candidate getCandidate() {
//        return candidate;
//    }
//
//    public void setCandidate(Candidate candidate) {
//        this.candidate = candidate;
//    }
//
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(LocalDateTime timestamp) {
//        this.timestamp = timestamp;
//    }
//}

//
//package VotingSystem.demo.models;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(
//        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "election_id"})
//)
//public class Vote {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "election_id")
//    private Election election;
//
//    @ManyToOne
//    @JoinColumn(name = "candidate_id")
//    private Candidate candidate;
//
//    private LocalDateTime timestamp;
//
//    // BUG FIX: JPA requires a no-argument constructor on every @Entity.
//    // The original Vote class only had the 3-arg constructor.
//    // Without this, Hibernate throws: "No default constructor for entity"
//    // and the app crashes on startup.
//    public Vote() {}
//
//    public Vote(User user, Election election, Candidate candidate) {
//        this.user = user;
//        this.election = election;
//        this.candidate = candidate;
//        this.timestamp = LocalDateTime.now();
//    }
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public User getUser() { return user; }
//    public void setUser(User user) { this.user = user; }
//
//    public Election getElection() { return election; }
//    public void setElection(Election election) { this.election = election; }
//
//    public Candidate getCandidate() { return candidate; }
//    public void setCandidate(Candidate candidate) { this.candidate = candidate; }
//
//    public LocalDateTime getTimestamp() { return timestamp; }
//    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
//}


package VotingSystem.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "election_id"})
)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private LocalDateTime timestamp;

    // BUG FIX: JPA requires a no-argument constructor on every @Entity.
    // The original Vote class only had the 3-arg constructor.
    // Without this, Hibernate throws: "No default constructor for entity"
    // and the app crashes on startup.
    public Vote() {}

    public Vote(User user, Election election, Candidate candidate) {
        this.user = user;
        this.election = election;
        this.candidate = candidate;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Election getElection() { return election; }
    public void setElection(Election election) { this.election = election; }

    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
