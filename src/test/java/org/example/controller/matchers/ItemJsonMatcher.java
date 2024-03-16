package org.example.controller.matchers;

import org.example.dto.item.ItemDto;
import org.example.entities.item.Item;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ItemJsonMatcher {
    public static void compare(ResultActions resultActions, String prefix, ItemDto item) throws Exception {
        resultActions.andExpectAll(
                jsonPath(prefix + ".id").value(item.id()),
                jsonPath(prefix + ".name").value(item.name()),
                jsonPath(prefix + ".price").value(item.price()),
                jsonPath(prefix + ".manufacturer").value(item.manufacturer()),
                jsonPath(prefix + ".type.id").value(item.typeId().id()),
                jsonPath(prefix + ".type.name").value(item.typeId().name()));
        if (item.speciality()!=null){
            resultActions.andExpectAll(
                    jsonPath(prefix + ".speciality.id").value(item.speciality().id()),
                    jsonPath(prefix + ".speciality.name").value(item.speciality().name()));}
        else{
            resultActions.andExpect(
                    jsonPath(prefix + ".speciality").doesNotExist());}
    }
}
