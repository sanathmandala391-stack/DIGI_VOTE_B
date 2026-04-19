//package VotingSystem.demo.models;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//public class Election {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    String title;
//    String description;
//
//    LocalDateTime startTime;
//    LocalDateTime endTime;
//
//    @Enumerated(EnumType.STRING)
//    ElectionStatus electionStatus;
//
//    @OneToMany(mappedBy = "election",cascade = CascadeType.ALL)
//    private List<Candidate> candidates;
//
//
//    public Election(){
//
//    }
//
//    public Election(Long id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, ElectionStatus electionStatus, List<Candidate> candidates) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.electionStatus = electionStatus;
//        this.candidates = candidates;
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
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public LocalDateTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalDateTime startTime) {
//        this.startTime = startTime;
//    }
//
//    public LocalDateTime getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(LocalDateTime endTime) {
//        this.endTime = endTime;
//    }
//
//    public ElectionStatus getElectionStatus() {
//        return electionStatus;
//    }
//
//    public void setElectionStatus(ElectionStatus electionStatus) {
//        this.electionStatus = electionStatus;
//    }
//
//    public List<Candidate> getCandidates() {
//        return candidates;
//    }
//
//    public void setCandidates(List<Candidate> candidates) {
//        this.candidates = candidates;
//    }
//
//
//}


package VotingSystem.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ElectionStatus electionStatus;

    // BUG FIX: @JsonIgnore here to break the circular reference.
    // Election → candidates → each Candidate → election → candidates → ... = infinite loop.
    // The candidates list is large and not needed in most API responses.
    // The frontend fetches candidates separately via /candidates/election/{id}.
    @JsonIgnore
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    private List<Candidate> candidates;

    public Election() {}

    public Election(Long id, String title, String description, LocalDateTime startTime,
                    LocalDateTime endTime, ElectionStatus electionStatus, List<Candidate> candidates) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.electionStatus = electionStatus;
        this.candidates = candidates;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public ElectionStatus getElectionStatus() { return electionStatus; }
    public void setElectionStatus(ElectionStatus electionStatus) { this.electionStatus = electionStatus; }

    public List<Candidate> getCandidates() { return candidates; }
    public void setCandidates(List<Candidate> candidates) { this.candidates = candidates; }
}
