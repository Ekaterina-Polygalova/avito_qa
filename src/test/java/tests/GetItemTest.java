package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.ErrorResponse;
import model.ItemInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static constants.ErrorConstants.*;
import static org.apache.http.HttpStatus.*;
import static specs.Specification.responseSpec;

@DisplayName("Проверки получения информации об объявлении")
public class GetItemTest extends BaseTest {

    String id = "a39def56-8dd7-4a7d-95d3-66b62a1fcea7";
    String expectedName = "Донской сфинкс";
    Integer expectedPrice = 20000;
    Integer expectedSellerId = 358937;
    Integer expectedContacts = 3;
    Integer expectedLikes = 500;
    Integer expectedViewCount = 1523;

    @Test
    @Description("Получение информации об объявлении по itemID")
    @DisplayName("Получение информации об объявлении по itemID")
    public void getItemInfoById() {
        Response response = requests.getItem(id);
        responseSpec(SC_OK);
        ItemInfo[] itemInfo = response.as(ItemInfo[].class);
        checkers.checkItemInfoById(itemInfo, id, expectedName, expectedPrice, expectedSellerId, expectedContacts, expectedLikes, expectedViewCount);
    }

    @Test
    @Description("Поиск несуществующего объявления")
    @DisplayName("Поиск несуществующего объявления")
    public void getItemInfoByIdNotFound() {
        Response response = requests.getItem(NOT_EXIST_ID);
        responseSpec(SC_NOT_FOUND);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        checkers.checkNotFound(errorResponse, ITEM_NOT_FOUND_MESSAGE);
    }

    @ParameterizedTest
    @Description("Получение объявления по его идентификатору с невалидными данными")
    @DisplayName("Получение объявления по его идентификатору с невалидными данными")
    @ValueSource(strings = {"12345", "Объявление", "Item", "-_.~"})
    public void getItemInfoByInvalidId(String invalidId) {
        Response response = requests.getItem(invalidId);
        responseSpec(SC_BAD_REQUEST);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        checkers.checkBadRequest(errorResponse, INVALID_ITEM_ID_ERROR_MESSAGE);
    }
}
