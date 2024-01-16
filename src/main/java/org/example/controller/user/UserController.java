package org.example.controller.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.user.UserAccountDto;
import org.example.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
@Tag(name = "Пользователь")
public class UserController extends BaseController {

  private final UserService userService;

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/{user_id}")
  public ResponseEntity<?> getItemInfo(@PathVariable(value = "user_id") Long userId) {
    return ResponseEntity.ok(UserAccountDto.fromUserAccount(userService.getUserInfo(userId)));
  }
}
