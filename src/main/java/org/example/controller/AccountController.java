package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.UserAccountDto;
import org.example.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController extends BaseController {
  private final AccountService accountService;

  @PostMapping("/registry")
  public ResponseEntity<?> registration(
      @RequestParam(value = "full_name") String fullName,
      @RequestParam(value = "phone") String phone,
      @RequestParam(value = "password") String password) {
    return ResponseEntity.ok(
        UserAccountDto.fromUserAccount(accountService.register(fullName, phone, password)));
  }
}
