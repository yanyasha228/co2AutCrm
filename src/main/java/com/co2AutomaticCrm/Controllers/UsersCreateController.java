package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("settings/users/create")
public class UsersCreateController {

    @Autowired
    private UserService userService;

}
