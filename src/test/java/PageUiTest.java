import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PageUiTest {
    WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless", "--disable-gpu"));

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\miko0\\Новая папка\\CardOrder\\artifacts\\chromedriver.exe");
    }

    @AfterEach
    void down() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestVCorrectValues() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Василий");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String text = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @Test
    void shouldTestWithEnglishWords() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Maximilian Maximilian");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String text = driver.findElement(By.cssSelector(".input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldTestWithOneLetter() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("В");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String text = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @Test
    void shouldTestWithOneNumber() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Васильев Александр");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("7");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String text = driver.findElement(By.cssSelector(".input__sub")).getText();
        assertEquals("Укажите точно как в паспорте", text);
    }

    @Test
    void shouldTestWithEmptyNameField() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String text = driver.findElement(By.cssSelector(".input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text);
    }
}