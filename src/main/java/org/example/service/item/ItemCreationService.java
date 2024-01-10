package org.example.service.item;

import static org.example.exception.TypicalServerExceptions.NOT_FOUND;

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

  public Item addItem(
      String name,
      Double price,
      String manufacturer,
      MultipartFile file,
      Long typeId,
      Long specialityId)
      throws IOException, B2Exception {
    Optional<Type> type = typeRepository.findById(typeId);
    Optional<Speciality> spec = specialityRepository.findById(specialityId);
    if (type.isEmpty()) {
      NOT_FOUND.throwException();
    }
    B2StorageClient client =
        B2StorageClientFactory.createDefaultFactory().create(APP_KEY_ID, APP_KEY, USER_AGENT);
    File tempFile = File.createTempFile("upload", null);
    file.transferTo(tempFile);
    final B2ContentSource source = B2FileContentSource.build(tempFile);
    final String fileName = "demo/" + file.getOriginalFilename();
    B2UploadFileRequest request =
        B2UploadFileRequest.builder(BUCKET_ID, fileName, B2ContentTypes.B2_AUTO, source)
            .setCustomField("color", "blue")
            .build();
    client.uploadSmallFile(request);
    Item item = new Item();
    item.setName(name);
    item.setPrice(price);
    item.setManufacturer(manufacturer);
    item.setPictureUrl("https://f003.backblazeb2.com/file/propill/" + fileName);
    item.setType(type.get());
    spec.ifPresent(item::setSpeciality);
    itemRepository.save(item);
    return item;
  }
}
