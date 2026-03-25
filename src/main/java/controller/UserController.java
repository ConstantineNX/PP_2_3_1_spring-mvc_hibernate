package controller;

import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.UserDaoService;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/")
    public String home() {
        return "forward:/users";
    }

    @GetMapping("/users")
    public String findAllUsers(Model model) {
        List<User> users = userDaoService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userDaoService.saveUser(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userDaoService.delete(id);
        return "redirect:/users";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam("id") long id) {
        return "redirect:/update?id=" + id;
    }

    @GetMapping("/update")
    public String updateUserForm(@RequestParam("id") long id,@ModelAttribute("error") String err, Model model) {
        User user = userDaoService.findById(id);
        model.addAttribute("user", user);
        if (err != null) {
            model.addAttribute("error", err);
        }
        return "update-form";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") long id, @Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-form";
        }
        try {
            userDaoService.update(id, user);
            return "redirect:/users";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "update-form";
        }
    }

    @GetMapping("/search")
    public String searchUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("users", userDaoService.findAll());
        model.addAttribute("user", new User());
        try {
            User user = userDaoService.findById(id);
            model.addAttribute("foundUser", user);
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", "the user was not found with the id " + id);
        }
        return "users";
    }
}
