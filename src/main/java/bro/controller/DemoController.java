package bro.controller;

import bro.entity.*;
import bro.service.FileUpload;
import bro.service.ItemService;
import bro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class DemoController {

    private final UserService userService;
    private final FileUpload fileUpload;
    private final ItemService itemService;

    @Autowired
    public DemoController(UserService userService,
                          FileUpload fileUpload,
                          ItemService itemService) {
        this.userService = userService;
        this.fileUpload = fileUpload;
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String showHome(Model theModel){
        List<UserEntity> users = userService.findAll();
        theModel.addAttribute("users",users);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();
        theModel.addAttribute("items", itemService.findBySeller_Username(customUser.getUsername()));

        theModel.addAttribute("allItems", itemService.findItemsByActiveTrue());
        return "home";
    }


    // showing profile page
    @GetMapping("/profile")
    public String showProfilePage(Model theModel){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();
        UserEntity userEntity = userService.findByUsername(customUser.getUsername());

        System.out.println(userEntity.getAuthorities());
        for (GrantedAuthority authority: userEntity.getAuthorities()){
            System.out.println(authority.getAuthority());
            System.out.println(authority.toString());
            System.out.println("\n\n\n");
        }
        // creating entity for username change request
        theModel.addAttribute("usernameChangeRequest", new UsernameChangeRequest());
        theModel.addAttribute("userEntity", userEntity);
        return "profile-page";
    }

    // updating the user information
    @PostMapping("/updateUserInfo")
    public String updateUserInformation(@ModelAttribute("userEntity") UserEntity userEntity){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
        UserEntity oldUserEntity = userService.findByUsername(currentUser.getUsername());

        oldUserEntity.setFirstname(userEntity.getFirstname());
        oldUserEntity.setLastname(userEntity.getLastname());
        oldUserEntity.setEmail(userEntity.getEmail());

        UserEntity saved = userService.save(oldUserEntity);

        CustomUserDetails updatedDetails = new CustomUserDetails(saved);
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedDetails, currentAuth.getCredentials(), updatedDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/";
    }

    // deleting the user account
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("password") String password){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();
        UserEntity userEntity = userService.findByUsername(customUser.getUsername());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            int userId = userEntity.getId();
            userService.delete(userEntity);

            SecurityContextHolder.clearContext();
            return "redirect:/showMyLoginPage";
        }

        return "redirect:/profile?error=wrong_password";
    }

    // changing the username
    @PostMapping("/changeUsername")
    public String changeUsername(@Valid @ModelAttribute("usernameChangeRequest") UsernameChangeRequest usernameChangeRequest,
                                 Model theModel,
                                 BindingResult theBindingResult){

        String username = usernameChangeRequest.getUsername();
        String password = usernameChangeRequest.getPassword();

        UserEntity existingUserEntity = userService.findByUsername(username); // find if user exists with this username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal(); // get current logged in user

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (existingUserEntity != null){
            if (existingUserEntity.getUsername().equals(currentUser.getUsername())){
                theBindingResult.rejectValue("username", "error.username", "You have entered your current username");
            }
            else {
                theBindingResult.rejectValue("username", "error.username", "Username is not available");
            }
        }
        else if (username.isEmpty() || username.isBlank()){
            theBindingResult.rejectValue("username", "error.username", "Username cannot be empty or null");
        }
        else {
            if (passwordEncoder.matches(password, currentUser.getPassword().replace("{bcrypt}", ""))) {
                UserEntity currentUserEntity = userService.findByUsername(currentUser.getUsername());
                currentUserEntity.setUsername(username);
                UserEntity savedUser = userService.save(currentUserEntity);

                CustomUserDetails updatedDetails = new CustomUserDetails(savedUser);
                Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        updatedDetails, currentAuth.getCredentials(), updatedDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                return "redirect:/";
            } else {
                theBindingResult.rejectValue("password", "error.password", "Password incorrect");
            }
        }
        theModel.addAttribute("userEntity", currentUser);
        return "profile-page";
    }

    // changing the profile photo
    @PostMapping("/changeProfilePhoto")
    public String changeProfilePhoto(@RequestParam("profilePhoto") MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()){
            return "redirect:/changeProfilePhoto";
        }
        else {
            String imageURL = fileUpload.uploadFile(multipartFile);

            // get current logged in user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();
            UserEntity userEntity = userService.findByUsername(customUser.getUsername());

            // change the profile photo url of the user
            String profilePhoto = userEntity.getProfile_photo();
            if (profilePhoto.contains("res.cloudinary")){
                fileUpload.deleteFile(userEntity.getProfile_photo());
            }

            userEntity.setProfile_photo(imageURL);

            // save the user entity
            UserEntity savedUser = userService.save(userEntity);

            // show the new user profile photo
            CustomUserDetails updatedDetails = new CustomUserDetails(savedUser);
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    updatedDetails, currentAuth.getCredentials(), updatedDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            return "redirect:/";
        }
    }

    @PostMapping("/searchItem")
    public String searchItem(@RequestParam("itemName") String itemName,
                             @RequestParam("parameter") String parameter,
                             Model theModel){
        System.out.println(itemName);
        System.out.println(parameter);
        if (itemName.isEmpty()){
            return "home";
        }
        List<ItemEntity> items = null;

        if (parameter.equals("Name")){
            items = itemService.findByKeyword(itemName);
        }
        else {
            items = itemService.findByType(itemName);
        }
        theModel.addAttribute("allItems",items);
        return "home";
    }

}
