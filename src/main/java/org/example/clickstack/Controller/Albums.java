package org.example.clickstack.Controller;


import org.example.clickstack.Model.AlbumModel;
import org.example.clickstack.Service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/album")
public class Albums {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/getAllAlbum")
    public ResponseEntity<List<AlbumModel>> getAllAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        try {

            return ResponseEntity.ok(albumService.getAllAlbums(token));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/getAlbum/{name}")
    public ResponseEntity<AlbumModel> getAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String name){
        try {

            return ResponseEntity.ok(albumService.getAlbum(token,name));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PostMapping("/createAlbum")
    public ResponseEntity<String> createAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestBody AlbumModel albumModel){
        try {

            return ResponseEntity.ok(albumService.createAlbum(token,albumModel));
        }
        catch (Exception ignored){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Album Not Created");
        }
    }
    @PutMapping("/updateAlbum/{name}")
    public ResponseEntity<String> updateAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String name,@RequestBody AlbumModel albumModel){
        try {

            return ResponseEntity.ok(albumService.updateAlbum(token, name, albumModel));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Album Not Updated");
        }
    }
    @DeleteMapping("/deleteAlbum/{name}")
    public ResponseEntity<String> deleteAlbum(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String name){
        try {

            return ResponseEntity.ok(albumService.deleteAlbum(token, name));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Album Not Deleted");
        }
    }
}
