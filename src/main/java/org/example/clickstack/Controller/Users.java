package org.example.clickstack.Controller;


import org.example.clickstack.Model.UserModel;
import org.example.clickstack.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.clickstack.config.JwtService;


@RestController
@RequestMapping("/api/v1/user")
public class Users {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @GetMapping("/getUser/{user_email}")
    public ResponseEntity<UserModel> getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String user_email){
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        if(loggedInUserEmail.equals(user_email)) {
            return ResponseEntity.ok(userService.getUser(user_email));
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
            return ResponseEntity.ok(userService.editUser(user_email,userModel));
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
            return ResponseEntity.ok(userService.deleteUser(user_email));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("You are Unaurthorized");
        }
    }
}
