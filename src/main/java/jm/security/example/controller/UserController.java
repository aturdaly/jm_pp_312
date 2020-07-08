package jm.security.example.controller;

import jm.security.example.model.Role;
import jm.security.example.model.User;
import jm.security.example.service.RoleService;
import jm.security.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsers(ModelMap model, User user, String[] posRoles) {
        model.addAttribute("userInfo", SecurityContextHolder.getContext()
                                                        .getAuthentication().getPrincipal());
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("roleList", roleService.getAllRole());
        return "readUser";
    }

    @GetMapping("/user")
    public String infoUserPage(ModelMap model) {
        model.addAttribute("userInfo", SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        return "infoUser";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(ModelMap model, User user, String[] posRoles) {
        userService.updateUser(findUserRole(user, posRoles));
        return "redirect:/admin";
    }

    @PostMapping("/admin/addUser")
    public String addUser(ModelMap model, User user, String[] posRoles) {
        userService.addUser(findUserRole(user, posRoles));
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/deleteUser")
    public String deleteUser(ModelMap model, @PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    private User findUserRole(User user, String[] posRoles) {
        Set<Role> userRoles = new HashSet<>();
        for (String role: posRoles) {
            userRoles.add(roleService.getRoleByName(role));
        }
        user.setRoles(userRoles);
        return user;
    }
}
