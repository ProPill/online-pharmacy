package org.example.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.user.UserAccountDto;
import org.example.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
@Tag(name = "Пользователь")
public class UserController extends BaseController {

  private final UserService userService;

  @Operation(
      summary = "Получение информации о пользователе",
      description = "Получение информации о пользователе по id пользователя")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/{user_id}")
  public ResponseEntity<?> getItemInfo(@PathVariable(value = "user_id") Long userId) {
    return ResponseEntity.ok(UserAccountDto.fromUserAccount(userService.getUserInfo(userId)));
  }
}
