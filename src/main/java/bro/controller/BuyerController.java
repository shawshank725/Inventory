package bro.controller;

import bro.entity.CustomUserDetails;
import bro.entity.ItemEntity;
import bro.entity.OrderEntity;
import bro.entity.UserEntity;
import bro.service.ItemService;
import bro.service.OrderService;
import bro.service.UserService;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    @Value("${payment_modes}")
    private List<String> payment_modes;

    private final UserService userService;
    private final ItemService itemService;
    private final OrderService orderService;

    public BuyerController(UserService userService, ItemService itemService, OrderService orderService){
        this.userService = userService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping("/viewDetails")
    public String viewDetails(@RequestParam("itemId") int theId, Model theModel){
        ItemEntity itemEntity = itemService.findById(theId);
        theModel.addAttribute("item", itemEntity);
        return "item-details";
    }

    @GetMapping("/allOrders")
    public String allOrders(Model theModel){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();
        UserEntity userEntity = userService.findByUsername(customUser.getUsername());

        List<OrderEntity> allOrdersList = orderService.findByBuyer(userEntity);
        theModel.addAttribute("allOrders", allOrdersList);
        return "buyer/buyer-orders";
    }

    @GetMapping("/buyItem")
    public String orderItem(@RequestParam("itemId") int itemId,  Model theModel){
        OrderEntity orderEntity = new OrderEntity();
        theModel.addAttribute("orderEntity", orderEntity);
        theModel.addAttribute("payment_modes", payment_modes);
        theModel.addAttribute("itemId", itemId);
        return "forms/buy-item";
    }

    @PostMapping("/finalizeBuyingItem")
    public String finalizeBuyingItem(@ModelAttribute("orderEntity")OrderEntity orderEntity,
                                     @RequestParam("itemId") int itemId,
                                     Model theModel,
                                     Principal principal){
        String username = principal.getName();
        UserEntity user = userService.findByUsername(username);

        ItemEntity itemEntity = itemService.findById(itemId);
        itemEntity.setQuantity(itemEntity.getQuantity()-1);

        orderEntity.setBuyer(user);
        orderEntity.setItem(itemService.findById(itemId));
        orderEntity.setStatus("Out for delivery.");

        orderService.save(orderEntity);

        String address = orderEntity.getAddress();
        theModel.addAttribute("address", address);
        return "buyer/order-confirmation";
    }
}
