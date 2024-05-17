package org.example.clickstack.service;

import org.assertj.core.api.Assertions;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.UserModel;
import org.example.clickstack.Repository.UserRepository;
import org.example.clickstack.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private String userEmail = "electric@gmail.com";
    @BeforeEach
    public void init() {
        user = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();
    }

    @Test
    public void UserService_FindByEmail_ReturnUserModel() {


        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));

        UserModel userReturn = userService.getUser(userEmail);

        Assertions.assertThat(userReturn).isNotNull();
    }

    @Test
    public void UserService_UpdateUser_ReturnString() {

        UserModel usermodel = UserModel.builder().name("updatepikachu").email("updateelectric@gmail.com").build();

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(user)).thenReturn(user);

        String updateReturn = userService.editUser(userEmail,usermodel);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void UserService_DeleteUserByEmail_ReturnString() {

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        doNothing().when(userRepository).delete(user);

        assertAll(() -> userService.deleteUser(userEmail));
    }
}
