
import com.codeborne.selenide.Condition;
import data.RegistrationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static data.Generator.generateDate;
import static data.Generator.generateRequestCard;
import static java.time.Duration.ofSeconds;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.openqa.selenium.Keys.chord;

public class CardTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void test() {
        RegistrationInfo registrationInfo = generateRequestCard("ru");
        String planningDate = generateDate(5, 1);
        $x("//input[@type='text']").setValue(registrationInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $x("//input[@name='name']").setValue(registrationInfo.getName());
        $x("//input[@name='phone']").setValue(registrationInfo.getPhone());
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .should(Condition.text("Встреча успешно забронирована на  " + planningDate), Duration.ofSeconds(15));
        String secondDate = generateDate(5, 4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondDate);
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content")
                .should(visible, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на  " + secondDate));
    }
    @Test
    void shouldSubmitComplexRequest() {
        RegistrationInfo registrationInfo = generateRequestCard("ru");
        $("[data-test-id='city'] input").val("ке");
        $(".input__menu").find(withText("Кемерово")).click();
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate dateOfMeeting = LocalDate.now().plusDays(7);
        String dateOfMeetingFormatted = dateOfMeeting.format(ofPattern("dd.MM.yyyy"));
        if (startDate.getMonthValue() != dateOfMeeting.getMonthValue()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(String.valueOf(dateOfMeeting.getDayOfMonth()))).click();
        $("[data-test-id='name'] input").setValue(registrationInfo.getName());
        $("[data-test-id='phone'] input").setValue(registrationInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $("button>.button__content").click();
        $("[class='notification__content']").shouldHave(text("Встреча успешно забронирована на " + dateOfMeetingFormatted), ofSeconds(15));

    }

}
