package core;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Safari_P1 {
	static WebDriver driver;
	static Properties p = new Properties();
	static Writer report;
	static String ls = System.getProperty("line.separator");

	///////////////////////////////////////////////////////////////////////////////
	public static boolean isElementPresent(By by) {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty())
			return true;
		else
			return false;
	}

	///////////////////////////////////////////////////////////////////////////////
	public static String getSize(By by) {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			return driver.findElement(by).getRect().getDimension().toString().replace(", ", "x");
		else
			return "";
	}

	///////////////////////////////////////////////////////////////////////////////
	public static String getLocation(By by) {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			return driver.findElement(by).getRect().getPoint().toString().replace(", ", "x");
		else
			return "";
	}

	///////////////////////////////////////////////////////////////////////////////
	public static void setValue(By by, String value) {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).sendKeys(p.getProperty(value));
	}

	///////////////////////////////////////////////////////////////////////////////
	public static String getValue(By by) {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			return driver.findElement(by).getText();
		else
			return "";
	}

	///////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) throws Exception {
		p.load(new FileInputStream("./input.properties"));
		report = new FileWriter("./firefox.csv", false);
		Logger.getLogger("").setLevel(Level.OFF);
		String driverPath = "";
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
			driverPath = "/usr/local/bin/firefox";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "c:\\windows\\geckodriver.exe";
		else
			throw new IllegalArgumentException("Unknown OS");
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver();
		///////////////////////////////////////////////////
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Capabilities caps = (((RemoteWebDriver) driver).getCapabilities());
		final long start = System.currentTimeMillis();
		driver.get(p.getProperty("url"));
		driver.manage().window().setSize(new Dimension(1000, 700));
		System.out.println("#,Browser,Page,Field,isPresent,Value,Size,Location");
		report.write("#,Browser,Page,Field,isPresent,Value,Size,Location");
		report.write(ls);
		report.write("01,Firefox,index.php,First Name," + isElementPresent(By.id(p.getProperty("fname_id"))) + ","
				+ p.getProperty("fname_value") + "," + getSize(By.id(p.getProperty("fname_id"))) + ","
				+ getLocation(By.id(p.getProperty("fname_id"))) + "\n");
		System.out.println("01,Firefox,index.php,First Name," + isElementPresent(By.id(p.getProperty("fname_id"))) + ","
				+ p.getProperty("fname_value") + "," + getSize(By.id(p.getProperty("fname_id"))) + ","
				+ getLocation(By.id(p.getProperty("fname_id"))));
		setValue(By.id(p.getProperty("fname_id")), "fname_value");
		report.write("02,Firefox,index.php,Last Name," + isElementPresent(By.id(p.getProperty("lname_id"))) + ","
				+ p.getProperty("lname_value") + "," + getSize(By.id(p.getProperty("lname_id"))) + ","
				+ getLocation(By.id(p.getProperty("lname_id"))) + "\n");
		System.out.println("02,Firefox,index.php,Last Name," + isElementPresent(By.id(p.getProperty("lname_id"))) + ","
				+ p.getProperty("lname_value") + "," + getSize(By.id(p.getProperty("lname_id"))) + ","
				+ getLocation(By.id(p.getProperty("lname_id"))));
		setValue(By.id(p.getProperty("lname_id")), "lname_value");
		report.write("03,Firefox,index.php,Email," + isElementPresent(By.id(p.getProperty("email_id"))) + ","
				+ p.getProperty("email_value") + "\n");
		System.out.println("03,Firefox,index.php,Email," + isElementPresent(By.id(p.getProperty("email_id"))) + ","
				+ p.getProperty("email_value"));
		setValue(By.id(p.getProperty("email_id")), "email_value");
		report.write("04,Firefox,index.php,Phone," + isElementPresent(By.id(p.getProperty("phone_id"))) + ","
				+ p.getProperty("phone_value") + "\n");
		System.out.println("04,Firefox,index.php,Phone," + isElementPresent(By.id(p.getProperty("phone_id"))) + ","
				+ p.getProperty("phone_value"));
		setValue(By.id(p.getProperty("phone_id")), "phone_value");
		driver.findElement(By.id(p.getProperty("submit_id"))).click();
		report.write("05,Firefox,confirmation.php,First Name," + isElementPresent(By.id(p.getProperty("fname_id")))
				+ "," + getValue(By.id(p.getProperty("fname_id"))) + "\n");
		System.out
				.println("05,Firefox,confirmation.php,First Name," + isElementPresent(By.id(p.getProperty("fname_id")))
						+ "," + getValue(By.id(p.getProperty("fname_id"))));
		report.write("06,Firefox,confirmation.php,Last Name," + isElementPresent(By.id(p.getProperty("lname_id"))) + ","
				+ getValue(By.id(p.getProperty("lname_id"))) + "\n");
		System.out.println("06,Firefox,confirmation.php,Last Name," + isElementPresent(By.id(p.getProperty("lname_id")))
				+ "," + getValue(By.id(p.getProperty("lname_id"))));
		report.write("07,Firefox,confirmation.php,Email," + isElementPresent(By.id(p.getProperty("email_id"))) + ","
				+ getValue(By.id(p.getProperty("email_id"))) + "\n");
		System.out.println("07,Firefox,confirmation.php,Email," + isElementPresent(By.id(p.getProperty("email_id")))
				+ "," + getValue(By.id(p.getProperty("email_id"))));
		report.write("08,Firefox,confirmation.php,Phone," + isElementPresent(By.id(p.getProperty("phone_id"))) + ","
				+ getValue(By.id(p.getProperty("phone_id"))) + "\n");
		System.out.println("08,Firefox,confirmation.php,Phone," + isElementPresent(By.id(p.getProperty("phone_id")))
				+ "," + getValue(By.id(p.getProperty("phone_id"))));
		final long finish = System.currentTimeMillis();
		report.flush();
		report.close();
		driver.quit();
		System.out.println("Response time: " + (finish - start) / 1000.0 + " seconds");
	}
}