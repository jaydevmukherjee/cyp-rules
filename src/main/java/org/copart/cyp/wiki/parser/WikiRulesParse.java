package org.copart.cyp.wiki.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.copart.cyp.assignment.pojo.fieldsPOJO;
import org.copart.cyp.assignment.pojo.rootPOJO;
import org.copart.cyp.common.utils.FetchProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.fasterxml.jackson.databind.util.TypeKey;

public class WikiRulesParse {
	public static void main(String[] args) {
		FetchProperties fetchProperties = new FetchProperties();
		fetchProperties.getProperty();
		/*
		parseWikiToExcel();*/

		/*List<String> reslutUIField = getExcelFieldDataNew(0);
		System.out.println(reslutUIField.size());
		for(String fields : reslutUIField){
			System.out.println(fields);
		}*/

		/*List<String> rows = null;
		List<String> resultCondition = null;
		List<String> resultRequiredCondition = null;
		List<String> resultReadableCondition = null;
		rows = getExcelFieldData(0);
		resultCondition = getExcelFieldData(6);
		resultRequiredCondition = getExcelFieldData(7);
		resultReadableCondition = getExcelFieldData(8);
		List<String> completeRow = new ArrayList<String>();
		completeRow.addAll(rows);
		completeRow.addAll(resultCondition);
		completeRow.addAll(resultRequiredCondition);
		completeRow.addAll(resultReadableCondition);
		System.out.println(completeRow);
		System.out.println(completeRow.size());
		System.out.println(completeRow.get(624));*/
		
		createRulesXML("de","AssignmentEntry");
	}

	static void parseWikiToExcel() {
		String RulesExcelPath = System.getProperty("user.dir") + "/RulesExcel";
		String ChromeDriverPath = System.getProperty("user.dir") + "/driver/chromedriver.exe";
		String wikiToExcelParserJS = "javascript:(function(){var form=document.createElement('form');"
				+ "var txt=document.createElement('textarea');" + "txt.setAttribute('name','table');"
				+ "txt.value=$('table')[1].innerHTML;" + "form.appendChild(txt);"
				+ "form.setAttribute('method','post');form.setAttribute('action','https://g-cobaltapi-dev6.copart.com/wiki/excelify');document.body.appendChild(form);form.submit();})();";
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", RulesExcelPath);
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--disable-extensions");
		caps.setCapability(ChromeOptions.CAPABILITY, options);

		System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
		WebDriver driver = new ChromeDriver(caps);
		JavascriptExecutor js;
		try {
			driver.get(System.getProperty("wikiLink"));
			driver.findElement(By.id("os_username")).sendKeys(System.getProperty("wikiUserName"));
			driver.findElement(By.id("os_password")).sendKeys(System.getProperty("wikiPassword"));
			driver.findElement(By.id("loginButton")).click();
			Thread.sleep(5000);
			if (driver instanceof JavascriptExecutor) {
				js = (JavascriptExecutor) driver;
				js.executeScript(wikiToExcelParserJS);
			}
			Thread.sleep(30000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}

	/**
	 * Will create rules XML for Germany.
	 * 
	 */
	static void createRulesXMLDE(String rootname){
		OutputStream outputStream = null; 
		try{
			List<String> rows = getExcelFieldData(0);
			List<String> resultCondition = getExcelFieldData(6);
			List<String> resultRequiredCondition = getExcelFieldData(7);
			List<String> resultReadableCondition = getExcelFieldData(8);
			List<String> completeRow = new ArrayList<String>();
			completeRow.addAll(rows);
			completeRow.addAll(resultCondition);
			completeRow.addAll(resultRequiredCondition);
			completeRow.addAll(resultReadableCondition);
			rootPOJO root = new rootPOJO(rootname);
			root = createRootObjTemp(root, completeRow);
			StringWriter sw = new StringWriter();
	        JAXBContext jaxbContext = JAXBContext.newInstance(rootPOJO.class);
	        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        jaxbMarshaller.marshal(root, sw);
	        String xmlString = sw.toString();
	        System.out.println(xmlString);
	        File xmlFile = new File(System.getProperty("user.dir") + System.getProperty("deRulesXML"));
	        if (!xmlFile.exists())
	            xmlFile.createNewFile();
	        outputStream = new FileOutputStream(System.getProperty("user.dir") + System.getProperty("deRulesXML"));
	        jaxbMarshaller.marshal(root, outputStream);
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	/**
	 * Will create rules XML for India.
	 * 
	 */
	static void createRulesXMLIN(){

	}

	/**
	 * Will create rules XML for Spain.
	 * 
	 */
	static void createRulesXMLES(){

	}

	public static rootPOJO createRootObjTemp(rootPOJO root, List<String> rows) {		
		try{
			List<fieldsPOJO> fields = new LinkedList<>();
			fieldsPOJO field;
			int rowCount = rows.size();
			for(int index=2; index <= 207; index++){	
				field = new fieldsPOJO();
				field.setFieldName(rows.get(index));
				field.setCondition(rows.get(index+208));
				field.setRequiredCondition(rows.get(index+416));
				field.setReadableCondition(rows.get(index+624));
				fields.add(field);
			}
			root.setList(fields);
			return root;
		}catch(Exception e){
			return null;
		}
	}

	static void createRulesXML(String country, String rootname){

		switch(country){
		case "de":
			createRulesXMLDE(rootname);
			break;
		case "in":
			createRulesXMLIN();
		case "es":
			createRulesXMLES();
			break;
		}
	}

	static List<String> getExcelFieldData(int columnNum){
		List<String> fields = null;
		FileInputStream fileInputStream = null;
		try{
			fields = new ArrayList<String>();			
			final File file = new File(System.getProperty("user.dir")+"/RulesExcel/8eaa0f99878.xlsx");
			fileInputStream = new FileInputStream(file.getAbsolutePath());
			final XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);

			for(Row row : sheet) {
				Cell cell = row.getCell(columnNum);
				if(cell != null) {
					String cellValue = cell.getStringCellValue();
					fields.add(cellValue);
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally {

			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return fields;
	}

}
