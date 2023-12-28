package com.mercadolibre.be_java_hisp_w23_g2.controller;


import com.mercadolibre.be_java_hisp_w23_g2.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.mercadolibre.be_java_hisp_w23_g2.dto.requests.PostDTO;
import com.mercadolibre.be_java_hisp_w23_g2.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Controller class for handling product-related operations.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final IUserService userService;

    public ProductController(IProductService productService, IUserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    /**
     * Endpoint for adding a new post.
     *
     * @param postDto The PostDTO containing information about the post to be added.
     * @return ResponseEntity with the result of the addPost operation.
     */
    @PostMapping("/post")
    public ResponseEntity<?> addPost(@RequestBody @Valid PostDTO postDto) {
        System.out.println("No debería llegar acá con validations erroneas");
        return new ResponseEntity<>(productService.addPost(postDto), HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving posts by followed users.
     *
     * @param userId The ID of the user whose followed users' posts are to be retrieved.
     * @param order  Optional parameter for specifying the order of the posts.
     * @return ResponseEntity with the result of the getPostsByFollowedUsers operation.
     */
    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getPostsByFollowedUsers(@PathVariable Integer userId, @RequestParam(required = false) String order) {
        return new ResponseEntity<>(userService.getPostsByFollowedUsers(userId, order), HttpStatus.OK);
    }
}
