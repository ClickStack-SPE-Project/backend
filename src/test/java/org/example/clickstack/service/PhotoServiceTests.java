package org.example.clickstack.service;

import org.assertj.core.api.Assertions;
import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.Photos;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.AlbumModel;
import org.example.clickstack.Model.PhotosModel;
import org.example.clickstack.Repository.AlbumRepository;
import org.example.clickstack.Repository.PhotosRepository;
import org.example.clickstack.Repository.UserRepository;
import org.example.clickstack.Service.AlbumService;
import org.example.clickstack.Service.PhotoService;
import org.example.clickstack.Service.StorageService;
import org.example.clickstack.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTests {
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PhotosRepository photosRepository;
    @Mock
    private StorageService service;
    @InjectMocks
    private PhotoService photoService;

    private User user;
    private Album myalbum;
    private Photos myphoto;
    private String userEmail = "electric@gmail.com";
    private String token = "Bearer somerandomtoken";
    private String albumName = "My Album";
    private String photoName = "My Photo";
    private URL link = new URL("https://example.com");

    public PhotoServiceTests() throws MalformedURLException {
    }

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
          myphoto = Photos.builder()
                .name("My Photo")
                .link("Some Link")
                .build();
    }

    @Test
    public void PhotoService_GetAllPhotos_ReturnPhotoModel() throws MalformedURLException {

        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(albumRepository.findByUser(user)).thenReturn(List.of(myalbum));
        when(photosRepository.findPhotosByAlbumName(albumName)).thenReturn(List.of(myphoto));
        when(service.getURL(myphoto.getName())).thenReturn(link);

        List<PhotosModel> photos = photoService.getAllPhotos(token,albumName);

        Assertions.assertThat(photos).isNotNull();
    }

    @Test
    public void PhotoService_GetPhoto_ReturnPhotoModel() throws MalformedURLException {

        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(albumRepository.findByUser(user)).thenReturn(List.of(myalbum));
        when(photosRepository.findPhotosByNameAndAlbumName(photoName,albumName)).thenReturn(myphoto);
        when(service.getURL(myphoto.getName())).thenReturn(link);

        PhotosModel photos = photoService.getPhoto(token,albumName,photoName);

        Assertions.assertThat(photos).isNotNull();
    }

    @Test
    public void PhotoService_CreatePhoto_ReturnString() throws MalformedURLException {


        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(albumRepository.findByUser(user)).thenReturn(List.of(myalbum));
        when(photosRepository.findPhotosByNameAndAlbumName(photoName,albumName)).thenReturn(myphoto);
        when(service.getURL(myphoto.getName())).thenReturn(link);

        PhotosModel photos = photoService.getPhoto(token,albumName,photoName);

        Assertions.assertThat(photos).isNotNull();
    }
    @Test
    public void PhotoService_DeletePhoto_ReturnString() {

        when(jwtService.extractUsername(token.split(" ")[1])).thenReturn(userEmail);
        when(albumRepository.findAlbumByNameAndUserEmail(albumName,user.getEmail())).thenReturn(myalbum);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.ofNullable(user));
        when(photosRepository.findPhotosByNameAndAlbumName(photoName,albumName)).thenReturn(myphoto);
        when(service.deleteFile(myphoto.getName())).thenReturn(true);
        doNothing().when(photosRepository).delete(myphoto);

        assertAll(() -> photoService.deletePhoto(token,albumName,photoName));
    }
}
