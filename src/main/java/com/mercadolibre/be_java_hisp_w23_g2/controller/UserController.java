package com.mercadolibre.be_java_hisp_w23_g2.controller;

import com.mercadolibre.be_java_hisp_w23_g2.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowersCountSeller(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getFollowersCountSeller(userId));
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getFollowersUser(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getFollowersUser(userId));
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowUser(@PathVariable int userId, @PathVariable int userIdToUnfollow){
        return ResponseEntity.ok(userService.unfollowUser(userId, userIdToUnfollow));
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<?> getFollowedUser(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getFollowedUser(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable int userId,@PathVariable int userIdToFollow){
        return new ResponseEntity<>(userService.followUser(userId,userIdToFollow),HttpStatus.OK);
    }
}
