package me.nes0x.security;

import me.nes0x.user.UserService;
import me.nes0x.user.UserValidator;
import me.nes0x.user.UserWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class SecurityController {
    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    SecurityController(final UserService userService, final SecurityService securityService, final UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }


    @GetMapping("/register")
    String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("user", new UserWriteModel());

        return "register";
    }

    @PostMapping("/register")
    String registration(@ModelAttribute("user") UserWriteModel current, BindingResult bindingResult, Model model) {
        userValidator.validate(current, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.save(current);
        model.addAttribute("message", "Pomyślnie się zarejestrowałeś!");
        return "register";
    }

    @GetMapping("/login")
    String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Login lub hasło jest nieprawidłowe.");

        if (logout != null)
            model.addAttribute("message", "Zostałeś wylogowany.");

        return "login";
    }
}
