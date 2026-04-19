//package VotingSystem.demo.controllers;
//
//
//import VotingSystem.demo.Repository.CandidateRepository;
//import VotingSystem.demo.models.Candidate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collections;
//import java.util.List;
//
//@RestController
//@RequestMapping("/candidates")
//public class CandidateController {
//
//    @Autowired
//    private CandidateRepository candidateRepository;
//
//    @GetMapping("/allcandidates")
//    public List<Candidate> getallCandidates(){
//        return candidateRepository.findAll();
//    }
//
//    @GetMapping("/{electionId}")
//    public List<Candidate> getCandidateById(@PathVariable Long electionId){
//        return Collections.singletonList(candidateRepository.findById(electionId).orElse(null));
//    }
//
//
//}

//
//package VotingSystem.demo.controllers;
//
//import VotingSystem.demo.Repository.CandidateRepository;
//import VotingSystem.demo.models.Candidate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/candidates")
//public class CandidateController {
//
//    @Autowired
//    private CandidateRepository candidateRepository;
//
//    @GetMapping("/allcandidates")
//    public List<Candidate> getAllCandidates() {
//        return candidateRepository.findAll();
//    }
//
//    // BUG FIX: Original method was named getCandidateById but took electionId as path variable,
//    // then used candidateRepository.findById(electionId) — mixing up election ID and candidate ID!
//    // This should return all candidates for a given election.
//    // Renamed to getCandidatesByElection and added a proper query method.
//    @GetMapping("/election/{electionId}")
//    public ResponseEntity<List<Candidate>> getCandidatesByElection(@PathVariable Long electionId) {
//        List<Candidate> candidates = candidateRepository.findByElectionId(electionId);
//        return ResponseEntity.ok(candidates);
//    }
//
//    // Keep a proper single candidate lookup by candidate ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCandidateById(@PathVariable Long id) {
//        Candidate c = candidateRepository.findById(id).orElse(null);
//        if (c == null) return ResponseEntity.status(404).body("Candidate not found");
//        return ResponseEntity.ok(c);
//    }
//}


package VotingSystem.demo.controllers;

import VotingSystem.demo.Repository.CandidateRepository;
import VotingSystem.demo.models.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/allcandidates")
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // BUG FIX: Original method was named getCandidateById but took electionId as path variable,
    // then used candidateRepository.findById(electionId) — mixing up election ID and candidate ID!
    // This should return all candidates for a given election.
    // Renamed to getCandidatesByElection and added a proper query method.
    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<Candidate>> getCandidatesByElection(@PathVariable Long electionId) {
        List<Candidate> candidates = candidateRepository.findByElectionId(electionId);
        return ResponseEntity.ok(candidates);
    }

    // Keep a proper single candidate lookup by candidate ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCandidateById(@PathVariable Long id) {
        Candidate c = candidateRepository.findById(id).orElse(null);
        if (c == null) return ResponseEntity.status(404).body("Candidate not found");
        return ResponseEntity.ok(c);
    }
}