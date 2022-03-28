package me.nes0x.user;


import me.nes0x.image.Image;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(final UserRepository repository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserReadModel save(UserWriteModel user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUrl("https://imageuploader.tk");
        user.setDescription("Hosting zdjęć");
        user.setColor("#808080");
        User result = repository.save(user.toUser());
        File file = new File(String.valueOf(Paths.get("user_images", user.getName())));
        file.mkdir();
        return new UserReadModel(result);
    }

    public UserReadModel getUserByName(String name) {
        var user = repository.findByName(name).orElseThrow(IllegalArgumentException::new);
        return new UserReadModel(user);
    }

    public List<UserReadModel> getAllUsers() {
        return repository.findAll().stream()
                .map(UserReadModel::new)
                .collect(Collectors.toList());
    }

    public List<UserReadModel> getAllUsersOfName(String name) {
        return repository.findByNameContainsIgnoreCase(name)
                .stream()
                .map(UserReadModel::new)
                .collect(Collectors.toList());
    }

    public UserReadModel getUserById(int id) {
        var user = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new UserReadModel(user);
    }

    @Transactional
    public void updateOg(String name, String color, String description, String url) {
        User user = repository.findByName(name).orElseThrow(IllegalArgumentException::new);
        user.setColor(color);
        user.setDescription(description);
        user.setUrl(url);
    }
}
