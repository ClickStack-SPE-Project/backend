package org.example.clickstack.Service;

import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.User;
import org.example.clickstack.Model.PhotosModel;
import org.example.clickstack.Repository.AlbumRepository;
import org.example.clickstack.Repository.PhotosRepository;
import org.example.clickstack.Repository.UserRepository;
import org.example.clickstack.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private PhotosRepository photosRepository;
    @Autowired
    private StorageService service;

    public List<PhotosModel> getAllPhotos(String token,String album) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        List<Album> albums = albumRepository.findByUser(user.get());
        boolean albumExists = albums.stream().anyMatch(a -> a.getName().equals(album));
        if (!albumExists) {
            return null;
        }
        List<org.example.clickstack.Entity.Photos> photos = photosRepository.findPhotosByAlbumName(album);
        List<PhotosModel> allPhotos = new ArrayList<>();
        for(org.example.clickstack.Entity.Photos photo:photos){
            PhotosModel viewPic = PhotosModel.builder()
                    .name(photo.getName())
                    .link(service.getURL(photo.getName()))
                    .build();
            allPhotos.add(viewPic);
        }
        return allPhotos;
    }

    public PhotosModel getPhoto(String token,String album,String name) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        List<Album> albums = albumRepository.findByUser(user.get());
        boolean albumExists = albums.stream().anyMatch(a -> a.getName().equals(album));
        if (!albumExists) {
            return null;
        }
        org.example.clickstack.Entity.Photos photo = photosRepository.findPhotosByNameAndAlbumName(name,album);
        PhotosModel viewPic = PhotosModel.builder()
                .name(photo.getName())
                .link(service.getURL(photo.getName()))
                .build();

        return viewPic;
    }

    public String createPhoto(String token, String album, MultipartFile imageFile) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        Album findalbum = albumRepository.findAlbumByNameAndUserEmail(album,user.get().getEmail());
        if (findalbum==null) {
            return null;
        }

        org.example.clickstack.Entity.Photos photo = org.example.clickstack.Entity.Photos.builder()
                .name(service.uploadFile(imageFile))
                .album(albumRepository.findAlbumByName(album))
                .build();
        photosRepository.save(photo);
        return "Photo Successfully Created";
    }

    public String deletePhoto(String token,String album,String name) {
        String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        Album findalbum = albumRepository.findAlbumByNameAndUserEmail(album,user.get().getEmail());
        if (findalbum==null) {
            return null;
        }
        org.example.clickstack.Entity.Photos photo = photosRepository.findPhotosByNameAndAlbumName(name,album);
        if(service.deleteFile(name)) {
            photosRepository.delete(photo);
            return "Photo Successfully Deleted";
        }
        else {
            return "Photo Not Deleted";
        }
    }
}
