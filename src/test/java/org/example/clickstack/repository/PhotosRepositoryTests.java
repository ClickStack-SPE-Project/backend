package org.example.clickstack.repository;
import org.assertj.core.api.Assertions;

import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Repository.AlbumRepository;
import org.example.clickstack.Repository.PhotosRepository;
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
public class PhotosRepositoryTests {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotosRepository photosRepository;

//    @Test
//    public void AlbumRepository_SaveAll_ReturnSavedAlbum() {
//
//        //Arrange
//        User myuser = User.builder()
//                .name("pikachu")
//                .email("electric@gmail.com")
//                .password("pikapikachu")
//                .build();
//        User savedUser = userRepository.save(myuser);
//
//        Album myalbum = Album.builder()
//                .name("My Album")
//                .Description("This is a sample album")
//                .user(savedUser)
//                .build();
//        //Act
//        Album savedAlbum = albumRepository.save(myalbum);
//        //Assert
//        Assertions.assertThat(savedAlbum).isNotNull();
//        Assertions.assertThat(savedAlbum.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void AlbumRepository_GetAll_ReturnMoreThenOneAlbum() {
//        User myuser = User.builder()
//                .name("pikachu")
//                .email("electric@gmail.com")
//                .password("pikapikachu")
//                .build();
//        User savedUser = userRepository.save(myuser);
//
//        Album myalbum = Album.builder()
//                .name("My Album")
//                .Description("This is a sample album")
//                .user(savedUser)
//                .build();
//
//        Album myalbum2 = Album.builder()
//                .name("My Album2")
//                .Description("This is a sample album2")
//                .user(savedUser)
//                .build();
//        albumRepository.save(myalbum);
//        albumRepository.save(myalbum2);
//
//        List<Album> albumList = albumRepository.findAll();
//
//        Assertions.assertThat(albumList).isNotNull();
//        Assertions.assertThat(albumList.size()).isEqualTo(2);
//    }
//
//    @Test
//    public void AlbumRepository_FindById_ReturnAlbum() {
//        User user = User.builder()
//                .name("pikachu")
//                .email("electric@gmail.com")
//                .password("pikapikachu")
//                .build();
//
//        User savedUser = userRepository.save(user);
//
//        Album myalbum = Album.builder()
//                .name("My Album")
//                .Description("This is a sample album")
//                .user(savedUser)
//                .build();
//        albumRepository.save(myalbum);
//
//        Album albumList = albumRepository.findById(myalbum.getId()).get();
//
//        Assertions.assertThat(albumList).isNotNull();
//    }
//
//    @Test
//    public void AlbumRepository_FindByUser_ReturnAlbumsNotNull() {
//        User user = User.builder()
//                .name("pikachu")
//                .email("electric@gmail.com")
//                .password("pikapikachu")
//                .build();
//
//        User savedUser = userRepository.save(user);
//
//        Album myalbum = Album.builder()
//                .name("My Album")
//                .Description("This is a sample album")
//                .user(savedUser)
//                .build();
//        albumRepository.save(myalbum);
//
//        List<Album> albumList = albumRepository.findByUser(myalbum.getUser());
//
//        Assertions.assertThat(albumList).isNotNull();
//        Assertions.assertThat(albumList).isNotEmpty();
//        Assertions.assertThat(albumList.get(0)).isEqualTo(myalbum);
//    }
//
//    @Test
//    public void AlbumRepository_findAlbumByName_ReturnAlbumNotNull() {
//        User user = User.builder()
//                .name("pikachu")
//                .email("electric@gmail.com")
//                .password("pikapikachu")
//                .build();
//
//        User savedUser = userRepository.save(user);
//
//        Album myalbum = Album.builder()
//                .name("My Album")
//                .Description("This is a sample album")
//                .user(savedUser)
//                .build();
//        albumRepository.save(myalbum);
//
//        Album albumList = albumRepository.findAlbumByName(myalbum.getName());
//
//        Assertions.assertThat(albumList).isNotNull();
//    }
//
//    @Test
//    public void AlbumRepository_findAlbumByNameAndUserEmail_ReturnAlbumNotNull() {
//        User user = User.builder()
//                .name("pikachu")
//                .email("electric@gmail.com")
//                .password("pikapikachu")
//                .build();
//
//        User savedUser = userRepository.save(user);
//
//        Album myalbum = Album.builder()
//                .name("My Album")
//                .Description("This is a sample album")
//                .user(savedUser)
//                .build();
//        albumRepository.save(myalbum);
//
//        Album albumList = albumRepository.findAlbumByNameAndUserEmail(myalbum.getName(),myalbum.getUser().getEmail());
//
//        Assertions.assertThat(albumList).isNotNull();
//    }
//
//    @Test
//    public void AlbumRepository_UpdateAlbum_ReturnAlbumNotNull() {
//        User user = User.builder()
//                .name("pikachu")
//                .email("electric@gmail.com")
//                .password("pikapikachu")
//                .build();
//
//        User savedUser = userRepository.save(user);
//
//        Album myalbum = Album.builder()
//                .name("My Album")
//                .Description("This is a sample album")
//                .user(savedUser)
//                .build();
//        albumRepository.save(myalbum);
//
//        Album albumSave = albumRepository.findById(myalbum.getId()).get();
//        albumSave.setDescription("This is an update to sample album");
//        albumSave.setName("MY New Album");
//
//        Album updatedUser = albumRepository.save(albumSave);
//
//        Assertions.assertThat(updatedUser.getName()).isNotNull();
//        Assertions.assertThat(updatedUser.getDescription()).isNotNull();
//    }
//
//    @Test
//    public void AlbumRepository_AlbumDelete_ReturnAlbumIsEmpty() {
//        User user = User.builder()
//                .name("pikachu")
//                .email("electric@gmail.com")
//                .password("pikapikachu")
//                .build();
//
//        User savedUser = userRepository.save(user);
//
//        Album myalbum = Album.builder()
//                .name("My Album")
//                .Description("This is a sample album")
//                .user(savedUser)
//                .build();
//        albumRepository.save(myalbum);
//
//        albumRepository.deleteById(myalbum.getId());
//        Optional<Album> albumReturn = albumRepository.findById(myalbum.getId());
//
//        Assertions.assertThat(albumReturn).isEmpty();
//    }

}
