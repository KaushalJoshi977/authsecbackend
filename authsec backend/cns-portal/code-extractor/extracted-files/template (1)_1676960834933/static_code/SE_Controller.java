"//package com.realnet.excel.controller;" + "\r\n" + 
"package com.realnet.template.controller;" + "\r\n" + 
"" + "\r\n" + 
"import java.io.BufferedReader;" + "\r\n" + 
"import java.io.ByteArrayInputStream;" + "\r\n" + 
"import java.io.ByteArrayOutputStream;" + "\r\n" + 
"import java.io.File;" + "\r\n" + 
"import java.io.IOException;" + "\r\n" + 
"import java.io.InputStream;" + "\r\n" + 
"import java.io.InputStreamReader;" + "\r\n" + 
"import java.nio.file.Files;" + "\r\n" + 
"import java.nio.file.Path;" + "\r\n" + 
"import java.nio.file.Paths;" + "\r\n" + 
"import java.nio.file.StandardCopyOption;" + "\r\n" + 
"import java.text.ParseException;" + "\r\n" + 
"import java.text.SimpleDateFormat;" + "\r\n" + 
"import java.util.ArrayList;" + "\r\n" + 
"import java.util.Date;" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.apache.poi.ss.usermodel.Cell;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.DataFormatter;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.Row;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.Sheet;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.Workbook;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.WorkbookFactory;" + "\r\n" + 
"import org.apache.poi.xssf.usermodel.XSSFWorkbook;" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"import org.springframework.core.io.InputStreamResource;" + "\r\n" + 
"import org.springframework.http.HttpHeaders;" + "\r\n" + 
"import org.springframework.http.HttpStatus;" + "\r\n" + 
"import org.springframework.http.MediaType;" + "\r\n" + 
"import org.springframework.http.ResponseEntity;" + "\r\n" + 
"import org.springframework.util.StringUtils;" + "\r\n" + 
"import org.springframework.web.bind.annotation.GetMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PathVariable;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PostMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RequestMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RequestParam;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RestController;" + "\r\n" + 
"import org.springframework.web.multipart.MultipartFile;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.Sr_Contact_type.Entity.Sr_Contact_type_t;" + "\r\n" + 
"import com.realnet.Sr_Contact_type.Repository.Sr_Contact_type_Repository;" + "\r\n" + 
"import com.realnet.Sr_State.Entity.Sr_State_t;" + "\r\n" + 
"import com.realnet.Sr_State.Repository.Sr_State_Repository;" + "\r\n" + 
"import com.realnet.Sr_category2.Entity.Sr_category2_t;" + "\r\n" + 
"import com.realnet.Sr_category2.Repository.Sr_category2_Repository;" + "\r\n" + 
"import com.realnet.Sr_customer.Entity.Sr_customer_t;" + "\r\n" + 
"import com.realnet.Sr_customer.Repository.Sr_customer_Repository;" + "\r\n" + 
"import com.realnet.Sr_handler.Entity.Sr_handler_t;" + "\r\n" + 
"import com.realnet.Sr_handler.Repository.Sr_handler_Repository;" + "\r\n" + 
"import com.realnet.Sr_impact2.Entity.Sr_impact2_t;" + "\r\n" + 
"import com.realnet.Sr_impact2.Repository.Sr_impact2_Repository;" + "\r\n" + 
"import com.realnet.Sr_priority2.Entity.Sr_priority2_t;" + "\r\n" + 
"import com.realnet.Sr_priority2.Repository.Sr_priority2_Repository;" + "\r\n" + 
"import com.realnet.Sr_urgency.Entity.Sr_urgency_t;" + "\r\n" + 
"import com.realnet.Sr_urgency.Repository.Sr_urgency_Repository;" + "\r\n" + 
"import com.realnet.template.repository.TemplatedataRepo;" + "\r\n" + 
"import com.realnet.template.service.FileUploadService;" + "\r\n" + 
"import com.realnet.users.entity1.AppUser;" + "\r\n" + 
"import com.realnet.users.service1.AppUserServiceImpl;" + "\r\n" + 
"" + "\r\n" + 
"@RestController" + "\r\n" + 
"@RequestMapping(\"api/template\")" + "\r\n" + 
"public class Controller {" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private TemplatedataRepo temprepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Sr_priority2_Repository priorityrepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Sr_impact2_Repository impactrepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Sr_urgency_Repository urgencyrepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Sr_State_Repository staterepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Sr_Contact_type_Repository contactrepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Sr_customer_Repository customerepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Sr_category2_Repository categoryrepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Sr_handler_Repository handlerepo;" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	FileUploadService fileupload;" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private AppUserServiceImpl userService;" + "\r\n" + 
"" + "\r\n" + 
"	@GetMapping(\"/demo/download/{file_type}\")" + "\r\n" + 
"" + "\r\n" + 
"	public ResponseEntity<?> demoTemplate(@PathVariable String file_type) throws IOException {" + "\r\n" + 
"" + "\r\n" + 
"		if (file_type.equalsIgnoreCase(\"priority\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_priority2_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Priority Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			ByteArrayInputStream in = demoTemplate(header, file_type);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"impact\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_impact2_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Impact Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			ByteArrayInputStream in = demoTemplate(header, file_type);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"urgency\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_urgency_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Urgency Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			ByteArrayInputStream in = demoTemplate(header, file_type);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"category\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_category2_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Category Name\", \"Customer Id\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			ByteArrayInputStream in = demoTemplate(header, file_type);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"state\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_State_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"State Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			ByteArrayInputStream in = demoTemplate(header, file_type);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"contact_type\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_Contact_type_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Contact Type Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			ByteArrayInputStream in = demoTemplate(header, file_type);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"customer\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_customer_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Customer Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			ByteArrayInputStream in = demoTemplate(header, file_type);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"handler\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_handler_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"User Id\", \"Role Id\", \"Is Active\", \"Effective Start Date\", \"Effective End date\" };" + "\r\n" + 
"			ByteArrayInputStream in = demoTemplate(header, file_type);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		return new ResponseEntity<String>(\"Not Found\", HttpStatus.BAD_REQUEST);" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream demoTemplate(String[] HEADERs, String file_type) throws IOException {" + "\r\n" + 
"		String SHEET = file_type;" + "\r\n" + 
"		Workbook workbook = new XSSFWorkbook();" + "\r\n" + 
"		ByteArrayOutputStream out = new ByteArrayOutputStream();" + "\r\n" + 
"		Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"		Row headerRow = sheet.createRow(0);" + "\r\n" + 
"		for (int col = 0; col < HEADERs.length; col++) {" + "\r\n" + 
"			Cell cell = headerRow.createCell(col);" + "\r\n" + 
"			cell.setCellValue(HEADERs[col]);" + "\r\n" + 
"		}" + "\r\n" + 
"		workbook.write(out);" + "\r\n" + 
"		return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	@PostMapping(\"/save/{filetype}\")" + "\r\n" + 
"	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @PathVariable String filetype)" + "\r\n" + 
"			throws IOException, ParseException {" + "\r\n" + 
"		BufferedReader br;" + "\r\n" + 
"		InputStream is = file.getInputStream();" + "\r\n" + 
"		br = new BufferedReader(new InputStreamReader(is));" + "\r\n" + 
"		String file_name = file.getOriginalFilename();" + "\r\n" + 
"		AppUser loggedInUser = userService.getLoggedInUser();" + "\r\n" + 
"" + "\r\n" + 
"		if (filetype.equalsIgnoreCase(\"priority\")) {" + "\r\n" + 
"" + "\r\n" + 
"			if (file_name.contains(\"Sr_priority2_t\")) {" + "\r\n" + 
"" + "\r\n" + 
"				String[] header = { \"Priority Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"						\"Effective End date\" };" + "\r\n" + 
"				ArrayList<Sr_priority2_t> prioritylist = new ArrayList<Sr_priority2_t>();" + "\r\n" + 
"" + "\r\n" + 
"				Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"" + "\r\n" + 
"// 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"				DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"" + "\r\n" + 
"				Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"" + "\r\n" + 
"				// Getting number of columns in the Sheet" + "\r\n" + 
"				int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"" + "\r\n" + 
"				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);" + "\r\n" + 
"" + "\r\n" + 
"				for (Row row : sheet) {" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() == 0) {" + "\r\n" + 
"						for (int i = 0; i < cols; i++) {" + "\r\n" + 
"							String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"" + "\r\n" + 
"							if (!header[i].equalsIgnoreCase(value)) {" + "\r\n" + 
"" + "\r\n" + 
"								return new ResponseEntity<>(" + "\r\n" + 
"										\"priority file Should  have \\\"\" + header + \" \\\"in the header in excel file\"," + "\r\n" + 
"										HttpStatus.BAD_REQUEST);" + "\r\n" + 
"							}" + "\r\n" + 
"						}" + "\r\n" + 
"" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() != 0) {" + "\r\n" + 
"						Date date = row.getCell(3).getDateCellValue();" + "\r\n" + 
"						Date date2 = row.getCell(4).getDateCellValue();" + "\r\n" + 
"" + "\r\n" + 
"						Sr_priority2_t priority = new Sr_priority2_t();" + "\r\n" + 
"						priority.setPriority_name(dataFormatter.formatCellValue(row.getCell(0)));" + "\r\n" + 
"						priority.setDescription(dataFormatter.formatCellValue(row.getCell(1)));" + "\r\n" + 
"						priority.setActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));" + "\r\n" + 
"" + "\r\n" + 
"						priority.setEffective_start_date(date);" + "\r\n" + 
"						priority.setEffective_end_date(date2);" + "\r\n" + 
"" + "\r\n" + 
"						prioritylist.add(priority);" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"				}" + "\r\n" + 
"				priorityrepo.saveAll(prioritylist);" + "\r\n" + 
"				workbook.close();" + "\r\n" + 
"" + "\r\n" + 
"//			" + "\r\n" + 
"" + "\r\n" + 
"				return new ResponseEntity<>(\"File Uploaded\", HttpStatus.ACCEPTED);" + "\r\n" + 
"			}" + "\r\n" + 
"			return new ResponseEntity<>(\"File name should contain Sr_priority2_t\", HttpStatus.BAD_REQUEST);" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		else if (filetype.contains(\"impact\")) {" + "\r\n" + 
"" + "\r\n" + 
"			if (file_name.contains(\"Sr_impact2_t\")) {" + "\r\n" + 
"" + "\r\n" + 
"				String[] header = { \"Impact Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"						\"Effective End date\" };" + "\r\n" + 
"" + "\r\n" + 
"				ArrayList<Sr_impact2_t> impactlist = new ArrayList<Sr_impact2_t>();" + "\r\n" + 
"" + "\r\n" + 
"				Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"" + "\r\n" + 
"// 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"				DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"" + "\r\n" + 
"				Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"" + "\r\n" + 
"				// Getting number of columns in the Sheet" + "\r\n" + 
"				int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"" + "\r\n" + 
"				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);" + "\r\n" + 
"" + "\r\n" + 
"				for (Row row : sheet) {" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() == 0) {" + "\r\n" + 
"						for (int i = 0; i < cols; i++) {" + "\r\n" + 
"							String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"" + "\r\n" + 
"							if (!header[i].equalsIgnoreCase(value)) {" + "\r\n" + 
"" + "\r\n" + 
"								return new ResponseEntity<>(\"priority file header is not in correct format \"," + "\r\n" + 
"										HttpStatus.BAD_REQUEST);" + "\r\n" + 
"							}" + "\r\n" + 
"						}" + "\r\n" + 
"" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() != 0) {" + "\r\n" + 
"" + "\r\n" + 
"						Date date = row.getCell(3).getDateCellValue();" + "\r\n" + 
"						Date date2 = row.getCell(4).getDateCellValue();" + "\r\n" + 
"" + "\r\n" + 
"						Sr_impact2_t impact = new Sr_impact2_t();" + "\r\n" + 
"						impact.setImpact_name(dataFormatter.formatCellValue(row.getCell(0)));" + "\r\n" + 
"						impact.setDescription(dataFormatter.formatCellValue(row.getCell(1)));" + "\r\n" + 
"						impact.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));" + "\r\n" + 
"" + "\r\n" + 
"						impact.setEffective_start_date(date);" + "\r\n" + 
"						impact.setEffective_end_date(date2);" + "\r\n" + 
"" + "\r\n" + 
"						impactlist.add(impact);" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"				}" + "\r\n" + 
"				impactrepo.saveAll(impactlist);" + "\r\n" + 
"				System.out.println(impactlist);" + "\r\n" + 
"" + "\r\n" + 
"				workbook.close();" + "\r\n" + 
"" + "\r\n" + 
"				return new ResponseEntity<>(\"File Uploaded\", HttpStatus.ACCEPTED);" + "\r\n" + 
"			}" + "\r\n" + 
"			return new ResponseEntity<>(\"File name should contain Sr_impact2_t\", HttpStatus.BAD_REQUEST);" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		else if (filetype.contains(\"urgency\")) {" + "\r\n" + 
"" + "\r\n" + 
"			if (file_name.contains(\"Sr_urgency_t\")) {" + "\r\n" + 
"" + "\r\n" + 
"				String[] header = { \"Urgency Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"						\"Effective End date\" };" + "\r\n" + 
"				List<String> li = new ArrayList<String>();" + "\r\n" + 
"				li.add(\"Impact Name\");" + "\r\n" + 
"				li.add(\"Description\");" + "\r\n" + 
"				li.add(\"Is Active\");" + "\r\n" + 
"				li.add(\"Effective Start Date\");" + "\r\n" + 
"				li.add(\"Effective End date\");" + "\r\n" + 
"" + "\r\n" + 
"				ArrayList<Sr_urgency_t> impactlist = new ArrayList<Sr_urgency_t>();" + "\r\n" + 
"" + "\r\n" + 
"				Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"" + "\r\n" + 
"// 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"				DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"" + "\r\n" + 
"				Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"" + "\r\n" + 
"				// Getting number of columns in the Sheet" + "\r\n" + 
"				int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"" + "\r\n" + 
"				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);" + "\r\n" + 
"" + "\r\n" + 
"				for (Row row : sheet) {" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() == 0) {" + "\r\n" + 
"						for (int i = 0; i < cols; i++) {" + "\r\n" + 
"							String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"" + "\r\n" + 
"							if (!header[i].equalsIgnoreCase(value)) {" + "\r\n" + 
"" + "\r\n" + 
"								return new ResponseEntity<>(" + "\r\n" + 
"										\"priority file Should  have \\\"\" + li + \" \\\"in the header in excel file\"," + "\r\n" + 
"										HttpStatus.BAD_REQUEST);" + "\r\n" + 
"							}" + "\r\n" + 
"						}" + "\r\n" + 
"" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() != 0) {" + "\r\n" + 
"						Date date = row.getCell(3).getDateCellValue();" + "\r\n" + 
"						Date date2 = row.getCell(4).getDateCellValue();" + "\r\n" + 
"" + "\r\n" + 
"						Sr_urgency_t urgency = new Sr_urgency_t();" + "\r\n" + 
"						urgency.setUrgency_name(dataFormatter.formatCellValue(row.getCell(0)));" + "\r\n" + 
"						urgency.setDescription(dataFormatter.formatCellValue(row.getCell(1)));" + "\r\n" + 
"						urgency.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));" + "\r\n" + 
"" + "\r\n" + 
"						urgency.setEffective_start_date(date);" + "\r\n" + 
"						urgency.setEffective_end_date(date2);" + "\r\n" + 
"" + "\r\n" + 
"						impactlist.add(urgency);" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"				}" + "\r\n" + 
"				urgencyrepo.saveAll(impactlist);" + "\r\n" + 
"				System.out.println(impactlist);" + "\r\n" + 
"" + "\r\n" + 
"				workbook.close();" + "\r\n" + 
"" + "\r\n" + 
"				return new ResponseEntity<>(\"File Uploaded\", HttpStatus.ACCEPTED);" + "\r\n" + 
"			}" + "\r\n" + 
"			return new ResponseEntity<>(\"File name should contain sr_incident_t\", HttpStatus.BAD_REQUEST);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (filetype.equalsIgnoreCase(\"category\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String[] header = { \"Category Name\", \"Customer Id\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"" + "\r\n" + 
"//			if (file_name.contains(\"Sr_impact2_t\")) {" + "\r\n" + 
"//" + "\r\n" + 
"//				ArrayList<Sr_impact2_t> impactlist = new ArrayList<Sr_impact2_t>();" + "\r\n" + 
"//" + "\r\n" + 
"//				Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"//" + "\r\n" + 
"////	 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"//				DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"//" + "\r\n" + 
"//				Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"//" + "\r\n" + 
"//				// Getting number of columns in the Sheet" + "\r\n" + 
"//				int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"//" + "\r\n" + 
"//				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);" + "\r\n" + 
"//" + "\r\n" + 
"//				for (Row row : sheet) {" + "\r\n" + 
"//" + "\r\n" + 
"//					if (row.getRowNum() == 0) {" + "\r\n" + 
"//						for (int i = 0; i < cols; i++) {" + "\r\n" + 
"//							String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"//" + "\r\n" + 
"//							if (!header[i].equalsIgnoreCase(value)) {" + "\r\n" + 
"//" + "\r\n" + 
"//								return new ResponseEntity<>(\"priority file header is not in correct format \"," + "\r\n" + 
"//										HttpStatus.BAD_REQUEST);" + "\r\n" + 
"//							}" + "\r\n" + 
"//						}" + "\r\n" + 
"//" + "\r\n" + 
"//					}" + "\r\n" + 
"//" + "\r\n" + 
"//					if (row.getRowNum() != 0) {" + "\r\n" + 
"//						String value = dataFormatter.formatCellValue(row.getCell(4));" + "\r\n" + 
"//						String value2 = dataFormatter.formatCellValue(row.getCell(5));" + "\r\n" + 
"//			Date date = new SimpleDateFormat(\"dd-MM-yyyy\").parse(value);" + "\r\n" + 
"//			Date date2 = new SimpleDateFormat(\"dd-MM-yyyy\").parse(value2);" + "\r\n" + 
"//" + "\r\n" + 
"//						Sr_impact2_t impact = new Sr_impact2_t();" + "\r\n" + 
"//						impact.setImpact_name(file_name);" + "\r\n" + 
"//						impact.setDescription(dataFormatter.formatCellValue(row.getCell(2)));" + "\r\n" + 
"//						impact.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(3))));" + "\r\n" + 
"//" + "\r\n" + 
"////					priority.setEffective_start_date(date);" + "\r\n" + 
"////					priority.setEffective_end_date(date2);" + "\r\n" + 
"//" + "\r\n" + 
"//						impactlist.add(impact);" + "\r\n" + 
"//					}" + "\r\n" + 
"//" + "\r\n" + 
"//				}" + "\r\n" + 
"//				impactrepo.saveAll(impactlist);" + "\r\n" + 
"//				System.out.println(impactlist);" + "\r\n" + 
"//" + "\r\n" + 
"//				workbook.close();" + "\r\n" + 
"//" + "\r\n" + 
"//			}" + "\r\n" + 
"" + "\r\n" + 
"		} else if (filetype.equalsIgnoreCase(\"state\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String[] header = { \"State Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"" + "\r\n" + 
"			if (file_name.contains(\"Sr_State_t\")) {" + "\r\n" + 
"" + "\r\n" + 
"				ArrayList<Sr_State_t> statelist = new ArrayList<Sr_State_t>();" + "\r\n" + 
"" + "\r\n" + 
"				Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"" + "\r\n" + 
"//	 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"				DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"" + "\r\n" + 
"				Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"" + "\r\n" + 
"				// Getting number of columns in the Sheet" + "\r\n" + 
"				int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"" + "\r\n" + 
"				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);" + "\r\n" + 
"" + "\r\n" + 
"				for (Row row : sheet) {" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() == 0) {" + "\r\n" + 
"						for (int i = 0; i < cols; i++) {" + "\r\n" + 
"							String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"" + "\r\n" + 
"							if (!header[i].equalsIgnoreCase(value)) {" + "\r\n" + 
"" + "\r\n" + 
"								return new ResponseEntity<>(\"priority file header is not in correct format \"," + "\r\n" + 
"										HttpStatus.BAD_REQUEST);" + "\r\n" + 
"							}" + "\r\n" + 
"						}" + "\r\n" + 
"" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() != 0) {" + "\r\n" + 
"						String value = dataFormatter.formatCellValue(row.getCell(3));" + "\r\n" + 
"						String value2 = dataFormatter.formatCellValue(row.getCell(4));" + "\r\n" + 
"" + "\r\n" + 
"						Sr_State_t state = new Sr_State_t();" + "\r\n" + 
"						state.setState_name(dataFormatter.formatCellValue(row.getCell(0)));" + "\r\n" + 
"						state.setDescription(dataFormatter.formatCellValue(row.getCell(1)));" + "\r\n" + 
"						state.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));" + "\r\n" + 
"" + "\r\n" + 
"						state.setEffective_end_date(value2);" + "\r\n" + 
"						state.setEffective_start_date(value);" + "\r\n" + 
"" + "\r\n" + 
"						statelist.add(state);" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"				}" + "\r\n" + 
"				staterepo.saveAll(statelist);" + "\r\n" + 
"" + "\r\n" + 
"				workbook.close();" + "\r\n" + 
"" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"		} else if (filetype.equalsIgnoreCase(\"contact_type\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String[] header = { \"Contact Type Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"" + "\r\n" + 
"			if (file_name.contains(\"Sr_Contact_type_t\")) {" + "\r\n" + 
"" + "\r\n" + 
"				ArrayList<Sr_Contact_type_t> statelist = new ArrayList<Sr_Contact_type_t>();" + "\r\n" + 
"" + "\r\n" + 
"				Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"" + "\r\n" + 
"//	 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"				DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"" + "\r\n" + 
"				Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"" + "\r\n" + 
"				// Getting number of columns in the Sheet" + "\r\n" + 
"				int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"" + "\r\n" + 
"				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);" + "\r\n" + 
"" + "\r\n" + 
"				for (Row row : sheet) {" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() == 0) {" + "\r\n" + 
"						for (int i = 0; i < cols; i++) {" + "\r\n" + 
"							String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"" + "\r\n" + 
"							if (!header[i].equalsIgnoreCase(value)) {" + "\r\n" + 
"" + "\r\n" + 
"								return new ResponseEntity<>(\"priority file header is not in correct format \"," + "\r\n" + 
"										HttpStatus.BAD_REQUEST);" + "\r\n" + 
"							}" + "\r\n" + 
"						}" + "\r\n" + 
"" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() != 0) {" + "\r\n" + 
"						Date date = row.getCell(3).getDateCellValue();" + "\r\n" + 
"						Date date2 = row.getCell(4).getDateCellValue();" + "\r\n" + 
"" + "\r\n" + 
"						Sr_Contact_type_t contact = new Sr_Contact_type_t();" + "\r\n" + 
"						contact.setCon_type_name(dataFormatter.formatCellValue(row.getCell(0)));" + "\r\n" + 
"						contact.setDescription(dataFormatter.formatCellValue(row.getCell(1)));" + "\r\n" + 
"						contact.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));" + "\r\n" + 
"" + "\r\n" + 
"						contact.setEffective_start_date(date);" + "\r\n" + 
"						contact.setEffective_end_date(date2);" + "\r\n" + 
"" + "\r\n" + 
"						statelist.add(contact);" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"				}" + "\r\n" + 
"				contactrepo.saveAll(statelist);" + "\r\n" + 
"" + "\r\n" + 
"				workbook.close();" + "\r\n" + 
"" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"		} else if (filetype.equalsIgnoreCase(\"customer\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String[] header = { \"Customer Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"" + "\r\n" + 
"			if (file_name.contains(\"Sr_customer_t\")) {" + "\r\n" + 
"" + "\r\n" + 
"				ArrayList<Sr_customer_t> customerlist = new ArrayList<Sr_customer_t>();" + "\r\n" + 
"" + "\r\n" + 
"				Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"" + "\r\n" + 
"//	 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"				DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"" + "\r\n" + 
"				Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"" + "\r\n" + 
"				// Getting number of columns in the Sheet" + "\r\n" + 
"				int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"" + "\r\n" + 
"				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);" + "\r\n" + 
"" + "\r\n" + 
"				for (Row row : sheet) {" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() == 0) {" + "\r\n" + 
"						for (int i = 0; i < cols; i++) {" + "\r\n" + 
"							String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"" + "\r\n" + 
"							if (!header[i].equalsIgnoreCase(value)) {" + "\r\n" + 
"" + "\r\n" + 
"								return new ResponseEntity<>(\"priority file header is not in correct format \"," + "\r\n" + 
"										HttpStatus.BAD_REQUEST);" + "\r\n" + 
"							}" + "\r\n" + 
"						}" + "\r\n" + 
"" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"					if (row.getRowNum() != 0) {" + "\r\n" + 
"						Date date = row.getCell(3).getDateCellValue();" + "\r\n" + 
"						Date date2 = row.getCell(4).getDateCellValue();" + "\r\n" + 
"" + "\r\n" + 
"						Sr_customer_t customer = new Sr_customer_t();" + "\r\n" + 
"						customer.setCustomer_name(dataFormatter.formatCellValue(row.getCell(0)));" + "\r\n" + 
"						customer.setDescription(dataFormatter.formatCellValue(row.getCell(1)));" + "\r\n" + 
"						customer.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));" + "\r\n" + 
"" + "\r\n" + 
"						customer.setEffective_start_date(date);" + "\r\n" + 
"						customer.setEffective_end_date(date2);" + "\r\n" + 
"" + "\r\n" + 
"						customerlist.add(customer);" + "\r\n" + 
"					}" + "\r\n" + 
"" + "\r\n" + 
"				}" + "\r\n" + 
"				customerepo.saveAll(customerlist);" + "\r\n" + 
"" + "\r\n" + 
"				workbook.close();" + "\r\n" + 
"" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"		} else if (filetype.equalsIgnoreCase(\"handler\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String[] header = { \"User Id\", \"Role Id\", \"Is Active\", \"Effective Start Date\", \"Effective End date\" };" + "\r\n" + 
"" + "\r\n" + 
"//			if (file_name.contains(\"Sr_State_t\")) {" + "\r\n" + 
"//" + "\r\n" + 
"//				ArrayList<Sr_State_t> statelist = new ArrayList<Sr_State_t>();" + "\r\n" + 
"//" + "\r\n" + 
"//				Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"//" + "\r\n" + 
"////	 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"//				DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"//" + "\r\n" + 
"//				Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"//" + "\r\n" + 
"//				// Getting number of columns in the Sheet" + "\r\n" + 
"//				int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"//" + "\r\n" + 
"//				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);" + "\r\n" + 
"//" + "\r\n" + 
"//				for (Row row : sheet) {" + "\r\n" + 
"//" + "\r\n" + 
"//					if (row.getRowNum() == 0) {" + "\r\n" + 
"//						for (int i = 0; i < cols; i++) {" + "\r\n" + 
"//							String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"//" + "\r\n" + 
"//							if (!header[i].equalsIgnoreCase(value)) {" + "\r\n" + 
"//" + "\r\n" + 
"//								return new ResponseEntity<>(\"priority file header is not in correct format \"," + "\r\n" + 
"//										HttpStatus.BAD_REQUEST);" + "\r\n" + 
"//							}" + "\r\n" + 
"//						}" + "\r\n" + 
"//" + "\r\n" + 
"//					}" + "\r\n" + 
"//" + "\r\n" + 
"//					if (row.getRowNum() != 0) {" + "\r\n" + 
"//			Date date = row.getCell(3).getDateCellValue();" + "\r\n" + 
"//			Date date2 = row.getCell(4).getDateCellValue();" + "\r\n" + 
"//" + "\r\n" + 
"//						Sr_State_t state = new Sr_State_t();" + "\r\n" + 
"//						state.setState_name(dataFormatter.formatCellValue(row.getCell(1)));" + "\r\n" + 
"//						state.setDescription(dataFormatter.formatCellValue(row.getCell(2)));" + "\r\n" + 
"//						state.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(3))));" + "\r\n" + 
"//" + "\r\n" + 
"////					priority.setEffective_start_date(date);" + "\r\n" + 
"////					priority.setEffective_end_date(date2);" + "\r\n" + 
"//" + "\r\n" + 
"//						statelist.add(state);" + "\r\n" + 
"//					}" + "\r\n" + 
"//" + "\r\n" + 
"//				}" + "\r\n" + 
"//				staterepo.saveAll(statelist);" + "\r\n" + 
"//" + "\r\n" + 
"//				workbook.close();" + "\r\n" + 
"//" + "\r\n" + 
"//			}" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		return new ResponseEntity<>(\"Something Went Wrong please try again....!!!!! \", HttpStatus.BAD_REQUEST);" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"//	Download template data in excel Files" + "\r\n" + 
"" + "\r\n" + 
"	@GetMapping(\"/download/{file_type}\")" + "\r\n" + 
"" + "\r\n" + 
"	public ResponseEntity<?> getFile(@PathVariable String file_type) throws IOException {" + "\r\n" + 
"" + "\r\n" + 
"		if (file_type.equalsIgnoreCase(\"priority\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_priority2_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Priority Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			List<Sr_priority2_t> findAll = priorityrepo.findAll();" + "\r\n" + 
"			ByteArrayInputStream in = templateByte(findAll, header);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"impact\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_impact2_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Impact Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			List<Sr_impact2_t> findAll = impactrepo.findAll();" + "\r\n" + 
"			ByteArrayInputStream in = templateByte3(findAll, header);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"urgency\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_urgency_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Urgency Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			List<Sr_urgency_t> findAll = urgencyrepo.findAll();" + "\r\n" + 
"			ByteArrayInputStream in = templateByte2(findAll, header);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"category\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_category2_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Category Name\", \"Customer Id\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			List<Sr_category2_t> findAll = categoryrepo.findAll();" + "\r\n" + 
"			ByteArrayInputStream in = templateByte4(findAll, header);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"state\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_State_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"State Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			List<Sr_State_t> findAll = staterepo.findAll();" + "\r\n" + 
"			ByteArrayInputStream in = templateByte6(findAll, header);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"contact_type\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_Contact_type_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Contact Type Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			List<Sr_Contact_type_t> findAll = contactrepo.findAll();" + "\r\n" + 
"			ByteArrayInputStream in = templateByte5(findAll, header);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"customer\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_customer_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"Customer Name\", \"Description\", \"Is Active\", \"Effective Start Date\"," + "\r\n" + 
"					\"Effective End date\" };" + "\r\n" + 
"			List<Sr_customer_t> findAll = customerepo.findAll();" + "\r\n" + 
"			ByteArrayInputStream in = templateByte7(findAll, header);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		} else if (file_type.equalsIgnoreCase(\"handler\")) {" + "\r\n" + 
"" + "\r\n" + 
"			String filename = \"Sr_handler_t\" + \".xlsx\";" + "\r\n" + 
"			String[] header = { \"User Id\", \"Role Id\", \"Is Active\", \"Effective Start Date\", \"Effective End date\" };" + "\r\n" + 
"			List<Sr_handler_t> findAll = handlerepo.findAll();" + "\r\n" + 
"			ByteArrayInputStream in = templateByte8(findAll, header);" + "\r\n" + 
"			InputStreamResource file = new InputStreamResource(in);" + "\r\n" + 
"			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + filename)" + "\r\n" + 
"					.contentType(MediaType.parseMediaType(\"application/vnd.ms-excel\")).body(file);" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		return new ResponseEntity<String>(\"Not Found\", HttpStatus.BAD_REQUEST);" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream templateByte(List<Sr_priority2_t> list, String[] hEADERs) {" + "\r\n" + 
"" + "\r\n" + 
"		String SHEET = \"priority\";" + "\r\n" + 
"" + "\r\n" + 
"		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {" + "\r\n" + 
"			Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"" + "\r\n" + 
"			// Header" + "\r\n" + 
"			Row headerRow = sheet.createRow(0);" + "\r\n" + 
"" + "\r\n" + 
"			for (int col = 0; col < hEADERs.length; col++) {" + "\r\n" + 
"				Cell cell = headerRow.createCell(col);" + "\r\n" + 
"				cell.setCellValue(hEADERs[col]);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			int rowIdx = 1;" + "\r\n" + 
"			for (Sr_priority2_t p : list) {" + "\r\n" + 
"				Row row = sheet.createRow(rowIdx++);" + "\r\n" + 
"" + "\r\n" + 
"				row.createCell(0).setCellValue(p.getPriority_name());" + "\r\n" + 
"				row.createCell(1).setCellValue(p.getDescription());" + "\r\n" + 
"				row.createCell(2).setCellValue(p.isActive());" + "\r\n" + 
"				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));" + "\r\n" + 
"				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));" + "\r\n" + 
"			}" + "\r\n" + 
"			workbook.write(out);" + "\r\n" + 
"			return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"		} catch (IOException e) {" + "\r\n" + 
"			throw new RuntimeException(\"fail to import data to Excel file: \" + e.getMessage());" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream templateByte2(List<Sr_urgency_t> list, String[] hEADERs) {" + "\r\n" + 
"" + "\r\n" + 
"		String SHEET = \"URGENCY\";" + "\r\n" + 
"" + "\r\n" + 
"		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {" + "\r\n" + 
"			Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"" + "\r\n" + 
"			// Header" + "\r\n" + 
"			Row headerRow = sheet.createRow(0);" + "\r\n" + 
"" + "\r\n" + 
"			for (int col = 0; col < hEADERs.length; col++) {" + "\r\n" + 
"				Cell cell = headerRow.createCell(col);" + "\r\n" + 
"				cell.setCellValue(hEADERs[col]);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			int rowIdx = 1;" + "\r\n" + 
"			for (Sr_urgency_t p : list) {" + "\r\n" + 
"				Row row = sheet.createRow(rowIdx++);" + "\r\n" + 
"" + "\r\n" + 
"				row.createCell(0).setCellValue(p.getUrgency_name());" + "\r\n" + 
"				row.createCell(1).setCellValue(p.getDescription());" + "\r\n" + 
"				row.createCell(2).setCellValue(p.isIsActive());" + "\r\n" + 
"				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));" + "\r\n" + 
"				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));" + "\r\n" + 
"			}" + "\r\n" + 
"			workbook.write(out);" + "\r\n" + 
"			return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"		} catch (IOException e) {" + "\r\n" + 
"			throw new RuntimeException(\"fail to import data to Excel file: \" + e.getMessage());" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream templateByte3(List<Sr_impact2_t> list, String[] hEADERs) {" + "\r\n" + 
"" + "\r\n" + 
"		String SHEET = \"Impact\";" + "\r\n" + 
"" + "\r\n" + 
"		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {" + "\r\n" + 
"			Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"" + "\r\n" + 
"			// Header" + "\r\n" + 
"			Row headerRow = sheet.createRow(0);" + "\r\n" + 
"" + "\r\n" + 
"			for (int col = 0; col < hEADERs.length; col++) {" + "\r\n" + 
"				Cell cell = headerRow.createCell(col);" + "\r\n" + 
"				cell.setCellValue(hEADERs[col]);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			int rowIdx = 1;" + "\r\n" + 
"			for (Sr_impact2_t p : list) {" + "\r\n" + 
"				Row row = sheet.createRow(rowIdx++);" + "\r\n" + 
"" + "\r\n" + 
"				row.createCell(0).setCellValue(p.getImpact_name());" + "\r\n" + 
"				row.createCell(1).setCellValue(p.getDescription());" + "\r\n" + 
"				row.createCell(2).setCellValue(p.isIsActive());" + "\r\n" + 
"				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));" + "\r\n" + 
"				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));" + "\r\n" + 
"			}" + "\r\n" + 
"			workbook.write(out);" + "\r\n" + 
"			return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"		} catch (IOException e) {" + "\r\n" + 
"			throw new RuntimeException(\"fail to import data to Excel file: \" + e.getMessage());" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream templateByte4(List<Sr_category2_t> list, String[] hEADERs) {" + "\r\n" + 
"" + "\r\n" + 
"		String SHEET = \"Impact\";" + "\r\n" + 
"" + "\r\n" + 
"		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {" + "\r\n" + 
"			Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"" + "\r\n" + 
"			// Header" + "\r\n" + 
"			Row headerRow = sheet.createRow(0);" + "\r\n" + 
"" + "\r\n" + 
"			for (int col = 0; col < hEADERs.length; col++) {" + "\r\n" + 
"				Cell cell = headerRow.createCell(col);" + "\r\n" + 
"				cell.setCellValue(hEADERs[col]);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			int rowIdx = 1;" + "\r\n" + 
"			for (Sr_category2_t p : list) {" + "\r\n" + 
"				Row row = sheet.createRow(rowIdx++);" + "\r\n" + 
"" + "\r\n" + 
"				row.createCell(0).setCellValue(p.getCustomer_id());" + "\r\n" + 
"				row.createCell(1).setCellValue(p.getDescription());" + "\r\n" + 
"				row.createCell(2).setCellValue(p.isActive());" + "\r\n" + 
"				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));" + "\r\n" + 
"				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));" + "\r\n" + 
"			}" + "\r\n" + 
"			workbook.write(out);" + "\r\n" + 
"			return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"		} catch (IOException e) {" + "\r\n" + 
"			throw new RuntimeException(\"fail to import data to Excel file: \" + e.getMessage());" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream templateByte5(List<Sr_Contact_type_t> list, String[] hEADERs) {" + "\r\n" + 
"" + "\r\n" + 
"		String SHEET = \"Impact\";" + "\r\n" + 
"" + "\r\n" + 
"		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {" + "\r\n" + 
"			Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"" + "\r\n" + 
"			// Header" + "\r\n" + 
"			Row headerRow = sheet.createRow(0);" + "\r\n" + 
"" + "\r\n" + 
"			for (int col = 0; col < hEADERs.length; col++) {" + "\r\n" + 
"				Cell cell = headerRow.createCell(col);" + "\r\n" + 
"				cell.setCellValue(hEADERs[col]);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			int rowIdx = 1;" + "\r\n" + 
"			for (Sr_Contact_type_t p : list) {" + "\r\n" + 
"				Row row = sheet.createRow(rowIdx++);" + "\r\n" + 
"" + "\r\n" + 
"				row.createCell(0).setCellValue(p.getCon_type_name());" + "\r\n" + 
"				row.createCell(1).setCellValue(p.getDescription());" + "\r\n" + 
"				row.createCell(2).setCellValue(p.isIsActive());" + "\r\n" + 
"				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));" + "\r\n" + 
"				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));" + "\r\n" + 
"			}" + "\r\n" + 
"			workbook.write(out);" + "\r\n" + 
"			return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"		} catch (IOException e) {" + "\r\n" + 
"			throw new RuntimeException(\"fail to import data to Excel file: \" + e.getMessage());" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream templateByte6(List<Sr_State_t> list, String[] hEADERs) {" + "\r\n" + 
"" + "\r\n" + 
"		String SHEET = \"Impact\";" + "\r\n" + 
"" + "\r\n" + 
"		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {" + "\r\n" + 
"			Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"" + "\r\n" + 
"			// Header" + "\r\n" + 
"			Row headerRow = sheet.createRow(0);" + "\r\n" + 
"" + "\r\n" + 
"			for (int col = 0; col < hEADERs.length; col++) {" + "\r\n" + 
"				Cell cell = headerRow.createCell(col);" + "\r\n" + 
"				cell.setCellValue(hEADERs[col]);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			int rowIdx = 1;" + "\r\n" + 
"			for (Sr_State_t p : list) {" + "\r\n" + 
"				Row row = sheet.createRow(rowIdx++);" + "\r\n" + 
"				row.createCell(0).setCellValue(p.getState_name());" + "\r\n" + 
"				row.createCell(1).setCellValue(p.getDescription());" + "\r\n" + 
"				row.createCell(2).setCellValue(p.isIsActive());" + "\r\n" + 
"				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));" + "\r\n" + 
"				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));" + "\r\n" + 
"			}" + "\r\n" + 
"			workbook.write(out);" + "\r\n" + 
"			return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"		} catch (IOException e) {" + "\r\n" + 
"			throw new RuntimeException(\"fail to import data to Excel file: \" + e.getMessage());" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream templateByte7(List<Sr_customer_t> list, String[] hEADERs) {" + "\r\n" + 
"" + "\r\n" + 
"		String SHEET = \"Impact\";" + "\r\n" + 
"" + "\r\n" + 
"		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {" + "\r\n" + 
"			Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"" + "\r\n" + 
"			// Header" + "\r\n" + 
"			Row headerRow = sheet.createRow(0);" + "\r\n" + 
"" + "\r\n" + 
"			for (int col = 0; col < hEADERs.length; col++) {" + "\r\n" + 
"				Cell cell = headerRow.createCell(col);" + "\r\n" + 
"				cell.setCellValue(hEADERs[col]);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			int rowIdx = 1;" + "\r\n" + 
"			for (Sr_customer_t p : list) {" + "\r\n" + 
"				Row row = sheet.createRow(rowIdx++);" + "\r\n" + 
"" + "\r\n" + 
"				row.createCell(0).setCellValue(p.getCustomer_name());" + "\r\n" + 
"				row.createCell(1).setCellValue(p.getDescription());" + "\r\n" + 
"				row.createCell(2).setCellValue(p.isIsActive());" + "\r\n" + 
"				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));" + "\r\n" + 
"				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));" + "\r\n" + 
"			}" + "\r\n" + 
"			workbook.write(out);" + "\r\n" + 
"			return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"		} catch (IOException e) {" + "\r\n" + 
"			throw new RuntimeException(\"fail to import data to Excel file: \" + e.getMessage());" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	public static ByteArrayInputStream templateByte8(List<Sr_handler_t> list, String[] hEADERs) {" + "\r\n" + 
"" + "\r\n" + 
"		String SHEET = \"Impact\";" + "\r\n" + 
"" + "\r\n" + 
"		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {" + "\r\n" + 
"			Sheet sheet = workbook.createSheet(SHEET);" + "\r\n" + 
"" + "\r\n" + 
"			// Header" + "\r\n" + 
"			Row headerRow = sheet.createRow(0);" + "\r\n" + 
"" + "\r\n" + 
"			for (int col = 0; col < hEADERs.length; col++) {" + "\r\n" + 
"				Cell cell = headerRow.createCell(col);" + "\r\n" + 
"				cell.setCellValue(hEADERs[col]);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			int rowIdx = 1;" + "\r\n" + 
"			for (Sr_handler_t p : list) {" + "\r\n" + 
"				Row row = sheet.createRow(rowIdx++);" + "\r\n" + 
"" + "\r\n" + 
"				row.createCell(0).setCellValue(p.getRole());" + "\r\n" + 
"				row.createCell(1).setCellValue(p.getUser_name());" + "\r\n" + 
"				row.createCell(2).setCellValue(p.isIsActive());" + "\r\n" + 
"				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));" + "\r\n" + 
"				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));" + "\r\n" + 
"			}" + "\r\n" + 
"			workbook.write(out);" + "\r\n" + 
"			return new ByteArrayInputStream(out.toByteArray());" + "\r\n" + 
"		} catch (IOException e) {" + "\r\n" + 
"			throw new RuntimeException(\"fail to import data to Excel file: \" + e.getMessage());" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	@GetMapping(\"/getalltemplate\")" + "\r\n" + 
"	public ResponseEntity<?> getALlTemplate() {" + "\r\n" + 
"		return new ResponseEntity<>(temprepo.findAll(), HttpStatus.ACCEPTED);" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"}" 