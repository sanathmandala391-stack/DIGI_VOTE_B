//package VotingSystem.demo.models;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Candidate{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    String name;
//    String party;
//
//    @ManyToOne
//    @JoinColumn(name = "election_id")
//   private Election election;
//
//    private int voteCount = 0;
//
//
//    public Candidate(){
//
//    }
//
//    public Candidate(String name, String party, Long id, Election election, int voteCount) {
//        this.name = name;
//        this.party = party;
//        this.id = id;
//        this.election = election;
//        this.voteCount = voteCount;
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
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getParty() {
//        return party;
//    }
//
//    public void setParty(String party) {
//        this.party = party;
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
//    public int getVoteCount() {
//        return voteCount;
//    }
//
//    public void setVoteCount(int voteCount) {
//        this.voteCount = voteCount;
//    }
//}


package VotingSystem.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String party;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    // FIX: Integer (wrapper) instead of int (primitive)
    // Frontend sends { name, party } with no voteCount field → Jackson maps it as null
    // Primitive int cannot be null → crashes with "Cannot map null into type int"
    // Integer wrapper handles null fine, defaults to 0
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer voteCount = 0;

    public Candidate() {}

    public Candidate(String name, String party, Long id, Election election, Integer voteCount) {
        this.name = name;
        this.party = party;
        this.id = id;
        this.election = election;
        this.voteCount = voteCount != null ? voteCount : 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }

    public Election getElection() { return election; }
    public void setElection(Election election) { this.election = election; }

    public Integer getVoteCount() { return voteCount != null ? voteCount : 0; }
    public void setVoteCount(Integer voteCount) { this.voteCount = voteCount != null ? voteCount : 0; }
}