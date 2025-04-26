package bro.controller;

import bro.entity.CustomUserDetails;
import bro.entity.ItemEntity;
import bro.entity.UserEntity;
import bro.service.FileUpload;
import bro.service.ItemService;
import bro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final UserService userService;
    private final ItemService itemService;
    private final FileUpload fileUpload;

    public SellerController(UserService userService,
                            ItemService itemService,
                            FileUpload fileUpload){
        this.userService = userService;
        this.itemService = itemService;
        this.fileUpload = fileUpload;
    }

    @Value("${categories}")
    private List<String> categories;

    @GetMapping("/showAddItemForm")
    public String showAddItemForm(Model theModel){
        ItemEntity itemEntity = new ItemEntity();
        theModel.addAttribute("itemEntity", itemEntity);
        theModel.addAttribute("categories", categories);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();

        return "forms/add-item-form";
    }

    @PostMapping("/saveItem")
    public String saveItem(@Valid @ModelAttribute("itemEntity") ItemEntity itemEntity,
                           BindingResult bindingResult,
                           @RequestParam("image") MultipartFile image, Model theModel) throws IOException {

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("categories", categories);
            return "forms/add-item-form";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();
        UserEntity userEntity = userService.findByUsername(customUser.getUsername());

        itemEntity.setSeller(userEntity);
        String imageURL = fileUpload.uploadFile(image);
        itemEntity.setImageURL(imageURL);

        ItemEntity db_item = itemService.save(itemEntity);

        return "redirect:/";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("itemId") int theId){
        String imageURL = itemService.findById(theId).getImageURL();
        try {
            fileUpload.deleteFile(imageURL);
        }
        catch (Exception e){
            System.out.println("Could not delete the image photo.");
        }
        itemService.delete(theId);
        return "redirect:/";
    }

    @GetMapping("/updateItem")
    public String updateItem(@RequestParam("itemId") int theId, Model theModel){
        ItemEntity itemEntity = itemService.findById(theId);
        theModel.addAttribute("itemEntity", itemEntity);
        theModel.addAttribute("categories",categories);
        return "forms/update-item-form";
    }
}
