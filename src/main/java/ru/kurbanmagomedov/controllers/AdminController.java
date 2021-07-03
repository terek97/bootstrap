package ru.kurbanmagomedov.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.kurbanmagomedov.models.Role;
import ru.kurbanmagomedov.models.User;
import ru.kurbanmagomedov.service.RoleService;
import ru.kurbanmagomedov.service.UserService;


import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("users")
    public String getUsers(Principal principal, Model model) {

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("principal", userService.getUserByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());

        return "users";
    }

    @PostMapping("/user/new")
    public RedirectView createUser(@ModelAttribute("user") User user,
                                   @RequestParam("newUserRoles") String[] roles) {
        Set<Role> roleSet = Arrays.stream(roles)
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet());

        user.setRoles(roleSet);

        userService.saveUser(user);
        return new RedirectView("/admin/users");
    }

    @PatchMapping("/user/change")
    public RedirectView changeUser(@ModelAttribute("user") User user,
                                   @RequestParam("allRoles[]") String[] roles) {
        Set<Role> roleSet = Arrays.stream(roles)
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet());

        userService.setUser(user, roleSet);

        return new RedirectView("/admin/users");
    }

    @DeleteMapping("/user/{id}")
    public RedirectView removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return new RedirectView("/admin/users");
    }
}
