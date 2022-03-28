package me.nes0x;

import me.nes0x.image.ImageReadModel;
import me.nes0x.image.ImageService;
import me.nes0x.user.UserReadModel;
import me.nes0x.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Random;


@Controller
class HomeController {
    private final ImageService imageService;
    private final UserService userService;

    HomeController(final ImageService imageService, final UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }


    @GetMapping("/")
    String home(Model model) {
        List<ImageReadModel> images = imageService.getAllImagesBySecret();
        if (images.isEmpty()) {
            model.addAttribute("image", null);
            return "index";
        }

        Random rand = new Random();
        ImageReadModel randomImage = images.get(rand.nextInt(images.size()));


        model.addAttribute("image", randomImage);
        return "index";
    }

    @GetMapping("/og")
    String ogEditor(Principal principal, Model model) {
        UserReadModel user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "og";
    }

    @PostMapping("/og")
    String saveOg(Principal principal, @RequestParam(value = "url") String url,
                         @RequestParam(value = "description") String description,
                         @RequestParam(value = "color") String color,
                  Model model) {
        if (url.isBlank() || description.isBlank() || color.isBlank()) {
            model.addAttribute("message", "Każde pole musi mieć wartość!");
        } else {
            userService.updateOg(principal.getName(), color, description, url);
        }

        model.addAttribute("user", userService.getUserByName(principal.getName()));
        return "og";
    }

}
