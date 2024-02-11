package org.example.controller.item;

import com.backblaze.b2.client.exceptions.B2Exception;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.item.ItemDto;
import org.example.service.item.ItemCreationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
@Tag(name = "Товар")
public class ItemCreationController extends BaseController {

  private final ItemCreationService itemCreationService;

  @Operation(
      summary = "Добавление товара",
      description = "Добавление товара в базу данных фармацевтом")
  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> addItemByAdmin(
      @RequestParam(value = "name") @Size(max = 100) String name,
      @RequestParam(value = "price") Double price,
      @RequestParam(value = "manufacturer") @Size(max = 100) String manufacturer,
      @RequestParam(value = "info") @Size(max = 500) String info,
      @RequestParam(value = "picture_url") MultipartFile file,
      @RequestParam(value = "type_id") Long typeId,
      @RequestParam(value = "speciality_id", required = false)
      @Nullable
      @Value("${specialityId:null}")
      Long specialityId)
      throws IOException, B2Exception {
    return ResponseEntity.ok(
        ItemDto.fromItem(
            itemCreationService.addItem(
                name, price, manufacturer, info, file, typeId, specialityId)));
  }
}
