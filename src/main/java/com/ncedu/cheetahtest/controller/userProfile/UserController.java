package com.ncedu.cheetahtest.controller.userProfile;

import com.ncedu.cheetahtest.entity.security.RegisterResponse;
import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/editUser")
  public ResponseEntity<RegisterResponse> editUser(@RequestBody User user) {
    userService.editUser(user);

    return ResponseEntity.ok(new RegisterResponse("success"));
  }

  @PostMapping("/doActive")
  public ResponseEntity<RegisterResponse> doActive(@RequestBody User user) {
    userService.doActive(user);

    return ResponseEntity.ok(new RegisterResponse("success"));
  }

  @PostMapping("/doInactive")
  public ResponseEntity<RegisterResponse> doInactive(@RequestBody User user) {
    userService.doInactive(user);

    return ResponseEntity.ok(new RegisterResponse("success"));
  }

  @PostMapping("/findById")
  public ResponseEntity<RegisterResponse> searchUser(@RequestBody Long id) {
    userService.findUserById(id);

    return ResponseEntity.ok(new RegisterResponse("success"));
  }
}
