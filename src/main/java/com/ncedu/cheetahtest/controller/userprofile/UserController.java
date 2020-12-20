package com.ncedu.cheetahtest.controller.userprofile;

import com.ncedu.cheetahtest.entity.testcase.IdsDto;
import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.UserDto;
import com.ncedu.cheetahtest.entity.user.UserPaginatedDto;
import com.ncedu.cheetahtest.entity.user.UserStatus;
import com.ncedu.cheetahtest.service.cloud.AmazonClientService;
import com.ncedu.cheetahtest.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public User searchUser(@PathVariable("id") String id) {
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


    @PostMapping("/uploadUserPhoto")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file,
                             @RequestParam("id") int id) {
        return this.amazonClientService.uploadUserPhoto(file, id);
    }

    @DeleteMapping("/deleteUserPhoto")
    public String deleteTestScenario(@RequestParam("url") String url) {
        return this.amazonClientService.deleteUserPhotoFromS3Bucket(url);
    }

    @GetMapping
    public List<UserDto> findByEmail(@RequestParam("email") String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/watchers/{projectId}")
    public List<UserDto> getByProjectId(@PathVariable int projectId) {
        return userService.getWatchersByProjectId(projectId);
    }


    @PutMapping("/watchers/{projectId}")
    public IdsDto getByProjectId(@PathVariable int projectId, @RequestBody IdsDto idsDto) {
        userService.saveWatchers(projectId, idsDto.getIds());
        return idsDto;
    }
}

class UserToUpdate {
    public User user;
    public String previousEmail;
}
