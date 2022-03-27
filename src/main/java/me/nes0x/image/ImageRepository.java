package me.nes0x.image;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository {
    Image save(Image source);
    Optional<Image> findById(UUID id);
    List<Image> findAll();
    boolean existsByIdAndUser_Name(UUID id, String name);
    List<Image> findByUser_Name(String name);
    void deleteById(UUID id);
}
