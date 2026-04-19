//package VotingSystem.demo.controllers;
//
//import VotingSystem.demo.Service.AdminService;
//import VotingSystem.demo.models.Candidate;
//import VotingSystem.demo.models.Election;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/admin")
//public class AdminController {
//
//
//    @Autowired
//    private AdminService adminService;
//
//
//    public ResponseEntity<?> createElection(@RequestBody Election election){
//        return adminService.createElection(election);
//
//    }
//
//    @PostMapping("/candidate")
//    public ResponseEntity<Candidate> addCandidate(@RequestParam Long electionId,
//                                                  @RequestBody Candidate candidate) {
//        Candidate c = adminService.addCandidate(electionId, candidate);
//        return ResponseEntity.ok(c);
//    }
//
//    // Start election → set status ACTIVE & startTime
//    @PutMapping("/election/start/{id}")
//    public ResponseEntity<?> startElection(@PathVariable Long id) {
//        Election e = adminService.startElection(id);
//        return ResponseEntity.ok(e);
//    }
//
//    // End election → set status CLOSED & endTime
//    @PutMapping("/election/end/{id}")
//    public ResponseEntity<?> endElection(@PathVariable Long id) {
//        Election e = adminService.endElection(id);
//        return ResponseEntity.ok(e);
//    }
//
//
//
//
//}

//
//package VotingSystem.demo.controllers;
//
//import VotingSystem.demo.Service.AdminService;
//import VotingSystem.demo.models.Candidate;
//import VotingSystem.demo.models.Election;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Autowired
//    private AdminService adminService;
//
//    // BUG FIX: was completely missing @PostMapping("/election") annotation!
//    // Without it, this endpoint was NEVER reachable — 404 for all election creation requests.
//    @PostMapping("/election")
//    public ResponseEntity<?> createElection(@RequestBody Election election) {
//        return adminService.createElection(election);
//    }
//
//    @PostMapping("/candidate")
//    public ResponseEntity<Candidate> addCandidate(@RequestParam Long electionId,
//                                                  @RequestBody Candidate candidate) {
//        Candidate c = adminService.addCandidate(electionId, candidate);
//        return ResponseEntity.ok(c);
//    }
//
//    // Start election → set status ACTIVE & startTime
//    @PutMapping("/election/start/{id}")
//    public ResponseEntity<?> startElection(@PathVariable Long id) {
//        Election e = adminService.startElection(id);
//        return ResponseEntity.ok(e);
//    }
//
//    // End election → set status CLOSED & endTime
//    @PutMapping("/election/end/{id}")
//    public ResponseEntity<?> endElection(@PathVariable Long id) {
//        Election e = adminService.endElection(id);
//        return ResponseEntity.ok(e);
//    }
//
//    // Get all elections (admin view)
//    @GetMapping("/elections")
//    public ResponseEntity<?> getAllElections() {
//        return ResponseEntity.ok(adminService.getAllElections());
//    }
//}





package VotingSystem.demo.controllers;

import VotingSystem.demo.Service.AdminService;
import VotingSystem.demo.models.Candidate;
import VotingSystem.demo.models.Election;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // BUG FIX: was completely missing @PostMapping("/election") annotation!
    // Without it, this endpoint was NEVER reachable — 404 for all election creation requests.
    @PostMapping("/election")
    public ResponseEntity<?> createElection(@RequestBody Election election) {
        return adminService.createElection(election);
    }

    @PostMapping("/candidate")
    public ResponseEntity<Candidate> addCandidate(@RequestParam Long electionId,
                                                  @RequestBody Candidate candidate) {
        Candidate c = adminService.addCandidate(electionId, candidate);
        return ResponseEntity.ok(c);
    }

    // Start election → set status ACTIVE & startTime
    @PutMapping("/election/start/{id}")
    public ResponseEntity<?> startElection(@PathVariable Long id) {
        Election e = adminService.startElection(id);
        return ResponseEntity.ok(e);
    }

    // End election → set status CLOSED & endTime
    @PutMapping("/election/end/{id}")
    public ResponseEntity<?> endElection(@PathVariable Long id) {
        Election e = adminService.endElection(id);
        return ResponseEntity.ok(e);
    }

    // Get all elections (admin view)
    @GetMapping("/elections")
    public ResponseEntity<?> getAllElections() {
        return ResponseEntity.ok(adminService.getAllElections());
    }
}