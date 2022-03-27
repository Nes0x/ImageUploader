package me.nes0x.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User source);
    Optional<User> findByName(String name);
    List<User> findAll();
    List<User> findByNameContainsIgnoreCase(String name);
    Optional<User> findById(Integer id);

}
