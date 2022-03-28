package me.nes0x.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface SqlImageRepository extends ImageRepository, JpaRepository<Image, UUID> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from images where id = ?1", nativeQuery = true)
    @Override
    void deleteById(UUID id);
}
