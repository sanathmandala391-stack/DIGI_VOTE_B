package VotingSystem.demo.controllers;


import VotingSystem.demo.Repository.ElectionRepository;
import VotingSystem.demo.Repository.VoteRepository;
import VotingSystem.demo.models.Candidate;
import VotingSystem.demo.models.Election;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/results")
public class ResultsController {


    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @GetMapping("/{electionId}")
    public ResponseEntity<?> OverallResults(@PathVariable Long electionId) {

        Election election = electionRepository.findById(electionId).orElse(null);
        if (election == null) {
            return ResponseEntity.status(404).body("Election not found");
        }

        Map<String, Long> results = new HashMap<>();
        for (Candidate candidate : election.getCandidates()) {
            long count = voteRepository.countByElectionIdAndCandidateId(electionId, candidate.getId());
            results.put(candidate.getName(), count);

        }
        return ResponseEntity.ok(results);
    }
}


