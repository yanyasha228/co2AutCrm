package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.User;
import com.co2AutomaticCrm.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("settings/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    private String usersList(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable) {

        Page<User> usersPage = userService.findAllWithPagination(pageable);

        model.addAttribute("users", usersPage);

        return "users";

    }


}
