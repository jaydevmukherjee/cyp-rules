package org.copart.cyp.wiki.parser;

import java.util.HashMap;
import java.util.Map;

import org.copart.cyp.common.utils.FetchProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WikiTableToExcel {
	public static void main(String[] args) {
		FetchProperties fetchProperties = new FetchProperties();
		fetchProperties.getProperty();
		parseWikiToExcel();
	}

	static void parseWikiToExcel() {
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", System.getProperty("user.dir")+"/RulesExcel");
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--disable-extensions");
		caps.setCapability(ChromeOptions.CAPABILITY, options);
		
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver(caps);
		JavascriptExecutor js;
		try {
			driver.get(System.getProperty("wikiLink"));
			driver.findElement(By.id("os_username")).sendKeys(
					System.getProperty("wikiUserName"));
			driver.findElement(By.id("os_password")).sendKeys(
					System.getProperty("wikiPassword"));
			driver.findElement(By.id("loginButton")).click();
			Thread.sleep(5000);
			if (driver instanceof JavascriptExecutor) {
				js = (JavascriptExecutor) driver;
				js.executeScript("javascript:(function(){var form=document.createElement('form');var txt=document.createElement('textarea');txt.setAttribute('name','table');txt.value=$('table')[1].innerHTML;form.appendChild(txt);form.setAttribute('method','post');form.setAttribute('action','https://g-cobaltapi-dev6.copart.com/wiki/excelify');document.body.appendChild(form);form.submit();})();");
			}
			Thread.sleep(30000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}
}
