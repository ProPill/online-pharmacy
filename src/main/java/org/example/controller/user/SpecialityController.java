package org.example.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.user.SpecialityDto;
import org.example.service.user.SpecialityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/speciality")
@RequiredArgsConstructor
@Tag(name = "Специальность")
public class SpecialityController extends BaseController {

  private final SpecialityService specialityService;

  @Operation(
      summary = "Получение всех специальностей",
      description = "Получение списка всех существующих специальностей врачей")
  @CrossOrigin
  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(
        specialityService.getAll().stream().map(SpecialityDto::fromSpeciality).toList());
  }

  @Operation(
      summary = "Получение специальности пользователя(врача)",
      description = "Получение специальности пользователя(врача) по id пользователя")
  @CrossOrigin
  @GetMapping("/{user_id}")
  public ResponseEntity<?> getItemInfo(@PathVariable(value = "user_id") Long userId) {
    return ResponseEntity.ok(SpecialityDto.fromSpeciality(specialityService.getByUserId(userId)));
  }
}
