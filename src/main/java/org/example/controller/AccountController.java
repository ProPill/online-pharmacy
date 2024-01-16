package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dto.user.UserAccountDto;
import org.example.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Аккаунт")
public class AccountController extends BaseController {
  private final AccountService accountService;

  @Operation(summary = "Регистрация", description = "Регистрация обычного пользователя")
  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/registry")
  public ResponseEntity<?> registration(
      @RequestParam(value = "full_name") String fullName,
      @RequestParam(value = "phone") String phone,
      @RequestParam(value = "password") String password) {
    return ResponseEntity.ok(
        UserAccountDto.fromUserAccount(accountService.register(fullName, phone, password)));
  }

  @Operation(summary = "Вход в лк", description = "Вход в лк пользователя")
  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestParam(value = "phone") String phone,
      @RequestParam(value = "password") String password) {
    return ResponseEntity.ok(UserAccountDto.fromUserAccount(accountService.login(phone, password)));
  }

  @Operation(summary = "Выход из лк", description = "Выход из лк пользователя")
  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestParam(value = "user_id") String userId) {
    return ResponseEntity.ok(
        UserAccountDto.fromUserAccount(accountService.logout(Long.valueOf(userId))));
  }
}
