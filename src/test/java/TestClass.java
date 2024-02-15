import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestClass {
    private WebDriver driver;
    private final String correctUsername = "Admin";
    private final String correctPassword = "admin123";
    By usernameLocator = By.name("username");
    By passwrodLocator = By.name("password");
    By loginButtonLocator = By.cssSelector(".oxd-button");

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public void login(String username, String password) {
        driver.findElement(usernameLocator).sendKeys(username);
        driver.findElement(passwrodLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();
    }

    @Test
    public void testWithCorrectUsernameAndPassword() {
        login(correctUsername,correctPassword);
        String dashboardText = driver.findElement(By.xpath("/html/body/div/div/div/header/div/div/span/h6")).getText();
        Assertions.assertEquals(dashboardText, "Dashboard");
    }

    @Test
    public void testWithCorrectUsernameButWrongPassword() {
        login(correctUsername,"admin1234");
        String errorText = driver.findElement(By.xpath("//body/div[@id='app']/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/p[1]")).getText();
        Assertions.assertEquals(errorText, "Invalid credentials");
    }

    @Test
    public void testWithWrongUsernameButCorrectPassword() {
        login("Admin1",correctPassword);
        String errorText = driver.findElement(By.xpath("//body/div[@id='app']/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/p[1]")).getText();
        Assertions.assertEquals(errorText, "Invalid credentials");
    }

    @Test
    public void testWithWrongUsernameAndPassword() {
        login("Admin1","admin1234");
        String errorText = driver.findElement(By.xpath("//body/div[@id='app']/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/p[1]")).getText();
        Assertions.assertEquals(errorText, "Invalid credentials");
    }

    @Test
    public void testWithBlankUsernameAndPassword() {
        login("","");
        String errorText = driver.findElement(By.xpath("//input[@name='username']/../../span[text()='Required']")).getText();
        Assertions.assertEquals(errorText, "Required");
        errorText = driver.findElement(By.xpath("//input[@name='password']/../../span[text()='Required']")).getText();
        Assertions.assertEquals(errorText, "Required");
    }
}
