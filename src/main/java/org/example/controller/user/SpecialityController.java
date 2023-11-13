package org.example.controller.user;

import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.user.SpecialityDto;
import org.example.service.user.SpecialityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/speciality")
@RequiredArgsConstructor
public class SpecialityController extends BaseController {

  private final SpecialityService specialityService;

  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(
        specialityService.getAll().stream().map(SpecialityDto::fromSpeciality).toList());
  }

  @GetMapping("/{user_id}")
  public ResponseEntity<?> getItemInfo(@PathVariable(value = "user_id") Long userId) {
    return ResponseEntity.ok(SpecialityDto.fromSpeciality(specialityService.getByUserId(userId)));
  }
}
