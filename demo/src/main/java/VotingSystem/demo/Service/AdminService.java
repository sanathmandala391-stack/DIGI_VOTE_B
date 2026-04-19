//package VotingSystem.demo.Service;
//
//
//import VotingSystem.demo.Repository.CandidateRepository;
//import VotingSystem.demo.Repository.ElectionRepository;
//import VotingSystem.demo.models.Candidate;
//import VotingSystem.demo.models.Election;
//import VotingSystem.demo.models.ElectionStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AdminService {
//
//    @Autowired
//    private ElectionRepository electionRepository;
//    @Autowired
//    private CandidateRepository candidateRepository;
//    @PostMapping("/election")
//    public ResponseEntity<?> createElection(Election election){
//        election.setElectionStatus(ElectionStatus.UPCOMING);
//        election.setStartTime(null);
//        election.setEndTime(null);
//        electionRepository.save(election);
//        return ResponseEntity.ok("Election Created Sucessfully..");
//    }
//
//    public Candidate addCandidate(Long electionId, Candidate candidate){
//        Optional<Election> electionOPT=electionRepository.findById(electionId);
//        if(electionOPT.isEmpty()){
//            throw new RuntimeException("Election Not Found..");
//
//        }
//        Election election=electionOPT.get();
//        candidate.setElection(election);
//
//        return candidateRepository.save(candidate);
//    }
//
//    public Election startElection(Long electionId) {
//        Optional<Election> electionOpt = electionRepository.findById(electionId);
//        if (electionOpt.isEmpty()) {
//            throw new RuntimeException("Election not found");
//        }
//
//        Election election = electionOpt.get();
//        if (election.getElectionStatus() != ElectionStatus.UPCOMING) {
//            throw new RuntimeException("Election cannot be started");
//        }
//
//        election.setElectionStatus(ElectionStatus.ACTIVE);
//        election.setStartTime(LocalDateTime.now());
//
//        return electionRepository.save(election);
//    }
//
//    // ✅ End election → set status CLOSED & endTime
//    public Election endElection(Long electionId) {
//        Optional<Election> electionOpt = electionRepository.findById(electionId);
//        if (electionOpt.isEmpty()) {
//            throw new RuntimeException("Election not found");
//        }
//
//        Election election = electionOpt.get();
//        if (election.getElectionStatus() != ElectionStatus.ACTIVE) {
//            throw new RuntimeException("Election cannot be ended");
//        }
//
//        election.setElectionStatus(ElectionStatus.CLOSED);
//        election.setEndTime(LocalDateTime.now());
//
//        return electionRepository.save(election);
//    }
//
//    // Optional: list all elections (admin)
//    public List<Election> getAllElections() {
//        return electionRepository.findAll();
//    }
//
//
//}

//
//package VotingSystem.demo.Service;
//
//import VotingSystem.demo.Repository.CandidateRepository;
//import VotingSystem.demo.Repository.ElectionRepository;
//import VotingSystem.demo.models.Candidate;
//import VotingSystem.demo.models.Election;
//import VotingSystem.demo.models.ElectionStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AdminService {
//
//    @Autowired
//    private ElectionRepository electionRepository;
//
//    @Autowired
//    private CandidateRepository candidateRepository;
//
//    // BUG FIX: Removed @PostMapping("/election") from here — that annotation belongs
//    // only in a @RestController, not in a @Service. Having it here caused a Spring
//    // context warning and was completely meaningless.
//    public ResponseEntity<?> createElection(Election election) {
//        election.setElectionStatus(ElectionStatus.UPCOMING);
//        election.setStartTime(null);
//        election.setEndTime(null);
//        electionRepository.save(election);
//        return ResponseEntity.ok("Election Created Successfully.");
//    }
//
//    public Candidate addCandidate(Long electionId, Candidate candidate) {
//        Optional<Election> electionOPT = electionRepository.findById(electionId);
//        if (electionOPT.isEmpty()) {
//            throw new RuntimeException("Election Not Found.");
//        }
//        Election election = electionOPT.get();
//        candidate.setElection(election);
//        return candidateRepository.save(candidate);
//    }
//
//    public Election startElection(Long electionId) {
//        Optional<Election> electionOpt = electionRepository.findById(electionId);
//        if (electionOpt.isEmpty()) {
//            throw new RuntimeException("Election not found");
//        }
//        Election election = electionOpt.get();
//        if (election.getElectionStatus() != ElectionStatus.UPCOMING) {
//            throw new RuntimeException("Election cannot be started — must be in UPCOMING status");
//        }
//        election.setElectionStatus(ElectionStatus.ACTIVE);
//        election.setStartTime(LocalDateTime.now());
//        return electionRepository.save(election);
//    }
//
//    public Election endElection(Long electionId) {
//        Optional<Election> electionOpt = electionRepository.findById(electionId);
//        if (electionOpt.isEmpty()) {
//            throw new RuntimeException("Election not found");
//        }
//        Election election = electionOpt.get();
//        if (election.getElectionStatus() != ElectionStatus.ACTIVE) {
//            throw new RuntimeException("Election cannot be ended — must be in ACTIVE status");
//        }
//        election.setElectionStatus(ElectionStatus.CLOSED);
//        election.setEndTime(LocalDateTime.now());
//        return electionRepository.save(election);
//    }
//
//    public List<Election> getAllElections() {
//        return electionRepository.findAll();
//    }
//}




package VotingSystem.demo.Service;

import VotingSystem.demo.Repository.CandidateRepository;
import VotingSystem.demo.Repository.ElectionRepository;
import VotingSystem.demo.models.Candidate;
import VotingSystem.demo.models.Election;
import VotingSystem.demo.models.ElectionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    // BUG FIX: Removed @PostMapping("/election") from here — that annotation belongs
    // only in a @RestController, not in a @Service. Having it here caused a Spring
    // context warning and was completely meaningless.
    public ResponseEntity<?> createElection(Election election) {
        election.setElectionStatus(ElectionStatus.UPCOMING);
        election.setStartTime(null);
        election.setEndTime(null);
        electionRepository.save(election);
        return ResponseEntity.ok("Election Created Successfully.");
    }

    public Candidate addCandidate(Long electionId, Candidate candidate) {
        Optional<Election> electionOPT = electionRepository.findById(electionId);
        if (electionOPT.isEmpty()) {
            throw new RuntimeException("Election Not Found.");
        }
        Election election = electionOPT.get();
        candidate.setElection(election);
        return candidateRepository.save(candidate);
    }

    public Election startElection(Long electionId) {
        Optional<Election> electionOpt = electionRepository.findById(electionId);
        if (electionOpt.isEmpty()) {
            throw new RuntimeException("Election not found");
        }
        Election election = electionOpt.get();
        if (election.getElectionStatus() != ElectionStatus.UPCOMING) {
            throw new RuntimeException("Election cannot be started — must be in UPCOMING status");
        }
        election.setElectionStatus(ElectionStatus.ACTIVE);
        election.setStartTime(LocalDateTime.now());
        return electionRepository.save(election);
    }

    public Election endElection(Long electionId) {
        Optional<Election> electionOpt = electionRepository.findById(electionId);
        if (electionOpt.isEmpty()) {
            throw new RuntimeException("Election not found");
        }
        Election election = electionOpt.get();
        if (election.getElectionStatus() != ElectionStatus.ACTIVE) {
            throw new RuntimeException("Election cannot be ended — must be in ACTIVE status");
        }
        election.setElectionStatus(ElectionStatus.CLOSED);
        election.setEndTime(LocalDateTime.now());
        return electionRepository.save(election);
    }

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }
}