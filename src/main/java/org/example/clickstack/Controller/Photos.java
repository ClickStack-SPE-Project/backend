package org.example.clickstack.Controller;

import org.example.clickstack.Model.PhotosModel;
import org.example.clickstack.Service.PhotoService;
import org.example.clickstack.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.util.List;


@RestController
@RequestMapping("/api/v1/photos")
public class Photos {

    @Autowired
    private StorageService service;
    @Autowired
    private PhotoService photoService;
    @GetMapping("/getAllPhotos/{album}")
    public ResponseEntity<List<PhotosModel>> getAllPhotos(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String album){
        try {
            List<PhotosModel> photos = photoService.getAllPhotos(token,album);
            if(photos==null || photos.size()==0){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(photos);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/getPhoto/{album}/{name}")
    public ResponseEntity<PhotosModel> getPhoto(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String album,@PathVariable String name){
        try {
            PhotosModel photo = photoService.getPhoto(token,album,name);
            if(photo==null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(photo);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PostMapping("/createPhoto/{album}")
    public ResponseEntity<String> createPhoto(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String album,@RequestParam(value = "imageFile") MultipartFile imageFile){
        try {
            String photo = photoService.createPhoto(token,album,imageFile);
            if(photo==null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(photo);
        }
        catch (Exception ignored){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Album Not Created");
        }
    }
//    @PutMapping("/updatePhoto/{album}/{name}")
//    public ResponseEntity<String> updatePhoto(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String album,@PathVariable String name,@RequestBody PhotosModel photosModel){
//        try {
//            String loggedInUserEmail = jwtService.extractUsername(token.split(" ")[1]);
//            Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
//            Album findalbum = albumRepository.findAlbumByNameAndUserEmail(album,user.get().getEmail());
//            if (findalbum==null) {
//                return ResponseEntity.notFound().build();
//            }
//            org.example.clickstack.Entity.Photos photo = photosRepository.findPhotosByNameAndAlbumName(name,album);
//
//                    photo.setName(photosModel.getName());
//                    photo.setLink(photosModel.getLink());
//                    photo.setAlbum(albumRepository.findAlbumByName(album));
//            photosRepository.save(photo);
//            return ResponseEntity.ok("Photo Successfully Updated");
//        }
//        catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Photo Not Updated");
//        }
//    }
    @DeleteMapping("/deletePhoto/{album}/{name}")
    public ResponseEntity<String> deletePhoto(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String album,@PathVariable String name){
        try {
            String photo = photoService.deletePhoto(token,album,name);
            if(photo==null){
                ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(photo);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Photo Not Deleted");
        }
    }

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
//        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
//    }
//    @GetMapping("/getImage/{name}")
//    public ResponseEntity<URL> getImage(@PathVariable String name) {
//        return new ResponseEntity<>(service.getURL(name),HttpStatus.OK);
//    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
//    @DeleteMapping("/delete/{fileName}")
//    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
//        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
//    }
}
