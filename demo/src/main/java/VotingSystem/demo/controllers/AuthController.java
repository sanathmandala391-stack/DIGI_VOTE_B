package VotingSystem.demo.controllers;

import VotingSystem.demo.Service.AuthService;
import VotingSystem.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> UserRegister(@RequestBody User u){
      return   authService.UserRegister(u);

    }

    @PostMapping("/login")
    public ResponseEntity<?> UserLogin(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        return authService.UserLogin(email, password);
    }
}
