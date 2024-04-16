package org.example.clickstack.Repository;

import org.example.clickstack.Controller.Albums;
import org.example.clickstack.Entity.Album;
import org.example.clickstack.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album,Integer> {

    List<Album> findByUser(User user);

    Album findAlbumByName(String name);

    Album findAlbumByNameAndUserEmail(String name, String email);
}
