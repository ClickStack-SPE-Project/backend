package org.example.clickstack.Controller;

import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.PhotosModel;
import org.example.clickstack.Repository.AlbumRepository;
import org.example.clickstack.Repository.PhotosRepository;
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
@RequestMapping("/api/v1/photos")
public class Photos {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private PhotosRepository photosRepository;
    @GetMapping("/getAllPhotos/{album}")
    public ResponseEntity<List<PhotosModel>> getAllPhotos(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String album){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            List<Album> albums = albumRepository.findByUser(user.get());
            boolean albumExists = albums.stream().anyMatch(a -> a.getName().equals(album));
            if (!albumExists) {
                return ResponseEntity.notFound().build();
            }
            List<org.example.clickstack.Entity.Photos> photos = photosRepository.findPhotosByAlbumName(album);
            List<PhotosModel> allPhotos = new ArrayList<>();
            for(org.example.clickstack.Entity.Photos photo:photos){
                PhotosModel viewPic = PhotosModel.builder()
                        .name(photo.getName())
                        .link(photo.getLink())
                        .build();
                allPhotos.add(viewPic);
            }
            return ResponseEntity.ok(allPhotos);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/getPhoto/{album}/{name}")
    public ResponseEntity<PhotosModel> getPhoto(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String album,@PathVariable String name){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            List<Album> albums = albumRepository.findByUser(user.get());
            boolean albumExists = albums.stream().anyMatch(a -> a.getName().equals(album));
            if (!albumExists) {
                return ResponseEntity.notFound().build();
            }
            org.example.clickstack.Entity.Photos photo = photosRepository.findPhotosByNameAndAlbumName(name,album);
            PhotosModel viewPic = PhotosModel.builder()
                    .name(photo.getName())
                    .link(photo.getLink())
                    .build();

            return ResponseEntity.ok(viewPic);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PostMapping("/createPhoto/{album}")
    public ResponseEntity<String> createPhoto(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String album,@RequestBody PhotosModel photosModel){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            Album findalbum = albumRepository.findAlbumByNameAndUserEmail(album,user.get().getEmail());
            if (findalbum==null) {
                return ResponseEntity.notFound().build();
            }
            org.example.clickstack.Entity.Photos photo = org.example.clickstack.Entity.Photos.builder()
                    .name(photosModel.getName())
                    .link(photosModel.getLink())
                    .album(albumRepository.findAlbumByName(album))
                    .build();
            photosRepository.save(photo);
            return ResponseEntity.ok("Photo Successfully Created");
        }
        catch (Exception ignored){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Album Not Created");
        }
    }
    @PutMapping("/updatePhoto/{album}/{name}")
    public ResponseEntity<String> updatePhoto(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String album,@PathVariable String name,@RequestBody PhotosModel photosModel){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            Album findalbum = albumRepository.findAlbumByNameAndUserEmail(album,user.get().getEmail());
            if (findalbum==null) {
                return ResponseEntity.notFound().build();
            }
            org.example.clickstack.Entity.Photos photo = photosRepository.findPhotosByNameAndAlbumName(name,album);

                    photo.setName(photosModel.getName());
                    photo.setLink(photosModel.getLink());
                    photo.setAlbum(albumRepository.findAlbumByName(album));
            photosRepository.save(photo);
            return ResponseEntity.ok("Photo Successfully Updated");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Photo Not Updated");
        }
    }
    @DeleteMapping("/deletePhoto/{album}/{name}")
    public ResponseEntity<String> deletePhoto(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String album,@PathVariable String name){
        try {
            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
            Album findalbum = albumRepository.findAlbumByNameAndUserEmail(album,user.get().getEmail());
            if (findalbum==null) {
                return ResponseEntity.notFound().build();
            }
            org.example.clickstack.Entity.Photos photo = photosRepository.findPhotosByNameAndAlbumName(name,album);
            photosRepository.delete(photo);
            return ResponseEntity.ok("Photo Successfully Deleted");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Photo Not Deleted");
        }
    }
}
