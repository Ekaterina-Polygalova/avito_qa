package tests;

import io.qameta.allure.Description;
import model.ErrorResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import io.restassured.response.Response;
import model.StatisticResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static constants.ErrorConstants.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static specs.Specification.*;

@DisplayName("Проверки получения статистики")
public class GetStatisticTest extends BaseTest {

    String id = "a39def56-8dd7-4a7d-95d3-66b62a1fcea7";
    Integer expectedContacts = 3;
    Integer expectedLikes = 500;
    Integer expectedViewCount = 1523;

    @Test
    @Description("Получение статистики по айтем id")
    @DisplayName("Получение статистики по айтем id")
    public void getStatisticTest() {
        Response response = requests.getStatistic(id);
        responseSpec(SC_OK);
        StatisticResponse[] statisticResponse = response.as(StatisticResponse[].class);
        checkers.checkGetStatistic(statisticResponse, expectedContacts, expectedLikes, expectedViewCount);
    }

    @Test
    @Description("Поиск статистики несуществующего объявления")
    @DisplayName("Поиск статистики несуществующего объявления")
    public void notFoundStatisticTest() {
        Response response = requests.getStatistic(NOT_EXIST_ID);
        responseSpec(SC_BAD_REQUEST);
        ErrorResponse errorResponses = response.as(ErrorResponse.class);
        checkers.checkNotFound(errorResponses, STATISTIC_NOT_FOUND_MESSAGE);
    }

    @ParameterizedTest
    @Description("Получить статистику по объявлению с невалидными данными")
    @DisplayName("Получить статистику по объявлению с невалидными данными")
    @ValueSource(strings = {"12345", "Объявление", "Item", "-_.~"})
    public void getStatisticInvalidIdTest(String invalidId) {
        Response response = requests.getStatistic(invalidId);
        responseSpec(SC_BAD_REQUEST);
        ErrorResponse errorResponses = response.as(ErrorResponse.class);
        checkers.checkBadRequest(errorResponses, INVALID_ITEM_ID_ERROR_MESSAGE);
    }
}
