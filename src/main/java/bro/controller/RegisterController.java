package bro.controller;

import bro.entity.NewUser;
import bro.entity.UserEntity;
import bro.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model theModel){
        theModel.addAttribute("newUser", new NewUser());
        return "register/registration-form";
    }

    @PostMapping("processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("newUser") NewUser newUser,
                                          BindingResult theBindingResult,
                                          HttpSession session, Model theModel){
        String username = newUser.getUsername();
        if (theBindingResult.hasErrors()){
            return "register/registration-form";
        }

        UserEntity existingUser = userService.findByUsername(username);
        if (existingUser !=null){
            System.out.println("user already exists with that username");
            //theModel.addAttribute("newUser", new NewUser());
            theBindingResult.rejectValue("username", "error.username", "Username is not available");
            return "register/registration-form";
        }

        userService.saveTheNewUser(newUser);
        System.out.println(newUser.toString());

        theModel.addAttribute("newUser",newUser);
        return "register/registration-confirmation";
    }
}