package org.example.clickstack.service;

import org.assertj.core.api.Assertions;
import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.AlbumModel;
import org.example.clickstack.Model.UserModel;
import org.example.clickstack.Repository.AlbumRepository;
import org.example.clickstack.Repository.UserRepository;
import org.example.clickstack.Service.AlbumService;
import org.example.clickstack.Service.UserService;
import org.example.clickstack.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTests {
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AlbumService albumService;

    private User user;
    private Album myalbum;
    private AlbumModel albumModel;
    private String userEmail = "electric@gmail.com";
    private String token = "Bearer somerandomtoken";
    private String name = "pikachu";

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

    @Test
    public void AlbumService_GetAllAlbums_ReturnAlbumModel() {
        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(albumRepository.findByUser(user)).thenReturn(List.of(myalbum));
        List<AlbumModel> albums = albumService.getAllAlbums(token);

        Assertions.assertThat(albums).isNotNull();
    }

    @Test
    public void AlbumService_GetAlbum_ReturnAlbumModel() {
        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(albumRepository.findAlbumByNameAndUserEmail(name,user.getEmail())).thenReturn(myalbum);
        AlbumModel album = albumService.getAlbum(token,name);

        Assertions.assertThat(album).isNotNull();
    }

    @Test
    public void AlbumService_CreateAlbum_ReturnString() {

        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(albumRepository.save(myalbum)).thenReturn(myalbum);

        String album = albumService.createAlbum(token,albumModel);

        Assertions.assertThat(album).isNotNull();
    }

    @Test
    public void AlbumService_UpdateAlbum_ReturnString() {

        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(albumRepository.findAlbumByNameAndUserEmail(name,user.getEmail())).thenReturn(myalbum);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(albumRepository.save(myalbum)).thenReturn(myalbum);

        String album = albumService.updateAlbum(token,name,albumModel);

        Assertions.assertThat(album).isNotNull();
    }

    @Test
    public void AlbumService_DeleteAlbum_ReturnString() {

        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(albumRepository.findAlbumByNameAndUserEmail(name,user.getEmail())).thenReturn(myalbum);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        doNothing().when(albumRepository).delete(myalbum);

        assertAll(() -> albumService.deleteAlbum(token,name));
    }
}
