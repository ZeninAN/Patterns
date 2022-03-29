import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public class CardTest {

    @BeforeEach
    public void setUp(){
        open("http://localhost:9999/");
    }
}
