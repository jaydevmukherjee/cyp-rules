package org.copart.cyp.wiki.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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



public class WikiRulesParse {
	public static void main(String[] args) {
		/*FetchProperties fetchProperties = new FetchProperties();
		fetchProperties.getProperty();
		parseWikiToExcel();*/
		/*List<String> reslutUIField = getExcelFieldData(0);
		for(String fields : reslutUIField){
			System.out.println(fields);
		}		
		List<String> resultCondition = getExcelFieldData(6);		
		for(String fields : resultCondition){
			System.out.println(fields);			
		}
		List<String> resultRequiredCondition = getExcelFieldData(7);
		for(String fields : resultRequiredCondition){
			System.out.println(fields);			
		}*/
		List<String> rows = getExcelFieldData(0);
		List<String> resultCondition = getExcelFieldData(6);
		List<String> resultRequiredCondition = getExcelFieldData(7);
		List<String> completeRow = new ArrayList<String>();
		completeRow.addAll(rows);
		completeRow.addAll(resultCondition);
		completeRow.addAll(resultRequiredCondition);
		//System.out.println(completeRow.size());
		//System.out.println(completeRow.get(201));
		/*for(String fields : completeRow){
			System.out.println("Output"+fields);			
		}*/
		//parseWikiToExcel();
		//createRulesXML("de","AssignmentEntered");
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
		List<String> rows = getExcelFieldData(0);
		List<String> resultCondition = getExcelFieldData(6);
		List<String> resultRequiredCondition = getExcelFieldData(7);
		List<String> completeRow = new ArrayList<String>();
		completeRow.addAll(rows);
		completeRow.addAll(resultCondition);
		completeRow.addAll(resultRequiredCondition);
		rootPOJO root = new rootPOJO(rootname);
		root = createRootObjTemp(root, completeRow);
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
			int rowCount = rows.size();
			for(int index=0; index <= rowCount; index++){
				fieldsPOJO field = new fieldsPOJO();
				//if()
				field.setFieldName(rows.get(index));
			}
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
		//final StringBuilder texto = new StringBuilder();
		//String texto=null;
		FileInputStream fileInputStream = null;
		List<String> fields = null;
		//XSSFExcelExtractor extractor = null;
		try {
			final File file = new File(System.getProperty("user.dir")+"/RulesExcel/03faa90791d.xlsx");
			fileInputStream = new FileInputStream(file.getAbsolutePath());
			final XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			int rowIndex = 0;
			int sys=0;
			fields = new ArrayList<>();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();					
					for (rowIndex = 0; rowIndex <= sheet.getLastRowNum()-3; rowIndex++) {
						row = sheet.getRow(rowIndex);
						String cellValue;
						for (int colIndex = 0; colIndex <= columnNum; colIndex++) {
							if (colIndex == columnNum) {
								cell = row.getCell(colIndex);
								if (cell != null) {
									// Found column and there is value in the cell.
									cellValue = cell.getStringCellValue();
									//System.out.println(cellValue);
									fields.add(cellValue);
									//fields.removeAll(null);
									break;
								}
							}

						}
					}	

					break;
				}

				break;
			}

		} catch (final Exception ex) {

			ex.printStackTrace();

		} finally {

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
