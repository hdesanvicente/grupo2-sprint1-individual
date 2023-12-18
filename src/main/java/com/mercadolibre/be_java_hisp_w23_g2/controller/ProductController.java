package com.mercadolibre.be_java_hisp_w23_g2.controller;


import com.mercadolibre.be_java_hisp_w23_g2.service.IUserService;
import org.springframework.web.bind.annotation.*;
import com.mercadolibre.be_java_hisp_w23_g2.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w23_g2.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final IUserService userService;

    public ProductController(IProductService productService, IUserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> addPost(@RequestBody PostDTO postDto) {
        return new ResponseEntity<>(productService.addPost(postDto), HttpStatus.OK);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getPostsByFollowedUsers(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getPostsByFollowedUsers(userId), HttpStatus.OK);
    }
}
