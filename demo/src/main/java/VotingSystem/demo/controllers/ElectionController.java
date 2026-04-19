package VotingSystem.demo.controllers;


import VotingSystem.demo.Service.ElectionService;
import VotingSystem.demo.models.Election;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ElectionController {


    @Autowired
    private ElectionService electionService;


    @GetMapping("/elections")
    public ResponseEntity<List<Election>> getAllElections(){
      List<Election> elections=electionService.getAllElections();
      return ResponseEntity.ok().body(elections);

    }

    @GetMapping("/elections/{id}")
    public ResponseEntity<?> getElectionById(@PathVariable Long id){
        Election election=electionService.getElectionById(id);
    if(election==null){
        return ResponseEntity.status(404).body("Election not found");
    }

        return ResponseEntity.ok(election);
    }



}
