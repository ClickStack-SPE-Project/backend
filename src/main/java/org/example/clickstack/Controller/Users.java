package org.example.clickstack.Controller;


import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.UserModel;
import org.example.clickstack.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.clickstack.config.JwtService;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class Users {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/getUser/{user_email}")
    public ResponseEntity<UserModel> getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String user_email){
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        if(loggedInUserEmail.equals(user_email)) {
            Optional<User> user = userRepository.findByEmail(user_email);
            UserModel userModel = UserModel.builder()
                    .name(user.get().getName())
                    .email(user.get().getEmail())
                    .build();
            return ResponseEntity.ok(userModel);
        }
        else
        {
            UserModel unaurthorized = UserModel.builder().build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(unaurthorized);
        }
    }
    @PutMapping("/editUser/{user_email}")
    public ResponseEntity<String> editUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String user_email,@RequestBody UserModel userModel){
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        if(loggedInUserEmail.equals(user_email)) {
            Optional<User> user = userRepository.findByEmail(user_email);
            user.get().setEmail(userModel.getEmail());
            user.get().setName(userModel.getName());
            userRepository.save(user.get());
            return ResponseEntity.ok("Updated Succesfully");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("You are Unaurthorized");
        }
    }
    @DeleteMapping("/deleteUser/{user_email}")
    public ResponseEntity<String> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String user_email){
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        if(loggedInUserEmail.equals(user_email)) {
            Optional<User> user = userRepository.findByEmail(user_email);
            userRepository.delete(user.get());
            return ResponseEntity.ok("Deleted Succesfully");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("You are Unaurthorized");
        }
    }
}
