package com.ncedu.cheetahtest.controller.userprofile;

import com.ncedu.cheetahtest.entity.user.*;
import com.ncedu.cheetahtest.service.cloud.AmazonClientService;
import com.ncedu.cheetahtest.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@CrossOrigin(origins = "${frontend.ulr}")
public class UserController {
    private final UserService userService;
    private final AmazonClientService amazonClientService;

    @Autowired
    public UserController(UserService userService, AmazonClientService amazonClientService) {
        this.userService = userService;
        this.amazonClientService = amazonClientService;
    }

    @PutMapping
    public User editUser(@RequestBody UserDto user) {
        return userService.editUser(user);
    }

    @PostMapping("/activate")
    public User doActive(@RequestBody long id) {
        return userService.changeUserStatus(id, UserStatus.ACTIVE.toString());
    }

    @PostMapping("/deactivate")
    public User doInactive(@RequestBody String email) {
        return userService.changeUserStatus(userService.findUserByEmail(email).getId(), UserStatus.INACTIVE.toString());
    }

    @GetMapping("/{id}")
    public User searchUser(@PathParam("id") String id) {
        return userService.findUserById(Long.parseLong(id));
    }

    @GetMapping("/profiles")
    public UserPaginatedDto getProfileByEmail(@RequestParam("size") int size,
                                              @RequestParam("page") int page) {
        return userService.getAllActiveUser(size, page);
    }

    @GetMapping("/search-profiles")
    public UserPaginatedDto searchUser(@RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam("role") String role,
                                       @RequestParam("size") int size,
                                       @RequestParam("page") int page) {
        return userService.getSearchUserByNameEmailRole(name, email, role, size, page);
    }

    @PostMapping("/edit-user")
    public User editUser(@RequestBody UserToUpdate name) {
        return userService.editUser(new UserDto(userService.findUserByEmail(name.previousEmail).getId(),
                name.user.getEmail(), name.user.getName(), name.user.getRole(), UserStatus.ACTIVE));
    }

    @GetMapping("/search/findByName")
    public List<UserDto> findUsersByName(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String title
    ) {
        return userService.findUsersByName(page, size, title);
    }

    @PostMapping("/edit-user/uploadPhoto")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file,
                             @RequestParam("id") int id) {
        return this.amazonClientService.uploadUserPhoto(file, id);
    }

    @DeleteMapping("/edit-user/deletePhoto")
    public String deleteTestScenario(@RequestParam("url") String url) {
        return this.amazonClientService.deleteUserPhotoFromS3Bucket(url);
    }
}

class UserToUpdate {
    public User user;
    public String previousEmail;
}
