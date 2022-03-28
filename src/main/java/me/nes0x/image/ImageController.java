package me.nes0x.image;

import me.nes0x.user.User;
import me.nes0x.user.UserReadModel;
import me.nes0x.user.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequestMapping("/image")
@Controller
class ImageController {
    private final ImageService imageService;

    ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @ModelAttribute("images")
    List<ImageReadModel> getImages(Principal principal) {

        if (principal == null) {
            return null;
        }

        return imageService.getAllImagesByUserName(principal.getName());
    }



    @GetMapping(path = "/{id}")
    String getImage(@PathVariable UUID id, Model model){
        ImageReadModel image = imageService.getImageById(id);
        User user = image.getUser();
        model.addAttribute("image", image);
        model.addAttribute("user", user);
        return "image";
    }

    @ResponseBody
    @GetMapping(path = "/api/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    ResponseEntity<Resource> getImage(@PathVariable UUID id) throws IOException {
        ImageReadModel image = imageService.getImageById(id);
        ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get("./", image.getPath()
        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

    @PostMapping("/add")
    String addImage(@RequestParam("image") MultipartFile multipartFile, @RequestParam(value = "secretImage", required = false) boolean secretImage,
                    Principal principal, Model model) throws IOException {
        if (multipartFile.isEmpty()) {
            model.addAttribute("message", "Musisz wybrać zdjęcie!");
            return "dashboard";
        }

        ImageReadModel image = imageService.save(multipartFile, principal.getName(), secretImage);

        if (image == null) {
            model.addAttribute("message", "Możesz dodawać tylko zdjęcia!");
            return "dashboard";
        }

        model.addAttribute("message", "Pomyślnie dodałeś zdjęcie!");
        model.addAttribute("images", getImages(principal));
        return "dashboard";
    }


    @PostMapping("/delete/{id}")
    String deleteImage(@PathVariable UUID id, Model model, Principal principal) {
        boolean result = imageService.deleteImage(id, principal.getName());
        if (result) {
            model.addAttribute("message", "Pomyślnie usunięto zdjęcie!");
            model.addAttribute("images", getImages(principal));
        } else {
            model.addAttribute("message", "Nie jesteś włascicielem tego zdjęcia!");
        }

        return "dashboard";
    }

    @PostMapping("/change/{id}")
    String changeImage(@PathVariable UUID id, Principal principal, Model model) {
        imageService.changeImage(id, principal.getName());
        model.addAttribute("images", getImages(principal));
        return "dashboard";
    }



}
