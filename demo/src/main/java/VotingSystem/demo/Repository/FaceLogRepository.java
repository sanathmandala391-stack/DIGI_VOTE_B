package VotingSystem.demo.Repository;

import VotingSystem.demo.models.FaceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaceLogRepository extends JpaRepository<FaceLog,Long> {
}
