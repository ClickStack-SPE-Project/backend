package org.example.clickstack.Service;

import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.UserModel;
import org.example.clickstack.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserModel getUser(String user_email) {
        Optional<User> user = userRepository.findByEmail(user_email);
        UserModel userModel = UserModel.builder()
                .name(user.get().getName())
                .email(user.get().getEmail())
                .build();
        return userModel;
    }
    public String editUser(String user_email,UserModel userModel) {
        Optional<User> user = userRepository.findByEmail(user_email);
        user.get().setEmail(userModel.getEmail());
        user.get().setName(userModel.getName());
        userRepository.save(user.get());
        return "Updated Succesfully";
    }
    public String deleteUser(String user_email) {
        Optional<User> user = userRepository.findByEmail(user_email);
        userRepository.delete(user.get());
        return "Deleted Succesfully";
    }
}
