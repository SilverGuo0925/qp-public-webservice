package qp.scs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import qp.scs.Greeting;
import qp.scs.dto.request.LoginRequestDTO;
import qp.scs.dto.request.RequestDTO;
import qp.scs.dto.response.LoginResponseDTO;
import qp.scs.dto.response.ProfileDetailResponseDTO;
import qp.scs.model.User;
import qp.scs.service.UserService;

@RestController
@RequestMapping(path = "/api")
public class ContentController {
	
	@Autowired
	private UserService userService;
	
//	@RequestMapping(path = "/exportFile", method= RequestMethod.POST)
//	  public ProfileDetailResponseDTO login(@Validated @RequestBody RequestDTO request) {
//		User user = userService.getUserFromToken(request.token);
//			
//		// [ Quick Profile query ] --------------------
//		ProfileDetailResponseDTO response = new ProfileDetailResponseDTO(user.username);
//		return response;
//		
//	  }
	
	  @RequestMapping("/exportFile")
	  public void greeting(HttpServletResponse response)  throws IOException {
		  
			Resource resource = new ClassPathResource("Quotation.xlsx");

		    FileInputStream file = new FileInputStream(resource.getFile());
		  
		  //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
		    
//		  XSSFWorkbook workbook = new XSSFWorkbook();
//		  XSSFSheet sheet = workbook.createSheet("Drivers");
            
            XSSFSheet sheet = workbook.getSheet("Quote");
		  
            sheet.getRow(9).getCell(0).setCellType(CellType.STRING);
            sheet.getRow(9).getCell(0).setCellValue("Silync Technology Pte Ltd");
            
		  userService.export(workbook, response, "application/vnd.ms-excel");
			
	
	  }
	

}
