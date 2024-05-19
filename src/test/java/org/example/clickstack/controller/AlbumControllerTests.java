package org.example.clickstack.controller;

import org.example.clickstack.Controller.Users;
import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.AlbumModel;
import org.example.clickstack.Model.UserModel;
import org.example.clickstack.Service.AlbumService;
import org.example.clickstack.Service.UserService;
import org.example.clickstack.config.JwtService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = Users.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AlbumControllerTests {

    private User user;
    private Album myalbum;
    private AlbumModel albumModel;
    private String userEmail = "electric@gmail.com";
    private String token = "Bearer somerandomtoken";
    private String name = "pikachu";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @Autowired
    private AlbumService albumService;

    @BeforeEach
    public void init() {
        user = User.builder()
                .name("pikachu")
                .email("electric@gmail.com")
                .password("pikapikachu")
                .build();
        myalbum = Album.builder()
                .name("My Album")
                .Description("This is a sample album")
                .user(user)
                .build();
        albumModel = AlbumModel.builder()
                .name("My Album")
                .description("This is a sample album")
                .build();
    }

//    @Test
//    public void AlbumController_GetAllAlbum_ReturnResponseDto() throws Exception {
//        AlbumModel responseDto = AlbumModel.builder()
//                .name("My Album")
//                .description("This is a sample album")
//                .build();
//        when(albumService.getAllAlbums(token)).thenReturn(List.of(responseDto));
//
//        ResultActions response = mockMvc.perform(get("/api/v1/album/getAllAlbum")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
}
