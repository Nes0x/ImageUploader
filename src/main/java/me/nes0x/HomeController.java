package me.nes0x;

import me.nes0x.image.ImageReadModel;
import me.nes0x.image.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Random;


@Controller
class HomeController {
    private final ImageService imageService;

    HomeController(final ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping("/")
    String home(Model model) {
        List<ImageReadModel> images = imageService.getAllImages();
        if (images.isEmpty()) {
            model.addAttribute("image", null);
            return "index";
        }

        Random rand = new Random();
        ImageReadModel randomImage = images.get(rand.nextInt(images.size()));


        model.addAttribute("image", randomImage);
        return "index";
    }

}
