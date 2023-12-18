package com.mercadolibre.be_java_hisp_w23_g2.controller;

import com.mercadolibre.be_java_hisp_w23_g2.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IUserService userService;

    public ProductController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getPostsByFollowedUsers(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getPostsByFollowedUsers(userId), HttpStatus.OK);
    }
}
