//package VotingSystem.demo.Repository;
//
//import VotingSystem.demo.models.Candidate;
//import jakarta.persistence.Id;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface CandidateRepository extends JpaRepository<Candidate,Long> {
//}
package VotingSystem.demo.Repository;

import VotingSystem.demo.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// BUG FIX: Added findByElectionId() so CandidateController can fetch
// all candidates for a specific election. Previously this method was missing,
// forcing the controller to use a wrong findById(electionId) call.
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByElectionId(Long electionId);
}