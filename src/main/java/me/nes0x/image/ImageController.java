package me.nes0x.image;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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


    @ResponseBody
    @GetMapping(path = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
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
    String addImage(@RequestParam("image") MultipartFile multipartFile, Principal principal, Model model) throws IOException {
        if (multipartFile.isEmpty()) {
            model.addAttribute("message", "Musisz wybrać zdjęcie!");
            return "dashboard";
        }

        ImageReadModel image = imageService.save(multipartFile, principal.getName());

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
        boolean result = imageService.deleteImageById(id, principal.getName());
        if (result) {
            model.addAttribute("message", "Pomyślnie usunięto zdjęcie!");
            model.addAttribute("images", getImages(principal));
        } else {
            model.addAttribute("message", "Nie jesteś włascicielem tego zdjęcia!");
        }

        return "dashboard";
    }



}
