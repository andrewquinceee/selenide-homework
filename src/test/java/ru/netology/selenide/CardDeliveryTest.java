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
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;

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

        $("[data-test-id='city'] input").setValue("Москва");

        SelenideElement dateField = $("[data-test-id='date'] input");
        dateField.doubleClick();
        dateField.sendKeys(Keys.BACK_SPACE);
        dateField.setValue(plannedDate);

        $("[data-test-id='name'] input").setValue("Иван Иванов");

        $("[data-test-id='phone'] input").setValue("+79991234567");

        $("[data-test-id='agreement'] .checkbox__text").click();

        $("button.button").click();

        $(".notification__title")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно"))
                .shouldHave(text(plannedDate));
    }
}
