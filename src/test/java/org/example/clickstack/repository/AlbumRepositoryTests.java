package org.example.clickstack.repository;

import org.assertj.core.api.Assertions;

import org.example.clickstack.Entity.User;
import org.example.clickstack.Repository.AlbumRepository;
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
public class AlbumRepositoryTests {
    @Autowired
    private AlbumRepository albumRepository;

}
