package org.example.controller;

import org.example.dto.item.ItemDto;
import org.example.dto.item.TypeDto;
import org.example.dto.pharmacy.PharmacyDto;
import org.example.dto.user.SpecialityDto;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.pharmacy.Pharmacy;
import org.example.entities.user.Speciality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestObjects {
    public static TypeDto commonType;
    public static TypeDto receiptType;
    public static TypeDto specialType;

    public static List<TypeDto> types;
    public static PharmacyDto firstPharmacy;
    public static PharmacyDto secondPharmacy;
    public static PharmacyDto[] pharmacies;
    public static ItemDto[] items;

    public static ItemDto receipt;
    public static ItemDto special;

    static {
        commonType = new TypeDto(1L, "common");

        receiptType = new TypeDto(2L, "receipt");
        specialType = new TypeDto(3L, "special");
        types = Arrays.asList(commonType, receiptType, specialType);

        SpecialityDto speciality = new SpecialityDto(1L, "терапевт");

        firstPharmacy = new PharmacyDto(1L, "Здоровье",
                "ул. Ленина, 10",
                "Пн-Пт: 9:00-18:00, Сб: 10:00-15:00",
                "+7 (123) 456-7890");
        secondPharmacy = new PharmacyDto(2L, "Фармация", "пр. Победы, 25",
                "Пн-Вс: 8:00-22:00",
                "+7 (987) 654-3210");
        pharmacies= new PharmacyDto[]{firstPharmacy, secondPharmacy};

        receipt = new ItemDto(1L, "Афобазол Ретард таблетки с пролонг высвобождением покрыт.плен.об. 30 мг 20 шт",650.0, "Фармстандарт-Лексредства, Россия", "https://f003.backblazeb2.com/file/propill/afobazol_20.jpg", receiptType ,null);
//        receipt.setInfo("Способ применения: Применяется внутрь. Дозировка: По 1 таблетке 1 раз в сутки, утром, независимо от приема пищи. Действующее вещество: Фабомотизол");
//        receipt.setPrice(650.0);
//        receipt.setName("Афобазол Ретард таблетки с пролонг высвобождением покрыт.плен.об. 30 мг 20 шт");
//        receipt.setManufacturer("Фармстандарт-Лексредства, Россия");
//        receipt.setType(receiptType);
//        receipt.setPictureUrl("https://f003.backblazeb2.com/file/propill/afobazol_20.jpg");

        special = new ItemDto(2L, "Артелак Баланс раствор офтальмологический увлажняющий 10 мл 1 шт",769.0,"Др. Герхард Манн ХФП, Германия","https://f003.backblazeb2.com/file/propill/artelak_balans.jpg", specialType,speciality);
//        special.setInfo("Способ применения: Рекомендуется закапывать Артелак Баланс в конъюнктивальный мешок каждого глаза. Дозировка: по 1 капле от 3 до 5 раз в день или, если это необходимо, чаще. Действующее вещество: Гиалуроновая кислота");
//        special.setPrice(769.0);
//        special.setName("Артелак Баланс раствор офтальмологический увлажняющий 10 мл 1 шт");
//        special.setManufacturer("Др. Герхард Манн ХФП, Германия");
//        special.setType(specialType);
//        special.setSpeciality(speciality);
//        special.setPictureUrl("https://f003.backblazeb2.com/file/propill/artelak_balans.jpg");

        items = new ItemDto[]{receipt, special};

    }
}
