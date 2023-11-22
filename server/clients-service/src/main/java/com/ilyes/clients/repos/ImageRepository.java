package com.ilyes.clients.repos;

import com.ilyes.clients.entities.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findByName(String name);
}
