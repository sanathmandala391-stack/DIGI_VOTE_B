package VotingSystem.demo.Repository;

import VotingSystem.demo.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election,Long> {
}
