package me.nes0x.image;

import me.nes0x.user.User;
import me.nes0x.user.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository repository;
    private final UserRepository userRepository;

    public ImageService(final ImageRepository repository, final UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public ImageReadModel save(MultipartFile file, String username, boolean secretImage) throws IOException {
        switch (file.getContentType()) {
            case MediaType.IMAGE_JPEG_VALUE:
            case MediaType.IMAGE_PNG_VALUE:
                Image image = new Image();
                User user = userRepository.findByName(username).orElseThrow(IllegalArgumentException::new);

                UUID uuid = UUID.randomUUID();
                image.setId(uuid);
                image.setUser(user);
                image.setSecretImage(secretImage);
                image.setExtension(file.getContentType());
                image.setTitle(file.getOriginalFilename());
                String path = String.valueOf(Paths.get("user_images", username, uuid + "." +
                        file.getContentType().replace("image/", "")));

                File userDir = new File("./user_images", username);


                if (!userDir.exists()) {
                    userDir.mkdirs();
                    System.out.println("User dir " + username + " created!");
                }

                image.setPath(path);
                File fileToDownload = new File(path);


                try (OutputStream os = new FileOutputStream(fileToDownload)) {
                    os.write(file.getBytes());
                }

                repository.save(image);
                return new ImageReadModel(image);
            default:
                return null;
        }


    }

    public ImageReadModel getImageById(UUID id) {
        var image = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new ImageReadModel(image);
    }


    public List<ImageReadModel> getAllImagesByUserName(String name) {
        return repository.findByUser_Name(name).stream()
                .map(ImageReadModel::new)
                .collect(Collectors.toList());

    }

    public List<ImageReadModel> getAllImagesBySecret() {
        return repository.findAllBySecretImageIsTrue()
                .stream()
                .map(ImageReadModel::new)
                .collect(Collectors.toList());

    }

    public boolean deleteImage(UUID id, String name) {
        if (repository.existsByIdAndUser_Name(id, name)) {
            Image image = repository.findById(id).get();
            File file = new File(image.getPath());
            if (file.delete()) {
                repository.deleteById(id);
            }
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    public boolean changeImage(UUID id, String name) {
        if (repository.existsByIdAndUser_Name(id, name)) {
            Image image = repository.findById(id).get();
            image.setSecretImage(!image.isSecretImage());
            return true;
        } else {
            return false;
        }
    }


}
