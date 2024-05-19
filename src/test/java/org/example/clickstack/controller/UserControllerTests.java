package org.example.clickstack.controller;

import org.example.clickstack.Controller.Users;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.UserModel;
import org.example.clickstack.Service.UserService;
import org.example.clickstack.config.JwtService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest (controllers = Users.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    private String userEmail = "electric@gmail.com";
    private String token = "Bearer somerandomtoken";
    private User user;
    private UserModel userModel;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void init() {
        user = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();
        userModel = UserModel.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .build();
    }
//    @Test
//    public void UserController_UserDetail_ReturnUserModel() throws Exception {
//        when(userService.getUser(userEmail)).thenReturn(userModel);
//        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
//        ResultActions response = mockMvc.perform(get("/api/pokemon/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userModel)));
//
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(userModel.getName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userModel.getEmail())));
//    }
}
