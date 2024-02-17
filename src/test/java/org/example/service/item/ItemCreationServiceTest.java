package org.example.service.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.backblaze.b2.client.exceptions.B2Exception;
import java.io.IOException;
import java.util.Optional;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.exception.ServerException;
import org.example.repository.item.ItemRepository;
import org.example.repository.item.TypeRepository;
import org.example.repository.user.SpecialityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ItemCreationServiceTest {

  @Mock private ItemRepository itemRepository;

  @Mock private TypeRepository typeRepository;

  @Mock private SpecialityRepository specialityRepository;

  @InjectMocks private ItemCreationService itemCreationService;

  @Test
  void addItem() throws IOException, B2Exception {
    String name = "Test Item";
    Double price = 100.0;
    String manufacturer = "Test Manufacturer";
    String info = "Test Info";
    MultipartFile file = createMockMultipartFile();
    Long typeId = 1L;
    Long specialityId = 1L;
    Type mockType = new Type();
    when(typeRepository.findById(typeId)).thenReturn(Optional.of(mockType));
    Speciality mockSpeciality = new Speciality();
    when(specialityRepository.findById(specialityId)).thenReturn(Optional.of(mockSpeciality));
    Item actualItem =
        itemCreationService.addItem(name, price, manufacturer, info, file, typeId, specialityId);
    assertNotNull(actualItem);
    assertEquals(name, actualItem.getName());
    assertEquals(price, actualItem.getPrice());
    assertEquals(manufacturer, actualItem.getManufacturer());
    assertEquals(info, actualItem.getInfo());
  }

  private MultipartFile createMockMultipartFile() {
    String fileName = "testFile.txt";
    byte[] content = "This is a mock file content".getBytes();
    return new MockMultipartFile(fileName, fileName, "text/plain", content);
  }

  @Test
  void addItem_specialityIdIsNull() throws IOException, B2Exception {
    String name = "Test Item";
    Double price = 100.0;
    String manufacturer = "Test Manufacturer";
    String info = "Test Info";
    MultipartFile file = createMockMultipartFile();
    Long typeId = 1L;
    Type mockType = new Type();
    when(typeRepository.findById(typeId)).thenReturn(Optional.of(mockType));
    Item actualItem =
        itemCreationService.addItem(name, price, manufacturer, info, file, typeId, null);
    assertNull(actualItem.getSpeciality());
  }

  @Test
  void addItem_typeNotFound() throws IOException, B2Exception {
    String name = "Test Item";
    Double price = 100.0;
    String manufacturer = "Test Manufacturer";
    String info = "Test Info";
    MultipartFile file = createMockMultipartFile();
    Long typeId = 1L;
    Long specialityId = 1L;
    when(typeRepository.findById(typeId)).thenReturn(Optional.empty());
    try {
      itemCreationService.addItem(name, price, manufacturer, info, file, typeId, specialityId);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("TYPE_NOT_FOUND", e.getCode());
      assertEquals("TYPE_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void addItem_invalidPrice() throws IOException, B2Exception {
    String name = "Test Item";
    Double price = -100.0;
    String manufacturer = "Test Manufacturer";
    String info = "Test Info";
    MultipartFile file = createMockMultipartFile();
    Long typeId = 1L;
    Long specialityId = 1L;
    Type mockType = new Type();
    when(typeRepository.findById(typeId)).thenReturn(Optional.of(mockType));
    try {
      itemCreationService.addItem(name, price, manufacturer, info, file, typeId, specialityId);
    } catch (ServerException e) {
      assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
      assertEquals("INVALID_PRICE", e.getCode());
      assertEquals("INVALID_PRICE", e.getMessage());
    }
  }

  @Test
  void addItem_invalidNameLength() throws IOException, B2Exception {
    String name =
        "qwertyqwqqwertyuioqwertyuiopqwertyuiopqwertyuiopwertyuiopqwertyuiopqwertyuerty7uio";
    Double price = 100.0;
    String manufacturer = "Test Manufacturer";
    String info = "Test Info";
    MultipartFile file = createMockMultipartFile();
    Long typeId = 1L;
    Long specialityId = 1L;
    Type mockType = new Type();
    when(typeRepository.findById(typeId)).thenReturn(Optional.of(mockType));
    try {
      itemCreationService.addItem(name, price, manufacturer, info, file, typeId, specialityId);
    } catch (ServerException e) {
      assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
      assertEquals("INVALID_LENGTH", e.getCode());
      assertEquals("INVALID_LENGTH", e.getMessage());
    }
  }

  @Test
  void addItem_invalidManufacturerLength() throws IOException, B2Exception {
    String name = "qwerty";
    Double price = 100.0;
    String manufacturer =
        "qwertyqwqqwertyuioqwertyuiopqwertyuiopqwertyuiopwertyuiopqwertyuiopqwertyuerty7uio";
    String info = "Test Info";
    MultipartFile file = createMockMultipartFile();
    Long typeId = 1L;
    Long specialityId = 1L;
    Type mockType = new Type();
    when(typeRepository.findById(typeId)).thenReturn(Optional.of(mockType));
    try {
      itemCreationService.addItem(name, price, manufacturer, info, file, typeId, specialityId);
    } catch (ServerException e) {
      assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
      assertEquals("INVALID_LENGTH", e.getCode());
      assertEquals("INVALID_LENGTH", e.getMessage());
    }
  }

  @Test
  void addItem_invalidInfoLength() throws IOException, B2Exception {
    String name = "qwerty";
    Double price = 100.0;
    String manufacturer = "qwerty";
    String info =
        "qwertyqwqqwertyuioqwertyuiopqwertyuiopqwertyuiopwertyuiopqwertyuiopqwertyuerty7uio"
            + "qwertyqwqqwertyuioqwertyuiopqwertyuiopqwertyuiopwertyuiopqwertyuiopqwertyuerty7uio"
            + "qwertyqwqqwertyuioqwertyuiopqwertyuiopqwertyuiopwertyuiopqwertyuiopqwertyuerty7uio"
            + "qwertyqwqqwertyuioqwertyuiopqwertyuiopqwertyuiopwertyuiopqwertyuiopqwertyuerty7uio"
            + "qwertyqwqqwertyuioqwertyuiopqwertyuiopqwertyuiopwertyuiopqwertyuiopqwertyuerty7uio";
    MultipartFile file = createMockMultipartFile();
    Long typeId = 1L;
    Long specialityId = 1L;
    Type mockType = new Type();
    when(typeRepository.findById(typeId)).thenReturn(Optional.of(mockType));
    try {
      itemCreationService.addItem(name, price, manufacturer, info, file, typeId, specialityId);
    } catch (ServerException e) {
      assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
      assertEquals("INVALID_LENGTH", e.getCode());
      assertEquals("INVALID_LENGTH", e.getMessage());
    }
  }
}
