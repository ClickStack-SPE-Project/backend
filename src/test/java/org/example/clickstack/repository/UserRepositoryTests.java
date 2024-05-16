package org.example.clickstack.repository;

import org.assertj.core.api.Assertions;

import org.example.clickstack.Entity.User;
import org.example.clickstack.Repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveAll_ReturnSavedUser() {

        //Arrange
        User myuser = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();

        //Act
        User savedUser = userRepository.save(myuser);

        //Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_GetAll_ReturnMoreThenOneUser() {
        User user = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();
        User user2 = User.builder()
                .name("pikachu2")
                .email("electric2@gmail.com")
                .password("pikapikachu2")
                .build();

        userRepository.save(user);
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void UserRepository_FindById_ReturnUser() {
        User user = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();

        userRepository.save(user);

        User userList = userRepository.findById(user.getId()).get();

        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_FindByEmail_ReturnUserNotNull() {
        User user = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();

        userRepository.save(user);

        User userList = userRepository.findByEmail(user.getEmail()).get();

        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_UpdateUser_ReturnUserNotNull() {
        User user = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();

        userRepository.save(user);

        User userSave = userRepository.findById(user.getId()).get();
        userSave.setEmail("Electric@mail.com");
        userSave.setName("Raichu");

        User updatedUser = userRepository.save(userSave);

        Assertions.assertThat(updatedUser.getName()).isNotNull();
        Assertions.assertThat(updatedUser.getEmail()).isNotNull();
    }

    @Test
    public void UserRepository_UserDelete_ReturnUserIsEmpty() {
        User user = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();

        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> userReturn = userRepository.findById(user.getId());

        Assertions.assertThat(userReturn).isEmpty();
    }

}
