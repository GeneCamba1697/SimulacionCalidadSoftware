package com;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ActividadSimulacion {

	private WebDriver driver;

	@Before
	public void SetUP() {

		ChromeOptions co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		co.addArguments("--incognito");
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/Driver/chromedriver.exe");

		driver = new ChromeDriver(co);
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com");

	}

	@Test
	public void VerificarLogin() {

		WebElement txtUsername = driver.findElement(By.id("user-name"));
		WebElement txtPassword = driver.findElement(By.id("password"));
		WebElement btnLogin = driver.findElement(By.id("login-button"));

		System.out.println("Ingresando usuario...");
		txtUsername.sendKeys("standard_user");

		System.out.println("Ingresando contraseña...");
		txtPassword.sendKeys("secret_sauce");

		System.out.println("Haciendo clic en Login...");
		btnLogin.click();

		WebElement lblProducts = driver.findElement(By.className("title"));
		String textoObtenido = lblProducts.getText();

		assertEquals("Products", textoObtenido);

		System.out.println("Login exitoso. Texto encontrado: " + textoObtenido);
	}

	@Test
	public void AgregarProductoCarrito() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement txtUsername = driver.findElement(By.id("user-name"));
		WebElement txtPassword = driver.findElement(By.id("password"));
		WebElement btnLogin = driver.findElement(By.id("login-button"));

		txtUsername.sendKeys("standard_user");
		txtPassword.sendKeys("secret_sauce");
		btnLogin.click();

		WebElement lblProducts = driver.findElement(By.className("title"));
		String textoObtenido = lblProducts.getText();
		assertEquals("Products", textoObtenido);

		WebElement btnAddToCart = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
		btnAddToCart.click();
		WebElement carritoBadge = driver.findElement(By.className("shopping_cart_badge"));
		assertEquals("1", carritoBadge.getText());

		WebElement iconoCarrito = wait
				.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link")));
		iconoCarrito.click();

		WebElement nombreProductoEnCarrito = driver.findElement(By.className("inventory_item_name"));
		assertEquals("Sauce Labs Backpack", nombreProductoEnCarrito.getText());

		driver.findElement(By.id("checkout")).click();

		driver.findElement(By.id("first-name")).sendKeys("Genesis");
		driver.findElement(By.id("last-name")).sendKeys("Camba");
		driver.findElement(By.id("postal-code")).sendKeys("12345");
		driver.findElement(By.id("continue")).click();

		WebElement resumen = driver.findElement(By.className("summary_info"));
		assertTrue(resumen.isDisplayed());

		driver.findElement(By.id("finish")).click();

		WebElement mensajeGracias = driver.findElement(By.className("complete-header"));
		assertEquals("Thank you for your order!", mensajeGracias.getText());
	}

	@Test
	public void RemoverProductoCarrito() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement txtUsername = driver.findElement(By.id("user-name"));
		WebElement txtPassword = driver.findElement(By.id("password"));
		WebElement btnLogin = driver.findElement(By.id("login-button"));

		txtUsername.sendKeys("standard_user");
		txtPassword.sendKeys("secret_sauce");
		btnLogin.click();

		WebElement lblProducts = driver.findElement(By.className("title"));
		assertEquals("Products", lblProducts.getText());

		WebElement btnAddToCart = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
		btnAddToCart.click();
		WebElement carritoBadge = driver.findElement(By.className("shopping_cart_badge"));
		assertEquals("1", carritoBadge.getText());

		WebElement iconoCarrito = wait
				.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link")));
		iconoCarrito.click();

		WebElement nombreProductoEnCarrito = driver.findElement(By.className("inventory_item_name"));
		assertEquals("Sauce Labs Backpack", nombreProductoEnCarrito.getText());

		WebElement btnRemove = driver.findElement(By.id("remove-sauce-labs-backpack"));
		btnRemove.click();

		boolean productoAparece;
		try {
			driver.findElement(By.className("inventory_item_name"));
			productoAparece = true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			productoAparece = false;
		}

		assertFalse("El producto fue eliminado correctamente del carrito", productoAparece);
	}

	@Test
	public void VerificarLogout() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement txtUsername = driver.findElement(By.id("user-name"));
		WebElement txtPassword = driver.findElement(By.id("password"));
		WebElement btnLogin = driver.findElement(By.id("login-button"));

		txtUsername.sendKeys("standard_user");
		txtPassword.sendKeys("secret_sauce");
		btnLogin.click();

		WebElement lblProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
		assertEquals("Products", lblProducts.getText());

		WebElement btnMenu = driver.findElement(By.id("react-burger-menu-btn"));
		btnMenu.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu-wrap")));

		WebElement btnLogout = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
		btnLogout.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
	}

	

	@After
	public void cierre() {
		// driver.quit();
	}
}
