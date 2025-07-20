package com;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.time.Duration;
import static org.junit.Assert.*;

public class ActividadSimulacion2 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        co.addArguments("--incognito");
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/Driver/chromedriver.exe");

        driver = new ChromeDriver(co);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://demoqa.com/");
    }

    @Test
    public void RegistroUsuario() {
    	
    	WebElement opcionForms = driver.findElement(By.xpath("//h5[text()='Forms']/ancestor::div[@class='card mt-4 top-card']"));
    	opcionForms.click();
        
        WebElement practiceForm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Practice Form']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", practiceForm);

        practiceForm.click();

        ((JavascriptExecutor) driver).executeScript("document.getElementById('fixedban').style.display='none'");
        ((JavascriptExecutor) driver).executeScript("document.getElementsByTagName('footer')[0].style.display='none'");

        driver.findElement(By.id("firstName")).sendKeys("Genesis");
        driver.findElement(By.id("lastName")).sendKeys("Camba");
        driver.findElement(By.id("userEmail")).sendKeys("genesiscamba@gmail.com");
        driver.findElement(By.xpath("//label[text()='Female']")).click();
        driver.findElement(By.id("userNumber")).sendKeys("1234567890");

        driver.findElement(By.id("dateOfBirthInput")).click();
        new Select(driver.findElement(By.className("react-datepicker__month-select"))).selectByVisibleText("April");
        new Select(driver.findElement(By.className("react-datepicker__year-select"))).selectByVisibleText("1997");
        driver.findElement(By.xpath("//div[@class='react-datepicker__day react-datepicker__day--016']")).click();

        WebElement subjects = driver.findElement(By.id("subjectsInput"));
        subjects.sendKeys("English");
        subjects.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//label[text()='Sports']")).click();

        String path = new File("src/test/resources/User.jpg").getAbsolutePath();
        driver.findElement(By.id("uploadPicture")).sendKeys(path);

        driver.findElement(By.id("currentAddress")).sendKeys("Costa Rica");

        WebElement estado = driver.findElement(By.id("react-select-3-input"));
        estado.sendKeys("NCR");
        estado.sendKeys(Keys.ENTER);

        WebElement ciudad = driver.findElement(By.id("react-select-4-input"));
        ciudad.sendKeys("Delhi");
        ciudad.sendKeys(Keys.ENTER);

        driver.findElement(By.id("submit")).click();
    }

    @Test
    public void TextBox() {
    	
    	WebElement opcionElements = driver.findElement(By.xpath("//h5[text()='Elements']/ancestor::div[@class='card mt-4 top-card']"));
    	opcionElements.click();
    	
    	 WebElement textBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Text Box']")));
    	 
    	 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textBox);
    	 
    	 WebElement textBoxOption = driver.findElement(By.xpath("//span[text()='Text Box']"));
         textBoxOption.click();
    	
         String nombre = "Genesis Camba Burguillos";
         String correo = "genesiscamba@egmail.com";
         String direccionActual = "Guapiles";
         String direccionPermanente = "San Jose";

         driver.findElement(By.id("userName")).sendKeys(nombre);
         driver.findElement(By.id("userEmail")).sendKeys(correo);
         driver.findElement(By.id("currentAddress")).sendKeys(direccionActual);
         driver.findElement(By.id("permanentAddress")).sendKeys(direccionPermanente);

         driver.findElement(By.id("submit")).click();

         String textoNombre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).getText(); 
         String textoCorreo = driver.findElement(By.id("email")).getText(); 
         String textoDireccionActual = driver.findElement(By.id("currentAddress")).getText(); 
         String textoDireccionPermanente = driver.findElement(By.id("permanentAddress")).getText(); 

         assertTrue("Nombre incorrecto", textoNombre.contains(nombre));
         assertTrue("Correo incorrecto", textoCorreo.contains(correo));
         assertTrue("Dirección actual incorrecta", textoDireccionActual.contains(direccionActual));
         assertTrue("Dirección permanente incorrecta", textoDireccionPermanente.contains(direccionPermanente));
     }
    @After
    public void cierre() {
        driver.quit();
    }
}
