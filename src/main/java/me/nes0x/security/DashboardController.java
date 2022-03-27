package me.nes0x.security;

import me.nes0x.image.ImageReadModel;
import me.nes0x.image.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
class DashboardController {
    private final ImageService imageService;

    DashboardController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @ModelAttribute("images")
    List<ImageReadModel> getImages(Principal principal) {
        return imageService.getAllImagesByUserName(principal.getName());
    }

    @GetMapping
    String showAddImage() {
        return "dashboard";
    }

}
