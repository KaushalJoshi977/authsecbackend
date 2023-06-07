package com.realnet.entitybuilder.Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FrontEndBuilderService {

	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private Index_Service_ui index_Service;

	public void buildFrontend(Integer proj_id, String modulename, HashMap<String, String> entityname, String addString,
			int j, String prj_name, String table_name) throws IOException {

//		Date d = new Date();	
//		String addString = "_" +d.getTime() ;

		String name2 = modulename;
		String table_name1 = name2.replaceAll(" ", "_");
		String tablepcakage_name = table_name1.substring(0, 1).toUpperCase() + table_name1.substring(1);

		StringBuilder html = new StringBuilder();
		StringBuilder servie = new StringBuilder();
		StringBuilder scss = new StringBuilder();
		StringBuilder ts = new StringBuilder();

		// MAKE HTML
		html.append("<ol class=\"breadcrumb breadcrumb-arrow font-trirong\">\r\n" + "  <li><a href=\"javascript://\"> "
				+ table_name + "</a></li>\r\n" + "</ol>\r\n" + "<div class=\"dg-wrapper\">\r\n"
				+ "  <div class=\"clr-row\">\r\n" + "    <div class=\"clr-col-8\">\r\n" + "      <h3>list </h3>\r\n"
				+ "    </div>\r\n" + "    <div class=\"clr-col-4\" style=\"text-align: right;\">\r\n"

				+ "      <button id=\"add\" class=\"btn btn-primary\" (click)=\"goToAdd(product)\" >\r\n"
				+ "        <clr-icon shape=\"plus\"></clr-icon>ADD\r\n" + "      </button>\r\n" + "    </div></div>\r\n"
				+ "  <clr-datagrid [clrDgLoading]=\"loading\" [(clrDgSelected)]=\"selected\">\r\n"
				+ "    <clr-dg-placeholder>user not found!</clr-dg-placeholder>\r\n");

		Set<Entry<String, String>> entrySet = entityname.entrySet();
		for (Entry<String, String> e : entrySet) {

			String string = e.getKey().toString().replaceAll("\"", "");
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();

			html.append("    <clr-dg-column [clrDgField]=\"'" + lowerCase
					+ "'\"> <ng-container *clrDgHideableColumn=\"{hidden: false}\">\r\n" + lowerCase + "\r\n"
					+ "    </ng-container></clr-dg-column>\r\n");
		}

		html.append("    <clr-dg-row *clrDgItems=\"let user of product\" [clrDgItem]=\"user\">\r\n");

		for (Entry<String, String> e : entrySet) {

			String string = e.getKey().toString().replaceAll("\"", "");;
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();

			html.append("      <clr-dg-cell>{{user." + lowerCase + "}}</clr-dg-cell>\r\n");
		}
		html.append("      <clr-dg-action-overflow>\r\n"
				+ "        <button class=\"action-item\" (click)=\"onEdit(user)\">Edit</button>\r\n"
				+ "        <button class=\"action-item\" (click)=\"onDelete(user)\">Delete</button>\r\n"
				+ "      </clr-dg-action-overflow>\r\n" + "         </clr-dg-row>\r\n" + "    <clr-dg-footer>\r\n"
				+ "      <clr-dg-pagination #pagination [clrDgPageSize]=\"10\">\r\n"
				+ "        <clr-dg-page-size [clrPageSizeOptions]=\"[10,20,50,100]\">Users per page</clr-dg-page-size>\r\n"
				+ "        {{pagination.firstItem + 1}} - {{pagination.lastItem + 1}}\r\n"
				+ "        of {{pagination.totalItems}} users\r\n" + "      </clr-dg-pagination>\r\n"
				+ "    </clr-dg-footer>\r\n" + "  </clr-datagrid>\r\n" + "</div>\r\n"
				+ "<clr-modal [(clrModalOpen)]=\"modalEdit\" [clrModalSize]=\"'lg'\" [clrModalStaticBackdrop]=\"true\">\r\n"
				+ "  <h3 class=\"modal-title\">User Edit</h3>\r\n"
				+ "  <div class=\"modal-body\" *ngIf=\"rowSelected.id\">\r\n"
				+ "    <h2 class=\"heading\">{{rowSelected.id}}</h2>\r\n"
				+ "    <form clrForm (ngSubmit)=\"onUpdate(rowSelected.id)\">\r\n");

		for (Entry<String, String> e : entrySet) {

			String string = e.getKey().toString().replaceAll("\"", "");;
			String datatype = e.getValue().toString().replaceAll("\"", "");;
			String data_lower = datatype.toLowerCase();
			
			
			if (data_lower.equalsIgnoreCase("Paragraph") || data_lower.equalsIgnoreCase("Text Area") 
					||data_lower.equalsIgnoreCase("phone number")) {
				datatype="Text";
			}
			else  {
				datatype=datatype;
			}

			
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();

			html.append("      <clr-input-container>\r\n" + "        <label>" + lowerCase
					+ "<span class=\"required-field\">*</span></label>\r\n"
					+ "        <input id=\"name\" clrInput type=\""+datatype+"\" [(ngModel)]=\"rowSelected." + lowerCase
					+ "\" name=\"" + lowerCase + "\" />\r\n" + "      </clr-input-container>\r\n");
		}
		
		html.append("      <div class=\"modal-footer\">\r\n"
				+ "      <button type=\"button\" class=\"btn btn-outline\" (click)=\"modalEdit = false\">Cancel</button>\r\n"
				+ "      <button type=\"submit\" class=\"btn btn-primary\" >Update</button>\r\n" + "      </div>\r\n"
				+ "    </form>\r\n" + "  </div>\r\n" + "</clr-modal>\r\n"
				+ "<clr-modal [(clrModalOpen)]=\"modaldelete\" [clrModalSize]=\"'lg'\" [clrModalStaticBackdrop]=\"true\">\r\n"
				+ "  <div class=\"modal-body\" *ngIf=\"rowSelected.id\">\r\n"
				+ "    <h1 class=\"delete\">Are You Sure Want to delete?</h1>\r\n"
				+ "    <h2 class=\"heading\">{{rowSelected.id}}</h2>\r\n" + "    <div class=\"modal-footer\">\r\n"
				+ "      <button type=\"button\" class=\"btn btn-outline\" (click)=\"modaldelete = false\">Cancel</button>\r\n"
				+ "    <button type=\"button\" (click)=\"delete(rowSelected.id)\" class=\"btn btn-primary\" >Delete</button>\r\n"
				+ "    </div>\r\n" + "  </div>\r\n" + "</clr-modal>\r\n"
				+ "<clr-modal [(clrModalOpen)]=\"modalAdd\" [clrModalSize]=\"'xl'\" [clrModalStaticBackdrop]=\"true\">\r\n"
				+ "  <h3 class=\"modal-title\">ENTRY FORM</h3>\r\n" + "  <div class=\"modal-body\">\r\n"
				+ "     <form [formGroup]=\"entryForm\" (ngSubmit)=\"onSubmit()\">\r\n"
				+ "   <div class=\"clr-row\" style=\"height: fit-content;\">\r\n");

		for (Entry<String, String> e : entrySet) {

			String string = e.getKey().toString().replaceAll("\"", "");;
			String datatype = e.getValue().toString().replaceAll("\"", "");;
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();
			String data_lower = datatype.toLowerCase();

			if (data_lower.equalsIgnoreCase("Paragraph") || data_lower.equalsIgnoreCase("Text Area") 
					||data_lower.equalsIgnoreCase("phone number")) {
				datatype="Text";
			}
			else  {
				datatype=datatype;
			}

			html.append("      <div class=\"clr-col-md-3 clr-col-sm-12\" style=\"margin-bottom: 20px;\">\r\n"
					+ "          <label> " + lowerCase + ":</label>\r\n"
					+ "          <input type=\""+datatype+"\" clrCheckbox formControlName=\"" + lowerCase + "\" />\r\n"
					+ "        </div>\r\n");
		}

		html.append("      </div>\r\n" + " <div class=\"modal-footer\">\r\n"
				+ "<button type=\"button\" class=\"btn btn-outline\" (click)=\"modalAdd = false\">Cancel</button>\r\n"
				+ "        <button type=\"submit\" class=\"btn btn-primary\" >ADD</button>\r\n" + "        </div>\r\n"
				+ "</form>\r\n" + "  </div>\r\n" + "</clr-modal>");
		System.out.println("html class created \n");

		// MAKE SCSS

		scss.append("input[type=text],[type=date], select {\r\n" + "  width: 100%;\r\n" + "  padding: 12px 20px;\r\n"
				+ "  margin: 8px 0;\r\n" + "  display: inline-block;\r\n" + "  border: 1px solid #ccc;\r\n"
				+ "  border-radius: 4px;\r\n" + "  box-sizing: border-box;\r\n" + "}\r\n" + ".required-field{\r\n"
				+ "  color: red;\r\n" + "\r\n" + "}\r\n" + ".horizontal{\r\n" + "  width: 50%;\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "\r\n" + ".td-title {\r\n" + "  text-align: center;\r\n"
				+ "color: white;\r\n" + "  font-weight: bold;\r\n"
				+ "  background-color: rgba(63, 122, 231, 0.863);\r\n" + "  //color: rgb(24, 13, 13);\r\n" + "}\r\n"
				+ "th{\r\n" + "  background-color:rgb(170, 169, 169);\r\n" + "  font-weight: bold;\r\n" + "}\r\n"
				+ ".td-content{\r\n" + "  text-align: left;\r\n" + "}\r\n" + ".delete,.heading{\r\n"
				+ "  text-align: center;\r\n" + "  color: red;\r\n" + "}\r\n" + ".section p {\r\n"
				+ "background-color: rgb(206, 201, 201);\r\n" + "  padding: 10px;\r\n" + "  font-size: 18px;\r\n"
				+ "}\r\n" + "");
		System.out.println("scss class created \n");

		// MAKE TS
		ts.append("import { Component, OnInit } from '@angular/core';\r\n"
				+ "import { ToastrService } from 'ngx-toastr';\r\n"
				+ "import { AlertService } from 'src/app/services/alert.service';\r\n" + "import { " + table_name
				+ "service} from './" + table_name + ".service" + "';\r\n"

				+ "import { FormArray, FormBuilder, FormGroup } from '@angular/forms';\r\n" + "@Component({\r\n"
				+ "  selector: 'app-" + table_name + "',\r\n" + "  templateUrl: './" + table_name
				+ ".component.html',\r\n" + "  styleUrls: ['./" + table_name + ".component.scss']\r\n" + "})\r\n"
				+ "export class " + table_name + "Component implements OnInit {\r\n" + "  rowSelected :any= {};\r\n"
				+ "  modaldelete=false;\r\n" + "  modalEdit=false;\r\n" + "  modalAdd= false;\r\n"
				+ "  public entryForm: FormGroup;\r\n" + "  loading = false;\r\n" + "  product;\r\n"
				+ "  modalOpenedforNewLine = false;\r\n" + "  newLine:any;\r\n" + " selected: any[] = []; "
				+ "constructor(\r\n" + "    private mainService:" + table_name + "service,\r\n"
				+ "    private alertService: AlertService,\r\n" + "    private toastr: ToastrService,\r\n"
				+ "    private _fb: FormBuilder,\r\n" + "  ) { }\r\n" + "  ngOnInit(): void {\r\n"
				+ "    this.getData();\r\n" + "    this.entryForm = this._fb.group({\r\n");
		for (Entry<String, String> e : entrySet) {

			String string = e.getKey().toString().replaceAll("\"", "");;
			String datatype = e.getValue().toString().replaceAll("\"", "");;
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();

			ts.append("  " + lowerCase + ": [null],\r\n");
		}
		ts.append("    });\r\n" + "  }\r\n" + "  getData() {\r\n"
				+ "    this.mainService.getAll().subscribe((data) => {\r\n" + "      console.log(data);\r\n"
				+ "      this.product = data;\r\n" + "     \r\n" + "    });\r\n" + "  }\r\n" + "  onEdit(row) {\r\n"
				+ "    this.rowSelected = row;\r\n" + "    this.modalEdit = true;\r\n" + "  }\r\n"
				+ "   onDelete(row) {\r\n" + "    this.rowSelected = row;\r\n" + "     this.modaldelete=true;\r\n"
				+ "  }\r\n" + "  delete(id)\r\n" + "  {\r\n" + "    this.modaldelete = false;\r\n"
				+ "    console.log(\"in delete  \"+id);\r\n" + "    this.mainService.delete(id).subscribe(\r\n"
				+ "      (data) => {\r\n" + "        console.log(data);\r\n" + "        this.ngOnInit();\r\n "
				+ " if (data) {" + "				      this.toastr.success('Deleted successfully');"
				+ "      }\r\n    });\r\n" + "  }\r\n" + "    onUpdate(id) {\r\n" + "      this.modalEdit = false;\r\n"
				+ "         //console.log(\"in update\");\r\n" + "      console.log(\"id  \"+id);\r\n"
				+ "      console.log( this.rowSelected );\r\n" + "      //console.log(\"out update\");\r\n"
				+ "      this.mainService.update(id,this.rowSelected).subscribe(\r\n" + "        (data) => {\r\n"
				+ "          console.log(data);\r\n" + "        },\r\n" + "      );\r\n" + "      if (id) {\r\n"
				+ "        this.toastr.success('Updated successfully');\r\n" + "              }\r\n" + "  }\r\n"

				+ "  goToAdd(row) {\r\n" + "this.modalAdd = true;\r\n" + "  }\r\n" + "onSubmit() {\r\n"
				+ "  console.log(this.entryForm.value);\r\n" + "  if (this.entryForm.invalid) {\r\n" + "    return;\r\n"
				+ "  }\r\n" + "  this.onCreate();\r\n" + "}\r\n" + "onCreate() {\r\n" + "     this.modalAdd=false;\r\n"
				+ "  this.mainService.create(this.entryForm.value).subscribe(\r\n" + "    (data) => {\r\n"
				+ "      console.log(data);\r\n" + "      this.ngOnInit();\r\n" + "    },\r\n" + "  );\r\n"
				+ "  if (this.entryForm.value) {\r\n" + "    this.toastr.success('Added successfully');\r\n" + "  }\r\n"
				+ "}\r\n" + "}");
		System.out.println("ts class created \n");

		// MAKE SERVICE CLASS
		servie.append("import { Injectable } from '@angular/core';\r\n"
				+ "import { HttpParams } from \"@angular/common/http\";\r\n"
				+ "import { Observable } from \"rxjs\";\r\n"
				+ "import { ApiRequestService } from \"src/app/services/api/api-request.service\";\r\n"

				+ "@Injectable({\r\n" + "  providedIn: 'root'\r\n" + "})\r\n" + "export class " + table_name + "service"
				+ "{\r\n" + "  private baseURL = " + "\"" + table_name + "/" + table_name + "\" ;"
				+ "  constructor(\r\n" + "    private apiRequest: ApiRequestService,\r\n" + "  ) { }\r\n"
				+ "  getAll(page?: number, size?: number): Observable<any> {\r\n"
				+ "    return this.apiRequest.get(this.baseURL);\r\n" + "  }\r\n"
				+ "  getById(id: number): Observable<any> {\r\n" + "    const _http = this.baseURL + \"/\" + id;\r\n"
				+ "    return this.apiRequest.get(_http);\r\n" + "  }\r\n"
				+ "  create(data: any): Observable<any> {\r\n"
				+ "    return this.apiRequest.post(this.baseURL, data);\r\n" + "  }\r\n"
				+ "  update(id: number, data: any): Observable<any> {\r\n"
				+ "    const _http = this.baseURL + \"/\" + id;\r\n"
				+ "    return this.apiRequest.put(_http, data);\r\n" + "  }\r\n"
				+ "  delete(id: number): Observable<any> {\r\n" + "    const _http = this.baseURL + \"/\" + id;\r\n"
				+ "    return this.apiRequest.delete(_http);\r\n" + "  }\r\n" + "}");

		System.out.println("servie created \n");

		// CEATE PACKAGE
//		String directoryName = directoryName();
//
//		String Path1 = directoryName + File.separator + "BuilderComponents" + File.separator + table_name + addString
//				+ "front";

		String Path1 = projectPath + File.separator + "cns-portal/code-extractor/builders";

		File builderMainDir1 = new File(Path1);
		if (!builderMainDir1.exists()) {
			boolean mkdir = builderMainDir1.mkdir();
			System.out.println("builder folder " + mkdir);
		}

		Path1 = Path1 + File.separator + proj_id;

		File staticMainDir1 = new File(Path1);
		if (!staticMainDir1.exists()) {
			boolean mkdir = staticMainDir1.mkdir();
			System.out.println(mkdir);
		}

		Path1 = Path1 + File.separator + proj_id;

		File tble_folder = new File(Path1);
		if (!tble_folder.exists()) {
			boolean mkdir = tble_folder.mkdir();
			System.out.println(mkdir);
		}

		Path1 = Path1 + File.separator + table_name + "_ui";

		File staticMainDir3 = new File(Path1);
		if (!staticMainDir3.exists()) {
			boolean mkdir = staticMainDir3.mkdir();
			System.out.println("frontend folder created = " + mkdir);
		}

		// MAKE INDEX FILE
		index_Service.build_index(proj_id, j, Path1, prj_name);

		// CREATING JAVA CLASS
		String Path = Path1 + File.separator + table_name + addString + ".component.html";
		String servicePath = Path1 + File.separator + table_name + addString + ".service.ts";
		String tspath = Path1 + File.separator + table_name + addString + ".component.ts";
		String scsspath = Path1 + File.separator + table_name + addString + ".component.scss";

		System.out.println("component = " + Path);
		System.out.println("service = " + servicePath);

		System.out.println("ts = " + tspath);

		System.out.println("scss = " + scsspath);

//		creating files
		File file0 = new File(Path);
		File file1 = new File(servicePath);

		File file2 = new File(tspath);
		File file3 = new File(scsspath);

		if (!file0.exists()) {
			boolean createNewFile = file0.createNewFile();
			System.out.println("componenet file created = " + createNewFile);

		}
		if (!file1.exists()) {
			boolean createNewFile = file1.createNewFile();
			System.out.println("service file created = " + createNewFile);

		}
		if (!file2.exists()) {
			boolean createNewFile = file2.createNewFile();
			System.out.println("ts file created = " + createNewFile);

		}
		if (!file3.exists()) {
			boolean createNewFile = file3.createNewFile();
			System.out.println("scss file created = " + createNewFile);

		}
//			Writing files

		FileWriter fw = new FileWriter(file0.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(html.toString());
		bw.close();

		FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		bw1.write(servie.toString());
		bw1.close();

		FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
		BufferedWriter bw2 = new BufferedWriter(fw2);
		bw2.write(ts.toString());
		bw2.close();

		FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
		BufferedWriter bw3 = new BufferedWriter(fw3);
		bw3.write(scss.toString());
		bw3.close();

		// routing module.ts
		String rout_import = "import { " + table_name + addString + "Component } from './BuilderComponents/"
				+ table_name + addString + "front" + "/" + table_name + addString + ".component';";
		String rout_path = "{path:'" + table_name + addString + "',component:" + table_name + addString + "Component},"
				+ "\n";
		String file_name = "main-routing.module.ts";

		buildfront2(rout_import, rout_path, file_name, prj_name, proj_id);

		// module.ts
		String module_import = "import { " + table_name + addString + "Component } from './BuilderComponents/"
				+ table_name + addString + "front" + "/" + table_name + addString + ".component';";
		String com_name = table_name + addString + "Component," + "\n";
		String module_file_name = "main.module.ts";

		buildfront2(module_import, com_name, module_file_name, prj_name, proj_id);

	}

//	public String directoryName1(String prj_name, Integer proj_id) {
//
//		String frontendpath = File.separator + "frontend" + File.separator + "angular-clarity-master" + File.separator
//				+ "src" + File.separator + "app" + File.separator + "modules" + File.separator + "main";
//
////		String frontendpath = File.separator + "frontend/angular-ui/src/app/modules/main";
//
//		StringBuilder Dir = new StringBuilder();
//
//		Dir.append(projectPath);
//		System.out.println(projectPath);
//		int lastIndexOf = projectPath.lastIndexOf(File.separator);
//		String prj_path = projectPath.substring(0, lastIndexOf);
//		String new_path = prj_path + File.separator + "cns-portal/code-extractor/builders" + File.separator + proj_id
//				+ File.separator + "index" + File.separator + prj_name + frontendpath;
//
//		System.out.println(new_path);
//
//		return new_path;
//	}

	public void buildfront2(String r_import, String com_name, String file_name, String prj_name, Integer proj_id)
			throws IOException {

		StringBuilder frontend = new StringBuilder();

		// CEATE PACKAGE
//		String directoryName = directoryName(prj_name, proj_id);

		String directoryName = projectPath + File.separator + "cns-portal/code-extractor/builders" + File.separator
				+ proj_id + File.separator + "index" + File.separator + prj_name + File.separator + "frontend"
				+ File.separator + "angular-clarity-master" + File.separator + "src" + File.separator + "app"
				+ File.separator + "modules" + File.separator + "main";

//		String directoryName = "/Users/Gaurav Kumar/Desktop/Job Pro/app_builder/frontend/angular-ui/src/app/modules/main";

		String filePath = directoryName + File.separator + file_name;
		System.out.println("routing path is " + filePath);

		String str = com_name;

		int length = str.length();

		// CEATE PACKAGE

		File file = null;

		file = new File(filePath);

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;

		while ((line = br.readLine()) != null) {
			frontend.append(line + "\n");
			System.out.println(line);

		}
		fr.close();
		br.close();
		System.out.println(frontend.length());

		String back = "// buildercomponents";
		int back_l = back.length();

		int ord = StringUtils.ordinalIndexOf(frontend.toString(), back, 1);

		String front = frontend.substring(0, ord + back_l) + "\n" + str + "\n"
				+ frontend.substring(ord + back_l, frontend.length());

		String codee = front.substring(0, front.lastIndexOf("\n")); // remove last line break

//		creating files
		File file1 = new File(filePath);

//		Writing files

		FileWriter fr1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter br1 = new BufferedWriter(fr1);
		PrintWriter out = new PrintWriter(br1);
		out.println(r_import);

		br1.write(codee.toString());
		br1.close();

	}

}
