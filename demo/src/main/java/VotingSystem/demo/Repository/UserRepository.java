//package VotingSystem.demo.Repository;
//
//import VotingSystem.demo.models.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User,Long> {
//    Optional<User> findById(Long userId);
//   User findByEmail(String email);
//}



package VotingSystem.demo.Repository;

import VotingSystem.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// BUG FIX: Removed unused import of ResponseEntity — that belongs in controllers, not repositories.
// findById(Long) is already provided by JpaRepository so the duplicate declaration is removed.
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
