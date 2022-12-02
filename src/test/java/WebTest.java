import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WebTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
//ок
    void shouldTestV1() {

        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Фассахова Алина");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79998886667");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("p[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
//неверное имя фамилия
    void shouldTestV2() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Fassahova Alina");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998886667");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
//пустое поле имя
    void shouldTestV3() {

        // driver.findElement(By.cssSelector("span[data-test-id=name].input invalid.input__sub")).sendKeys("");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79998886667");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test   // пустая форма
    public void shouldTestV4() {

        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test   // пустое поле телефона
    public void shouldTestV5() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Фассахова Алина");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();


        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test   //Задача №2 - валидация поля телефона
    public void shouldTestV6() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Фассахова Алина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790123456");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();


        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test   // не отмеченный чек бокс
    public void shouldTestV7() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Фассахова Алина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998886667");
        driver.findElement(By.className("button")).click();


        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных" +
        " и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();

        assertEquals(expected, actual);

    }
}
