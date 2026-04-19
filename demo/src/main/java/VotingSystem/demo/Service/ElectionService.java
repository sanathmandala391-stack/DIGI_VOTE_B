package VotingSystem.demo.Service;

import VotingSystem.demo.Repository.ElectionRepository;
import VotingSystem.demo.models.Election;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionService {

    @Autowired
   private ElectionRepository electionRepository;


    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }


    public Election getElectionById(Long id) {
        return electionRepository.findById(id).orElse(null);
    }
}
