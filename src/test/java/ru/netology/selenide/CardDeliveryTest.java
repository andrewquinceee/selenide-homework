package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        Configuration.baseUrl = "http://localhost:9999";
    }

    @Test
    void shouldSubmitForm() {
        open("/");

        String plannedDate = LocalDate.now().plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        // Город
        $("[data-test-id='city'] input").setValue("Москва");

        // Дата: разбиваем на отдельные действия, чтобы избежать ошибки void
        SelenideElement dateField = $("[data-test-id='date'] input");
        dateField.doubleClick(); // Выделяем текст
        dateField.sendKeys(Keys.BACK_SPACE); // Удаляем его
        dateField.setValue(plannedDate); // Вводим новую дату

        // ФИО
        $("[data-test-id='name'] input").setValue("Иван Иванов");

        // Телефон
        $("[data-test-id='phone'] input").setValue("+79991234567");

        // Чекбокс согласия
        $("[data-test-id='agreement'] .checkbox__text").click();

        // Кнопка "Запланировать"
        $("button.button").click();

        // Проверка успешного уведомления
        $(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
