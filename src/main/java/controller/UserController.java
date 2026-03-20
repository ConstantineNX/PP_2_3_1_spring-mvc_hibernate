package controller;

import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.UserDaoService;
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
    public String updateUser(@RequestParam("id") long id, @ModelAttribute User user, Model model) {
        try {
            User newUser = userDaoService.findById(id);
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setAge(user.getAge());
            newUser.setCity(user.getCity());
            newUser.setEmail(user.getEmail());
            newUser.setPhone(user.getPhone());
            userDaoService.update(newUser);
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "update-form";
        }

    }

    @GetMapping("/search")
    public String searchUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("users", userDaoService.findAll());
        model.addAttribute("user", new User());
        User user = userDaoService.findById(id);
        if (user != null) {
            model.addAttribute("foundUser", user);
        } else {
            model.addAttribute("error", "the user was not found with the id " + id);
        }
        return "users";
    }
}
