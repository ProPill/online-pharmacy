package org.example.service.item;

import static org.example.exception.TypicalServerExceptions.INVALID_LENGTH;
import static org.example.exception.TypicalServerExceptions.INVALID_PRICE;
import static org.example.exception.TypicalServerExceptions.TYPE_NOT_FOUND;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.contentSources.B2ContentTypes;
import com.backblaze.b2.client.contentSources.B2FileContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.repository.item.ItemRepository;
import org.example.repository.item.TypeRepository;
import org.example.repository.user.SpecialityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemCreationService {

  private final ItemRepository itemRepository;
  private final TypeRepository typeRepository;
  private final SpecialityRepository specialityRepository;

  @Value("${userAgent}")
  private String USER_AGENT;

  @Value("${appKey}")
  private String APP_KEY;

  @Value("${appKeyId}")
  private String APP_KEY_ID;

  @Value("${bucketId}")
  private String BUCKET_ID;

  @Value("${bucketUrl}")
  private String BUCKET_URL;

  @Transactional
  public Item addItem(
      String name,
      Double price,
      String manufacturer,
      String info,
      MultipartFile file,
      Long typeId,
      Long specialityId)
      throws IOException, B2Exception {
    Optional<Type> type = typeRepository.findById(typeId);
    if (type.isEmpty()) {
      TYPE_NOT_FOUND.throwException();
    }
    if (price <= 0) {
      INVALID_PRICE.throwException();
    }
    if (name.length() > 100) {
      INVALID_LENGTH.throwException();
    }
    if (manufacturer.length() > 100) {
      INVALID_LENGTH.throwException();
    }
    if (info.length() > 500) {
      INVALID_LENGTH.throwException();
    }
    B2StorageClient client =
        B2StorageClientFactory.createDefaultFactory().create(APP_KEY_ID, APP_KEY, USER_AGENT);
    File tempFile = File.createTempFile("upload", null);
    file.transferTo(tempFile);
    final B2ContentSource source = B2FileContentSource.build(tempFile);
    final String fileName = file.getOriginalFilename();
    B2UploadFileRequest request =
        B2UploadFileRequest.builder(BUCKET_ID, fileName, B2ContentTypes.B2_AUTO, source)
            .setCustomField("color", "blue")
            .build();
    client.uploadSmallFile(request);
    Item item = new Item();
    item.setName(name);
    item.setPrice(price);
    item.setManufacturer(manufacturer);
    item.setInfo(info);
    item.setPictureUrl(BUCKET_URL + fileName);
    item.setType(type.get());
    if (specialityId != null) {
      Optional<Speciality> spec = specialityRepository.findById(specialityId);
      spec.ifPresent(item::setSpeciality);
    }
    itemRepository.save(item);
    return item;
  }
}
