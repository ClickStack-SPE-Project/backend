package org.example.clickstack.Repository;

import org.example.clickstack.Entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotosRepository extends JpaRepository<Photos,Integer> {
    List<Photos> findPhotosByAlbumName(String album_name);
    Photos findPhotosByNameAndAlbumName(String photo_name,String album_name);
}
