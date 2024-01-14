package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.UserAccountDto;
import org.example.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController extends BaseController {
  private final AccountService accountService;

  @CrossOrigin
  @PostMapping("/registry")
  public ResponseEntity<?> registration(
      @RequestParam(value = "full_name") String fullName,
      @RequestParam(value = "phone") String phone,
      @RequestParam(value = "password") String password) {
    return ResponseEntity.ok(
        UserAccountDto.fromUserAccount(accountService.register(fullName, phone, password)));
  }

  @CrossOrigin
  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestParam(value = "phone") String phone,
      @RequestParam(value = "password") String password) {
    return ResponseEntity.ok(UserAccountDto.fromUserAccount(accountService.login(phone, password)));
  }

  @CrossOrigin
  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestParam(value = "user_id") String userId) {
    return ResponseEntity.ok(
        UserAccountDto.fromUserAccount(accountService.logout(Long.valueOf(userId))));
  }
}
