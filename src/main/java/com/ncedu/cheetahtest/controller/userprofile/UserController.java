package com.ncedu.cheetahtest.controller.userprofile;

import com.ncedu.cheetahtest.entity.security.RegisterResponse;
import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PutMapping("/editUser")
  public ResponseEntity<RegisterResponse> editUser(@RequestBody User user) {
    userService.editUser(user);

    return ResponseEntity.ok(new RegisterResponse("success"));
  }

  @PutMapping("/activate")
  public ResponseEntity<RegisterResponse> doActive(@RequestBody User user) {
    userService.changeUserStatus(user);

    return ResponseEntity.ok(new RegisterResponse("success"));
  }

  @PutMapping("/deactivate")
  public ResponseEntity<RegisterResponse> doInactive(@RequestBody User user) {
    userService.changeUserStatus(user);

    return ResponseEntity.ok(new RegisterResponse("success"));
  }

  @GetMapping("/findById")
  public ResponseEntity<RegisterResponse> searchUser(@RequestBody Long id) {
    userService.findUserById(id);

    return ResponseEntity.ok(new RegisterResponse("success"));
  }
}
