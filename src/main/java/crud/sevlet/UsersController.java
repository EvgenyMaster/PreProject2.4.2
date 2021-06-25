package crud.sevlet;


import crud.model.Role;
import crud.model.User;
import crud.service.ServiceDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UsersController {

    @Autowired
    private ServiceDB serviceDb;

    @GetMapping("/")
    public String showSignUpForm() {
        return "login";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("users", serviceDb.listUsers());
        return "adminPage";
    }

    @GetMapping("/user")
    public ModelAndView showUserPage() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPage");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/addNewUser")

    public String adduser(Model model) {
        model.addAttribute("user", new User());
        return "/adduser";
    }

    @PostMapping("/adduser")
    public String saveAddingUser(@RequestParam("name") String name,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam(required = false, name = "ADMIN") String ADMIN,
                                 @RequestParam(required = false, name = "USER") String USER) {

        Set<Role> roles = new HashSet<>();
        if (ADMIN != null) {
            roles.add(new Role(2L, ADMIN));
        }
        if (USER != null) {
            roles.add(new Role(1L, USER));
        }
        if (ADMIN == null && USER == null) {
            roles.add(new Role(1L, USER));
        }

        User user = new User(name, email, password, roles);
        serviceDb.insertUser(user);

        return "adminPage";
    }

    @GetMapping("/edit/{id}")

    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", serviceDb.getUserById(id));
        return "editUserPage";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") int id,
                             @RequestParam(required = false, name = "ADMIN") String ADMIN,
                             @RequestParam(required = false, name = "USER") String USER) {

        Set<Role> roles = new HashSet<>();
        if (ADMIN != null) {
            roles.add(new Role(2L, ADMIN));
        }
        if (USER != null) {
            roles.add(new Role(1L, USER));
        }
        if (ADMIN == null && USER == null) {
            roles.add(new Role(1L, USER));
        }
        user.setRoles(roles);
        serviceDb.updateUser(user);
        return "adminPage";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        serviceDb.deleteUser(id);
        model.addAttribute("users", serviceDb.listUsers());
        return "adminPage";
    }
}