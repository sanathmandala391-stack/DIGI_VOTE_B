package VotingSystem.demo.Repository;

import VotingSystem.demo.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    boolean existsByUserIdAndElectionId(Long id, Long id1);

    long countByElectionIdAndCandidateId(Long electionId, Long id);
}
