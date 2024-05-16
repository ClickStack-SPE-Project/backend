package org.example.clickstack.Controller;

import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.AlbumModel;
import org.example.clickstack.Repository.AlbumRepository;
import org.example.clickstack.Repository.UserRepository;
import org.example.clickstack.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/v1/album")
public class Albums {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;


    @GetMapping("/getAllAlbum")
    public ResponseEntity<List<AlbumModel>> getAllAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        try {

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
            return ResponseEntity.ok(viewAlbum);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/getAlbum/{name}")
    public ResponseEntity<AlbumModel> getAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String name){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            Album album = albumRepository.findAlbumByNameAndUserEmail(name,user.get().getEmail());
            AlbumModel albumModel = AlbumModel.builder()
                    .name(album.getName())
                    .description(album.getDescription())
                    .build();
            return ResponseEntity.ok(albumModel);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PostMapping("/createAlbum")
    public ResponseEntity<String> createAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestBody AlbumModel albumModel){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            Album album = Album.builder()
                    .name(albumModel.getName())
                    .Description(albumModel.getDescription())
                    .user(user.get())
                    .build();
            albumRepository.save(album);
            return ResponseEntity.ok("Album Successfully Created");
        }
        catch (Exception ignored){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Album Not Created");
        }
    }
    @PutMapping("/updateAlbum/{name}")
    public ResponseEntity<String> updateAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String name,@RequestBody AlbumModel albumModel){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            Album album = albumRepository.findAlbumByNameAndUserEmail(name,user.get().getEmail());
            album.setName(albumModel.getName());
            album.setDescription(albumModel.getDescription());
            albumRepository.save(album);
            return ResponseEntity.ok("Album Successfully Updated");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Album Not Updated");
        }
    }
    @DeleteMapping("/deleteAlbum/{name}")
    public ResponseEntity<String> deleteAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String name){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            Album album = albumRepository.findAlbumByNameAndUserEmail(name,user.get().getEmail());
            albumRepository.delete(album);
            return ResponseEntity.ok("Album Successfully Deleted");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Album Not Deleted");
        }
    }
}
