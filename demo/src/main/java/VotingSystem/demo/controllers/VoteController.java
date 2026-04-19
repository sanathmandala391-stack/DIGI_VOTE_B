package VotingSystem.demo.controllers;

import VotingSystem.demo.Repository.CandidateRepository;
import VotingSystem.demo.Repository.ElectionRepository;
import VotingSystem.demo.Repository.VoteRepository;
import VotingSystem.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping("/castVote")
    public ResponseEntity<?> castVote(@RequestBody VoteRequest voteRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        System.out.println("CAST VOTE >>> userId=" + user.getId()
                + " electionId=" + voteRequest.getElectionId()
                + " candidateId=" + voteRequest.getCandidateId());

        if (voteRequest.getElectionId() == null || voteRequest.getCandidateId() == null) {
            return ResponseEntity.badRequest().body("electionId and candidateId are required");
        }

        Election election = electionRepository.findById(voteRequest.getElectionId()).orElse(null);
        if (election == null || election.getElectionStatus() != ElectionStatus.ACTIVE) {
            return ResponseEntity.status(403).body("Election not active or not found");
        }

        boolean voted = voteRepository.existsByUserIdAndElectionId(user.getId(), election.getId());
        if (voted) return ResponseEntity.badRequest().body("Already voted in this election");

        Candidate candidate = candidateRepository.findById(voteRequest.getCandidateId()).orElse(null);
        if (candidate == null || !candidate.getElection().getId().equals(election.getId())) {
            return ResponseEntity.badRequest().body("Invalid candidate for this election");
        }

        Vote v = new Vote(user, election, candidate);
        voteRepository.save(v);
        return ResponseEntity.ok("Vote cast successfully");
    }
}
