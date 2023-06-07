//package com.realnet.excel.controller;
package com.realnet.template.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realnet.Sr_Contact_type.Entity.Sr_Contact_type_t;
import com.realnet.Sr_Contact_type.Repository.Sr_Contact_type_Repository;
import com.realnet.Sr_State.Entity.Sr_State_t;
import com.realnet.Sr_State.Repository.Sr_State_Repository;
import com.realnet.Sr_category2.Entity.Sr_category2_t;
import com.realnet.Sr_category2.Repository.Sr_category2_Repository;
import com.realnet.Sr_customer.Entity.Sr_customer_t;
import com.realnet.Sr_customer.Repository.Sr_customer_Repository;
import com.realnet.Sr_handler.Entity.Sr_handler_t;
import com.realnet.Sr_handler.Repository.Sr_handler_Repository;
import com.realnet.Sr_impact2.Entity.Sr_impact2_t;
import com.realnet.Sr_impact2.Repository.Sr_impact2_Repository;
import com.realnet.Sr_priority2.Entity.Sr_priority2_t;
import com.realnet.Sr_priority2.Repository.Sr_priority2_Repository;
import com.realnet.Sr_urgency.Entity.Sr_urgency_t;
import com.realnet.Sr_urgency.Repository.Sr_urgency_Repository;
import com.realnet.template.repository.TemplatedataRepo;
import com.realnet.template.service.FileUploadService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@RestController
@RequestMapping("api/template")
public class Controller {
	@Autowired
	private TemplatedataRepo temprepo;

	@Autowired
	private Sr_priority2_Repository priorityrepo;

	@Autowired
	private Sr_impact2_Repository impactrepo;

	@Autowired
	private Sr_urgency_Repository urgencyrepo;

	@Autowired
	private Sr_State_Repository staterepo;

	@Autowired
	private Sr_Contact_type_Repository contactrepo;

	@Autowired
	private Sr_customer_Repository customerepo;

	@Autowired
	private Sr_category2_Repository categoryrepo;

	@Autowired
	private Sr_handler_Repository handlerepo;
	@Autowired
	FileUploadService fileupload;
	@Autowired
	private AppUserServiceImpl userService;

	@GetMapping("/demo/download/{file_type}")

	public ResponseEntity<?> demoTemplate(@PathVariable String file_type) throws IOException {

		if (file_type.equalsIgnoreCase("priority")) {

			String filename = "Sr_priority2_t" + ".xlsx";
			String[] header = { "Priority Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			ByteArrayInputStream in = demoTemplate(header, file_type);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("impact")) {

			String filename = "Sr_impact2_t" + ".xlsx";
			String[] header = { "Impact Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			ByteArrayInputStream in = demoTemplate(header, file_type);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("urgency")) {

			String filename = "Sr_urgency_t" + ".xlsx";
			String[] header = { "Urgency Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			ByteArrayInputStream in = demoTemplate(header, file_type);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("category")) {

			String filename = "Sr_category2_t" + ".xlsx";
			String[] header = { "Category Name", "Customer Id", "Is Active", "Effective Start Date",
					"Effective End date" };
			ByteArrayInputStream in = demoTemplate(header, file_type);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("state")) {

			String filename = "Sr_State_t" + ".xlsx";
			String[] header = { "State Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			ByteArrayInputStream in = demoTemplate(header, file_type);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("contact_type")) {

			String filename = "Sr_Contact_type_t" + ".xlsx";
			String[] header = { "Contact Type Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			ByteArrayInputStream in = demoTemplate(header, file_type);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("customer")) {

			String filename = "Sr_customer_t" + ".xlsx";
			String[] header = { "Customer Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			ByteArrayInputStream in = demoTemplate(header, file_type);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("handler")) {

			String filename = "Sr_handler_t" + ".xlsx";
			String[] header = { "User Id", "Role Id", "Is Active", "Effective Start Date", "Effective End date" };
			ByteArrayInputStream in = demoTemplate(header, file_type);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		}

		return new ResponseEntity<String>("Not Found", HttpStatus.BAD_REQUEST);

	}

	public static ByteArrayInputStream demoTemplate(String[] HEADERs, String file_type) throws IOException {
		String SHEET = file_type;
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Sheet sheet = workbook.createSheet(SHEET);
		Row headerRow = sheet.createRow(0);
		for (int col = 0; col < HEADERs.length; col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(HEADERs[col]);
		}
		workbook.write(out);
		return new ByteArrayInputStream(out.toByteArray());

	}

	@PostMapping("/save/{filetype}")
	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @PathVariable String filetype)
			throws IOException, ParseException {
		BufferedReader br;
		InputStream is = file.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		String file_name = file.getOriginalFilename();
		AppUser loggedInUser = userService.getLoggedInUser();

		if (filetype.equalsIgnoreCase("priority")) {

			if (file_name.contains("Sr_priority2_t")) {

				String[] header = { "Priority Name", "Description", "Is Active", "Effective Start Date",
						"Effective End date" };
				ArrayList<Sr_priority2_t> prioritylist = new ArrayList<Sr_priority2_t>();

				Workbook workbook = WorkbookFactory.create(is);

// 			Create a DataFormatter to format and get each cell's value as String
				DataFormatter dataFormatter = new DataFormatter();

				Sheet sheet = workbook.getSheetAt(0);

				// Getting number of columns in the Sheet
				int cols = sheet.getRow(0).getLastCellNum();

				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);

				for (Row row : sheet) {

					if (row.getRowNum() == 0) {
						for (int i = 0; i < cols; i++) {
							String value = dataFormatter.formatCellValue(row.getCell(i));

							if (!header[i].equalsIgnoreCase(value)) {

								return new ResponseEntity<>(
										"priority file Should  have \"" + header + " \"in the header in excel file",
										HttpStatus.BAD_REQUEST);
							}
						}

					}

					if (row.getRowNum() != 0) {
						Date date = row.getCell(3).getDateCellValue();
						Date date2 = row.getCell(4).getDateCellValue();

						Sr_priority2_t priority = new Sr_priority2_t();
						priority.setPriority_name(dataFormatter.formatCellValue(row.getCell(0)));
						priority.setDescription(dataFormatter.formatCellValue(row.getCell(1)));
						priority.setActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));

						priority.setEffective_start_date(date);
						priority.setEffective_end_date(date2);

						prioritylist.add(priority);
					}

				}
				priorityrepo.saveAll(prioritylist);
				workbook.close();

//			

				return new ResponseEntity<>("File Uploaded", HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>("File name should contain Sr_priority2_t", HttpStatus.BAD_REQUEST);

		}

		else if (filetype.contains("impact")) {

			if (file_name.contains("Sr_impact2_t")) {

				String[] header = { "Impact Name", "Description", "Is Active", "Effective Start Date",
						"Effective End date" };

				ArrayList<Sr_impact2_t> impactlist = new ArrayList<Sr_impact2_t>();

				Workbook workbook = WorkbookFactory.create(is);

// 			Create a DataFormatter to format and get each cell's value as String
				DataFormatter dataFormatter = new DataFormatter();

				Sheet sheet = workbook.getSheetAt(0);

				// Getting number of columns in the Sheet
				int cols = sheet.getRow(0).getLastCellNum();

				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);

				for (Row row : sheet) {

					if (row.getRowNum() == 0) {
						for (int i = 0; i < cols; i++) {
							String value = dataFormatter.formatCellValue(row.getCell(i));

							if (!header[i].equalsIgnoreCase(value)) {

								return new ResponseEntity<>("priority file header is not in correct format ",
										HttpStatus.BAD_REQUEST);
							}
						}

					}

					if (row.getRowNum() != 0) {

						Date date = row.getCell(3).getDateCellValue();
						Date date2 = row.getCell(4).getDateCellValue();

						Sr_impact2_t impact = new Sr_impact2_t();
						impact.setImpact_name(dataFormatter.formatCellValue(row.getCell(0)));
						impact.setDescription(dataFormatter.formatCellValue(row.getCell(1)));
						impact.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));

						impact.setEffective_start_date(date);
						impact.setEffective_end_date(date2);

						impactlist.add(impact);
					}

				}
				impactrepo.saveAll(impactlist);
				System.out.println(impactlist);

				workbook.close();

				return new ResponseEntity<>("File Uploaded", HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>("File name should contain Sr_impact2_t", HttpStatus.BAD_REQUEST);

		}

		else if (filetype.contains("urgency")) {

			if (file_name.contains("Sr_urgency_t")) {

				String[] header = { "Urgency Name", "Description", "Is Active", "Effective Start Date",
						"Effective End date" };
				List<String> li = new ArrayList<String>();
				li.add("Impact Name");
				li.add("Description");
				li.add("Is Active");
				li.add("Effective Start Date");
				li.add("Effective End date");

				ArrayList<Sr_urgency_t> impactlist = new ArrayList<Sr_urgency_t>();

				Workbook workbook = WorkbookFactory.create(is);

// 			Create a DataFormatter to format and get each cell's value as String
				DataFormatter dataFormatter = new DataFormatter();

				Sheet sheet = workbook.getSheetAt(0);

				// Getting number of columns in the Sheet
				int cols = sheet.getRow(0).getLastCellNum();

				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);

				for (Row row : sheet) {

					if (row.getRowNum() == 0) {
						for (int i = 0; i < cols; i++) {
							String value = dataFormatter.formatCellValue(row.getCell(i));

							if (!header[i].equalsIgnoreCase(value)) {

								return new ResponseEntity<>(
										"priority file Should  have \"" + li + " \"in the header in excel file",
										HttpStatus.BAD_REQUEST);
							}
						}

					}

					if (row.getRowNum() != 0) {
						Date date = row.getCell(3).getDateCellValue();
						Date date2 = row.getCell(4).getDateCellValue();

						Sr_urgency_t urgency = new Sr_urgency_t();
						urgency.setUrgency_name(dataFormatter.formatCellValue(row.getCell(0)));
						urgency.setDescription(dataFormatter.formatCellValue(row.getCell(1)));
						urgency.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));

						urgency.setEffective_start_date(date);
						urgency.setEffective_end_date(date2);

						impactlist.add(urgency);
					}

				}
				urgencyrepo.saveAll(impactlist);
				System.out.println(impactlist);

				workbook.close();

				return new ResponseEntity<>("File Uploaded", HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>("File name should contain sr_incident_t", HttpStatus.BAD_REQUEST);

		} else if (filetype.equalsIgnoreCase("category")) {

			String[] header = { "Category Name", "Customer Id", "Is Active", "Effective Start Date",
					"Effective End date" };

//			if (file_name.contains("Sr_impact2_t")) {
//
//				ArrayList<Sr_impact2_t> impactlist = new ArrayList<Sr_impact2_t>();
//
//				Workbook workbook = WorkbookFactory.create(is);
//
////	 			Create a DataFormatter to format and get each cell's value as String
//				DataFormatter dataFormatter = new DataFormatter();
//
//				Sheet sheet = workbook.getSheetAt(0);
//
//				// Getting number of columns in the Sheet
//				int cols = sheet.getRow(0).getLastCellNum();
//
//				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);
//
//				for (Row row : sheet) {
//
//					if (row.getRowNum() == 0) {
//						for (int i = 0; i < cols; i++) {
//							String value = dataFormatter.formatCellValue(row.getCell(i));
//
//							if (!header[i].equalsIgnoreCase(value)) {
//
//								return new ResponseEntity<>("priority file header is not in correct format ",
//										HttpStatus.BAD_REQUEST);
//							}
//						}
//
//					}
//
//					if (row.getRowNum() != 0) {
//						String value = dataFormatter.formatCellValue(row.getCell(4));
//						String value2 = dataFormatter.formatCellValue(row.getCell(5));
//			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(value);
//			Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(value2);
//
//						Sr_impact2_t impact = new Sr_impact2_t();
//						impact.setImpact_name(file_name);
//						impact.setDescription(dataFormatter.formatCellValue(row.getCell(2)));
//						impact.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(3))));
//
////					priority.setEffective_start_date(date);
////					priority.setEffective_end_date(date2);
//
//						impactlist.add(impact);
//					}
//
//				}
//				impactrepo.saveAll(impactlist);
//				System.out.println(impactlist);
//
//				workbook.close();
//
//			}

		} else if (filetype.equalsIgnoreCase("state")) {

			String[] header = { "State Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };

			if (file_name.contains("Sr_State_t")) {

				ArrayList<Sr_State_t> statelist = new ArrayList<Sr_State_t>();

				Workbook workbook = WorkbookFactory.create(is);

//	 			Create a DataFormatter to format and get each cell's value as String
				DataFormatter dataFormatter = new DataFormatter();

				Sheet sheet = workbook.getSheetAt(0);

				// Getting number of columns in the Sheet
				int cols = sheet.getRow(0).getLastCellNum();

				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);

				for (Row row : sheet) {

					if (row.getRowNum() == 0) {
						for (int i = 0; i < cols; i++) {
							String value = dataFormatter.formatCellValue(row.getCell(i));

							if (!header[i].equalsIgnoreCase(value)) {

								return new ResponseEntity<>("priority file header is not in correct format ",
										HttpStatus.BAD_REQUEST);
							}
						}

					}

					if (row.getRowNum() != 0) {
						String value = dataFormatter.formatCellValue(row.getCell(3));
						String value2 = dataFormatter.formatCellValue(row.getCell(4));

						Sr_State_t state = new Sr_State_t();
						state.setState_name(dataFormatter.formatCellValue(row.getCell(0)));
						state.setDescription(dataFormatter.formatCellValue(row.getCell(1)));
						state.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));

						state.setEffective_end_date(value2);
						state.setEffective_start_date(value);

						statelist.add(state);
					}

				}
				staterepo.saveAll(statelist);

				workbook.close();

			}

		} else if (filetype.equalsIgnoreCase("contact_type")) {

			String[] header = { "Contact Type Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };

			if (file_name.contains("Sr_Contact_type_t")) {

				ArrayList<Sr_Contact_type_t> statelist = new ArrayList<Sr_Contact_type_t>();

				Workbook workbook = WorkbookFactory.create(is);

//	 			Create a DataFormatter to format and get each cell's value as String
				DataFormatter dataFormatter = new DataFormatter();

				Sheet sheet = workbook.getSheetAt(0);

				// Getting number of columns in the Sheet
				int cols = sheet.getRow(0).getLastCellNum();

				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);

				for (Row row : sheet) {

					if (row.getRowNum() == 0) {
						for (int i = 0; i < cols; i++) {
							String value = dataFormatter.formatCellValue(row.getCell(i));

							if (!header[i].equalsIgnoreCase(value)) {

								return new ResponseEntity<>("priority file header is not in correct format ",
										HttpStatus.BAD_REQUEST);
							}
						}

					}

					if (row.getRowNum() != 0) {
						Date date = row.getCell(3).getDateCellValue();
						Date date2 = row.getCell(4).getDateCellValue();

						Sr_Contact_type_t contact = new Sr_Contact_type_t();
						contact.setCon_type_name(dataFormatter.formatCellValue(row.getCell(0)));
						contact.setDescription(dataFormatter.formatCellValue(row.getCell(1)));
						contact.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));

						contact.setEffective_start_date(date);
						contact.setEffective_end_date(date2);

						statelist.add(contact);
					}

				}
				contactrepo.saveAll(statelist);

				workbook.close();

			}

		} else if (filetype.equalsIgnoreCase("customer")) {

			String[] header = { "Customer Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };

			if (file_name.contains("Sr_customer_t")) {

				ArrayList<Sr_customer_t> customerlist = new ArrayList<Sr_customer_t>();

				Workbook workbook = WorkbookFactory.create(is);

//	 			Create a DataFormatter to format and get each cell's value as String
				DataFormatter dataFormatter = new DataFormatter();

				Sheet sheet = workbook.getSheetAt(0);

				// Getting number of columns in the Sheet
				int cols = sheet.getRow(0).getLastCellNum();

				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);

				for (Row row : sheet) {

					if (row.getRowNum() == 0) {
						for (int i = 0; i < cols; i++) {
							String value = dataFormatter.formatCellValue(row.getCell(i));

							if (!header[i].equalsIgnoreCase(value)) {

								return new ResponseEntity<>("priority file header is not in correct format ",
										HttpStatus.BAD_REQUEST);
							}
						}

					}

					if (row.getRowNum() != 0) {
						Date date = row.getCell(3).getDateCellValue();
						Date date2 = row.getCell(4).getDateCellValue();

						Sr_customer_t customer = new Sr_customer_t();
						customer.setCustomer_name(dataFormatter.formatCellValue(row.getCell(0)));
						customer.setDescription(dataFormatter.formatCellValue(row.getCell(1)));
						customer.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(2))));

						customer.setEffective_start_date(date);
						customer.setEffective_end_date(date2);

						customerlist.add(customer);
					}

				}
				customerepo.saveAll(customerlist);

				workbook.close();

			}

		} else if (filetype.equalsIgnoreCase("handler")) {

			String[] header = { "User Id", "Role Id", "Is Active", "Effective Start Date", "Effective End date" };

//			if (file_name.contains("Sr_State_t")) {
//
//				ArrayList<Sr_State_t> statelist = new ArrayList<Sr_State_t>();
//
//				Workbook workbook = WorkbookFactory.create(is);
//
////	 			Create a DataFormatter to format and get each cell's value as String
//				DataFormatter dataFormatter = new DataFormatter();
//
//				Sheet sheet = workbook.getSheetAt(0);
//
//				// Getting number of columns in the Sheet
//				int cols = sheet.getRow(0).getLastCellNum();
//
//				fileupload.uploadFile(file, loggedInUser.getUserId(), filetype);
//
//				for (Row row : sheet) {
//
//					if (row.getRowNum() == 0) {
//						for (int i = 0; i < cols; i++) {
//							String value = dataFormatter.formatCellValue(row.getCell(i));
//
//							if (!header[i].equalsIgnoreCase(value)) {
//
//								return new ResponseEntity<>("priority file header is not in correct format ",
//										HttpStatus.BAD_REQUEST);
//							}
//						}
//
//					}
//
//					if (row.getRowNum() != 0) {
//			Date date = row.getCell(3).getDateCellValue();
//			Date date2 = row.getCell(4).getDateCellValue();
//
//						Sr_State_t state = new Sr_State_t();
//						state.setState_name(dataFormatter.formatCellValue(row.getCell(1)));
//						state.setDescription(dataFormatter.formatCellValue(row.getCell(2)));
//						state.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(3))));
//
////					priority.setEffective_start_date(date);
////					priority.setEffective_end_date(date2);
//
//						statelist.add(state);
//					}
//
//				}
//				staterepo.saveAll(statelist);
//
//				workbook.close();
//
//			}

		}

		return new ResponseEntity<>("Something Went Wrong please try again....!!!!! ", HttpStatus.BAD_REQUEST);

	}

//	Download template data in excel Files

	@GetMapping("/download/{file_type}")

	public ResponseEntity<?> getFile(@PathVariable String file_type) throws IOException {

		if (file_type.equalsIgnoreCase("priority")) {

			String filename = "Sr_priority2_t" + ".xlsx";
			String[] header = { "Priority Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			List<Sr_priority2_t> findAll = priorityrepo.findAll();
			ByteArrayInputStream in = templateByte(findAll, header);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("impact")) {

			String filename = "Sr_impact2_t" + ".xlsx";
			String[] header = { "Impact Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			List<Sr_impact2_t> findAll = impactrepo.findAll();
			ByteArrayInputStream in = templateByte3(findAll, header);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("urgency")) {

			String filename = "Sr_urgency_t" + ".xlsx";
			String[] header = { "Urgency Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			List<Sr_urgency_t> findAll = urgencyrepo.findAll();
			ByteArrayInputStream in = templateByte2(findAll, header);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("category")) {

			String filename = "Sr_category2_t" + ".xlsx";
			String[] header = { "Category Name", "Customer Id", "Is Active", "Effective Start Date",
					"Effective End date" };
			List<Sr_category2_t> findAll = categoryrepo.findAll();
			ByteArrayInputStream in = templateByte4(findAll, header);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("state")) {

			String filename = "Sr_State_t" + ".xlsx";
			String[] header = { "State Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			List<Sr_State_t> findAll = staterepo.findAll();
			ByteArrayInputStream in = templateByte6(findAll, header);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("contact_type")) {

			String filename = "Sr_Contact_type_t" + ".xlsx";
			String[] header = { "Contact Type Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			List<Sr_Contact_type_t> findAll = contactrepo.findAll();
			ByteArrayInputStream in = templateByte5(findAll, header);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("customer")) {

			String filename = "Sr_customer_t" + ".xlsx";
			String[] header = { "Customer Name", "Description", "Is Active", "Effective Start Date",
					"Effective End date" };
			List<Sr_customer_t> findAll = customerepo.findAll();
			ByteArrayInputStream in = templateByte7(findAll, header);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		} else if (file_type.equalsIgnoreCase("handler")) {

			String filename = "Sr_handler_t" + ".xlsx";
			String[] header = { "User Id", "Role Id", "Is Active", "Effective Start Date", "Effective End date" };
			List<Sr_handler_t> findAll = handlerepo.findAll();
			ByteArrayInputStream in = templateByte8(findAll, header);
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		}

		return new ResponseEntity<String>("Not Found", HttpStatus.BAD_REQUEST);

	}

	public static ByteArrayInputStream templateByte(List<Sr_priority2_t> list, String[] hEADERs) {

		String SHEET = "priority";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < hEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(hEADERs[col]);
			}

			int rowIdx = 1;
			for (Sr_priority2_t p : list) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(p.getPriority_name());
				row.createCell(1).setCellValue(p.getDescription());
				row.createCell(2).setCellValue(p.isActive());
				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));
				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

	public static ByteArrayInputStream templateByte2(List<Sr_urgency_t> list, String[] hEADERs) {

		String SHEET = "URGENCY";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < hEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(hEADERs[col]);
			}

			int rowIdx = 1;
			for (Sr_urgency_t p : list) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(p.getUrgency_name());
				row.createCell(1).setCellValue(p.getDescription());
				row.createCell(2).setCellValue(p.isIsActive());
				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));
				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

	public static ByteArrayInputStream templateByte3(List<Sr_impact2_t> list, String[] hEADERs) {

		String SHEET = "Impact";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < hEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(hEADERs[col]);
			}

			int rowIdx = 1;
			for (Sr_impact2_t p : list) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(p.getImpact_name());
				row.createCell(1).setCellValue(p.getDescription());
				row.createCell(2).setCellValue(p.isIsActive());
				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));
				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

	public static ByteArrayInputStream templateByte4(List<Sr_category2_t> list, String[] hEADERs) {

		String SHEET = "Impact";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < hEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(hEADERs[col]);
			}

			int rowIdx = 1;
			for (Sr_category2_t p : list) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(p.getCustomer_id());
				row.createCell(1).setCellValue(p.getDescription());
				row.createCell(2).setCellValue(p.isActive());
				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));
				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

	public static ByteArrayInputStream templateByte5(List<Sr_Contact_type_t> list, String[] hEADERs) {

		String SHEET = "Impact";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < hEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(hEADERs[col]);
			}

			int rowIdx = 1;
			for (Sr_Contact_type_t p : list) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(p.getCon_type_name());
				row.createCell(1).setCellValue(p.getDescription());
				row.createCell(2).setCellValue(p.isIsActive());
				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));
				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

	public static ByteArrayInputStream templateByte6(List<Sr_State_t> list, String[] hEADERs) {

		String SHEET = "Impact";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < hEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(hEADERs[col]);
			}

			int rowIdx = 1;
			for (Sr_State_t p : list) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(p.getState_name());
				row.createCell(1).setCellValue(p.getDescription());
				row.createCell(2).setCellValue(p.isIsActive());
				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));
				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

	public static ByteArrayInputStream templateByte7(List<Sr_customer_t> list, String[] hEADERs) {

		String SHEET = "Impact";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < hEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(hEADERs[col]);
			}

			int rowIdx = 1;
			for (Sr_customer_t p : list) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(p.getCustomer_name());
				row.createCell(1).setCellValue(p.getDescription());
				row.createCell(2).setCellValue(p.isIsActive());
				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));
				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

	public static ByteArrayInputStream templateByte8(List<Sr_handler_t> list, String[] hEADERs) {

		String SHEET = "Impact";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < hEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(hEADERs[col]);
			}

			int rowIdx = 1;
			for (Sr_handler_t p : list) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(p.getRole());
				row.createCell(1).setCellValue(p.getUser_name());
				row.createCell(2).setCellValue(p.isIsActive());
				row.createCell(3).setCellValue(String.valueOf(p.getEffective_start_date()));
				row.createCell(4).setCellValue(String.valueOf(p.getEffective_end_date()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

	@GetMapping("/getalltemplate")
	public ResponseEntity<?> getALlTemplate() {
		return new ResponseEntity<>(temprepo.findAll(), HttpStatus.ACCEPTED);
	}

}
