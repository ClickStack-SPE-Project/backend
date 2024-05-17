package org.example.clickstack.Service;

import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.AlbumModel;
import org.example.clickstack.Repository.AlbumRepository;
import org.example.clickstack.Repository.UserRepository;
import org.example.clickstack.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;

    public List<AlbumModel> getAllAlbums(String token) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        List<Album> albums = albumRepository.findByUser(user.get());
        List<AlbumModel> viewAlbum = new ArrayList<>();
        for(Album album : albums) {
            AlbumModel albumModel = AlbumModel.builder()
                    .name(album.getName())
                    .description(album.getDescription())
                    .build();
            viewAlbum.add(albumModel);
        }
        return viewAlbum;
    }

    public AlbumModel getAlbum(String token, String name) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        Album album = albumRepository.findAlbumByNameAndUserEmail(name,user.get().getEmail());
        AlbumModel albumModel = AlbumModel.builder()
                .name(album.getName())
                .description(album.getDescription())
                .build();
        return albumModel;
    }

    public String createAlbum(String token, AlbumModel albumModel) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        Album album = Album.builder()
                .name(albumModel.getName())
                .Description(albumModel.getDescription())
                .user(user.get())
                .build();
        albumRepository.save(album);
        return "Album Successfully Created";
    }

    public String updateAlbum(String token,String name, AlbumModel albumModel) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        Album album = albumRepository.findAlbumByNameAndUserEmail(name,user.get().getEmail());
        album.setName(albumModel.getName());
        album.setDescription(albumModel.getDescription());
        albumRepository.save(album);
        return "Album Successfully Updated";
    }

    public String deleteAlbum(String token, String name) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        Album album = albumRepository.findAlbumByNameAndUserEmail(name,user.get().getEmail());
        albumRepository.delete(album);
        return "Album Successfully Deleted";
    }
}
