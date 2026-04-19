package VotingSystem.demo.controllers;

import VotingSystem.demo.Service.AuthService;
import VotingSystem.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
  private   AuthService authService;


    @GetMapping("/profile")
    public ResponseEntity<User> GetUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal(); // ✅ already User
        System.out.println("Email: " + user.getEmail());
        return ResponseEntity.ok(user);         // send full user
    }



}
