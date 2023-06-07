package com.realnet.entitybuilder.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.entitybuilder.Services.ActionBuilder_Update_Service;
import com.realnet.entitybuilder.Services.ActionService_Insert;
import com.realnet.entitybuilder.Services.BackendBuilderService;
import com.realnet.entitybuilder.Services.DashBuilderService;
import com.realnet.entitybuilder.Services.FilebuildService;
import com.realnet.entitybuilder.Services.FrontEndBuilderService;
import com.realnet.entitybuilder.Services.Index_Service_be;
import com.realnet.entitybuilder.Services.Script_Making;
import com.realnet.entitybuilder.Services.WhoColumnService;
import com.realnet.entitybuilder.response.EntityResponse;
import com.realnet.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/entityBuilder", produces = MediaType.APPLICATION_JSON_VALUE)
public class WireframeBuilder {

	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private BackendBuilderService backendservice;

	@Autowired
	private Script_Making script_serviceMaking;

	@Autowired
	private DashBuilderService dashboardBuilderService;

	@Autowired
	private Index_Service_be index_Service;

	@Autowired
	private FilebuildService filebuildService;

	@Autowired
	private WhoColumnService whoColumnService;

	@Autowired
	private ActionBuilder_Update_Service actionBuilderService;

	@Autowired
	private ActionService_Insert actionService_2;

	@GetMapping(value = "/BuildByProject/{id}")
	public ResponseEntity<?> ADDINJOBPRO(@PathVariable Integer id)
			throws UnsupportedEncodingException, JsonProcessingException {

		int count = 6;
		String job_type = "build app";

		// data inserted in workflow
		System.out.println(Constant.LOCAL_HOST + Constant.BACKEND_PORT_9191);
		String job_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
				+ "/entityBuilder/pull_script/" + id + "/" + count + "/";

		String Save_work_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
				+ "/sureops/savework?id=" + id + "&count=" + count + "&job_url=" + job_url + "&job_type=" + job_type;
		ResponseEntity<Object> get = GET(Save_work_url);
		if (get.hasBody()) {
			return new ResponseEntity<>("data inserted in workflow_entity", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("data not inserted", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/pull_script/{proj_id}/{count}/{workflow_json_id}")
	public ResponseEntity<?> Pull_Script(@PathVariable Integer proj_id, @PathVariable int count,
			@PathVariable int workflow_json_id) throws IOException {

		// git pull for app builder
		Integer current_state = 0;

		String workflow_id = "56";
		int status_code = 500;

		String responsebody = "null_body";

		ResponseEntity<?> get = script_serviceMaking.CreateFiles(proj_id, workflow_id);

		status_code = get.getStatusCodeValue();

		if (status_code == 500) {

			if (get.getBody() != null) {

				responsebody = get.getBody().toString().replace("{", "").replace("}", "");

			}

			String job_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
					+ "/entityBuilder/run_pullscript/" + proj_id + "/" + count + "/" + workflow_json_id + "/"
					+ current_state;

			// for update workflow json
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + job_url + "&resp_body=" + responsebody;
			GET(update_url);
			return new ResponseEntity<>("script not created", HttpStatus.BAD_REQUEST);

		} else {

			if (get.getBody() != null) {

				responsebody = get.getBody().toString().replace("{", "").replace("}", "");

			}
			current_state++;

			String job_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
					+ "/entityBuilder/run_pullscript/" + proj_id + "/" + count + "/" + workflow_json_id + "/"
					+ current_state;

			// for update workflow json
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + job_url + "&resp_body=" + responsebody;
			GET(update_url);

			// run next job
//				GET(job_url);

			return new ResponseEntity<>("pull script created", HttpStatus.OK);
		}

	}

	@GetMapping("/run_pullscript/{proj_id}/{count}/{workflow_json_id}/{current_state}")
	public ResponseEntity<?> run_pullscript(@PathVariable Integer proj_id, @PathVariable Integer count,
			@PathVariable Integer workflow_json_id, @PathVariable Integer current_state) {

		String filepath = projectPath + File.separator + "cns-portal/code-extractor/builders" + File.separator + proj_id
				+ File.separator + "index";

		String string = "/data/cns-portal/code-extractor/builders/5071/index";
		String gitpull_file = "git_pull_exec.sh";

		String script_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
				+ "/entityBuilder/runScript?filepath=" + filepath + "&filename=";
		int status_code = 500;

		String responsebody = "null_body";

		try {

			ResponseEntity<Object> gitpull = GET(script_url + gitpull_file);

			if (gitpull.getStatusCodeValue() == 200) {
				log.info("gitpull file run");

			}

			if (gitpull.getBody() != null) {

				responsebody = gitpull.getBody().toString().replace("{", "").replace("}", "");
			}

			status_code = gitpull.getStatusCodeValue();
			current_state++;

			String delete_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
					+ "/entityBuilder/file_builder/" + proj_id + "/" + count + "/" + workflow_json_id + "/"
					+ current_state;

			// for update workflow json
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + delete_url + "&resp_body="
					+ responsebody;
			GET(update_url);

			// run next job
//				GET(delete_url);

		} catch (RestClientException e) {

			responsebody = e.toString().replace("{", "").replace("}", "");
			System.out.println(responsebody);
			String delete_url = "no url";

			System.out.println("rest client error");
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + delete_url + "&resp_body="
					+ responsebody;
			GET(update_url);

		}

		if (status_code == 500) {
			return new ResponseEntity<>("file not run", HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<>("file run", HttpStatus.ACCEPTED);

		}

	}

	// BUILD ALL BY PROJECT ID
	@GetMapping(value = "/file_builder/{proj_id}/{count}/{workflow_json_id}/{current_state}")
	public ResponseEntity<?> buildfile_byTechstack(@PathVariable Integer proj_id, @PathVariable int count,
			@PathVariable int workflow_json_id, @PathVariable int current_state) throws IOException {

		int i = 0;
		int j = 1;
		int l = 2;
		int k = 1;

		int status_code = 500;

		List<String> tablename = new ArrayList<>();
		List<String> repo_name = new ArrayList<>();

		HashMap<String, String> entityname = new HashMap<>();
		HashMap<String, String> updateaction = new HashMap<>();

		String prj_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/getproject/" + proj_id;

		// get project
		ResponseEntity<Object> prj = GET(prj_url);
		Object prj_body = prj.getBody();

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(prj_body);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		// FOR WIRE FRAME CODE

		JsonObject obj = element.getAsJsonObject();
		String prj_prefix = obj.get("projectPrefix").getAsString();

		String prj_name = obj.get("projectName").getAsString();

		JsonElement modules = obj.get("modules");

		// DUMMY INDEX CALL
		String copy_from = null;
		String Copy_to_path = null;
		index_Service.build_index(proj_id, prj_name, j, copy_from, Copy_to_path);
		j++;

		JsonArray module_list = modules.getAsJsonArray();

		if (!modules.isJsonNull()) {

			for (JsonElement modue : module_list) {
				JsonObject module = modue.getAsJsonObject();

				String modulen = module.get("moduleName").toString();
				String mo = modulen.toLowerCase().replaceAll(" ", "_");
				String mol = mo.replaceAll("\"", "");
				String moduleName = mol.substring(0, 1).toUpperCase() + mol.substring(1);
				repo_name.add(moduleName);

				JsonElement fb_header = module.get("rn_fb_headers");
				JsonArray rn_fb_headers = fb_header.getAsJsonArray();
				if (!rn_fb_headers.isJsonNull()) {

					for (JsonElement header : rn_fb_headers) {

						JsonObject header_obj = header.getAsJsonObject();
						boolean build = header_obj.get("build").getAsBoolean();
						boolean testing = header_obj.get("testing").getAsBoolean();
						
						String tech_stack = header_obj.get("techStack").getAsString();
						String object_type = header_obj.get("objectType").getAsString();
						String sub_object_type = header_obj.get("subObjectType").getAsString();


						String header_id = header_obj.get("id").toString();

						if (testing || build) {

							String wf_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
									+ "/token/fnd1/callingsureops/wfline/" + header_id;
							System.out.println("wf url " + wf_url + "\n");
							try {

								ResponseEntity<Object> wf_get = GET(wf_url);

								if (wf_get.hasBody()) {

									String Wf_str = mapper.writeValueAsString(wf_get.getBody());
									JsonElement wf_element = parser.parse(Wf_str);

									JsonObject wf_obj = wf_element.getAsJsonObject();
									String model = wf_obj.get("model").getAsString();

//					Optional<Rn_wf_lines_3> wireframe = repo.findheader(header.getId());
//								i++;

									JsonElement model_element = parser.parse(model);
									JsonObject jsonObject = model_element.getAsJsonObject();

									JsonElement tab_name = jsonObject.get("name");
									tablename.add(tab_name.getAsString());

									JsonElement element2 = jsonObject.get("attributes");
									System.out.println(element2);

									JsonArray jsonArray = element2.getAsJsonArray();
									System.out.println(jsonArray);

									for (JsonElement ar : jsonArray) {

										JsonObject obj1 = ar.getAsJsonObject();
//										System.out.println(obj1);

										// FOR CHILD FILE
//										JsonElement children = obj1.get("children");
////										String child = obj1.get("children").getasj;
//
//										System.out.println("child -- " + children);
//
//										if (children != null) {
//											
//											JsonElement label = obj1.get("label");
//											childtable.add(tablename.get(0) + "_" + label.toString().replaceAll("\"", "").replaceAll(" ", "_"));
////											tablename.add(childtable.get(0));
//
//											JsonArray array = children.getAsJsonArray();
////											System.out.println(array);
//
//											for (JsonElement ar1 : array) {
//
//												JsonObject obj2 = ar1.getAsJsonObject();
//
//												String field_value = obj2.get("label").getAsString();
//												String field1 = field_value.replaceAll(" ", "_");
//												System.out.println(field_value);
//
//												String datatype = obj2.get("type").getAsString();
//												String data_type = datatype.replaceAll(" ", "_");
//
//												entityline.put(field1, data_type);
//											}
//
//											// file build for children table
//											boolean child_file = filebuildService.filebuild(obj, moduleName, childtable,
//													entityline, proj_id, prj_name);
//											if (child_file) {
//												System.out.println("child table build ");
//											}
//
//										}
//
////										************ end here child code ******
//										else {

										String field_value1 = obj1.get("label").getAsString();
										String field2 = field_value1.replaceAll(" ", "_");
										System.out.println(field_value1);

										String type = obj1.get("type").getAsString();
										String data_type1 = type.replaceAll(" ", "_");

//											String type = obj1.get("title").toString().replaceAll("\"", "");

//											for one to many code

										if (data_type1.contains("button")) {
											String actiontype = obj1.get("actiontype").toString().replaceAll(" ", "_")
													.replaceAll("\"", "").toLowerCase();
											if (actiontype.contains("update")) {
												String entity1 = obj1.get("entity1").toString().replaceAll(" ", "_")
														.replaceAll("\"", "").toLowerCase();
												String body1 = obj1.get("body1").getAsString();

												if (!entity1.isEmpty() && !body1.isEmpty()) {
													updateaction.put(entity1, body1);

												}

												String entity2 = obj1.get("entity2").toString().replaceAll(" ", "_")
														.replaceAll("\"", "").toLowerCase();
												String body2 = obj1.get("body2").getAsString();
												if (!entity2.isEmpty() && !body2.isEmpty()) {

													updateaction.put(entity2, body2);
												}

												String entity3 = obj1.get("entity3").toString().replaceAll(" ", "_")
														.replaceAll("\"", "").toLowerCase();
												String body3 = obj1.get("body3").getAsString();
												if (!entity3.isEmpty() && !body3.isEmpty()) {

													updateaction.put(entity3, body3);
												}

//												String update_entity = updateaction.toString().replace("{", "@")
//														.replace("}", "$");
//												String update_entity = updateaction.toString();

												actionBuilderService.updateaction(proj_id, prj_name, moduleName,
														tab_name.getAsString(), k, updateaction);

												updateaction.clear();

											}
//											else if (actiontype.contains("insert")) {
//												String entity1 = obj1.get("entity1").toString().replaceAll(" ", "_")
//														.replaceAll("\"", "").toLowerCase();
//												String body1 = obj1.get("body1").getAsString();
//
//												if (!entity1.isEmpty() && !body1.isEmpty()) {
//													updateaction.put(entity1, body1);
//
//												}
//
//												String entity2 = obj1.get("entity2").toString().replaceAll(" ", "_")
//														.replaceAll("\"", "").toLowerCase();
//												String body2 = obj1.get("body2").getAsString();
//												if (!entity2.isEmpty() && !body2.isEmpty()) {
//
//													updateaction.put(entity2, body2);
//												}
//
//												String entity3 = obj1.get("entity3").toString().replaceAll(" ", "_")
//														.replaceAll("\"", "").toLowerCase();
//												String body3 = obj1.get("body3").getAsString();
//												if (!entity3.isEmpty() && !body3.isEmpty()) {
//
//													updateaction.put(entity3, body3);
//												}
//
//												actionService_2.insert_action(proj_id, prj_name, moduleName,
//														tab_name.getAsString(), updateaction);
//
//												updateaction.clear();
////
//											}

										} else if (field_value1.equals("OnetoOne") || field_value1.equals("OnetoMany")
												|| field_value1.equals("ManytoMany")) {
											String totable = obj1.get("toWireframe").getAsString();
											entityname.put(field_value1, totable);

										} else {
											entityname.put(field2, data_type1);

										}
//										}

									}

									// call file build service
									boolean filebuild = filebuildService.filebuild(obj, moduleName, tablename,
											entityname, proj_id, prj_name,tech_stack,object_type,sub_object_type);
									whoColumnService.createwhocolumn(proj_id, prj_name, moduleName);

									if (filebuild) {

										// set build true
										String wireframe_url = "http://" + Constant.LOCAL_HOST + ":"
												+ Constant.BACKEND_PORT_9191 + "/token/fnd1/callingsureops/wireframe/"
												+ header_id;

										ResponseEntity<Object> get = GET(wireframe_url);

										int statusCodeValue = get.getStatusCodeValue();
										if (statusCodeValue <= 209) {

											tablename.removeAll(tablename);
											entityname.clear();
											i++;

										}
										j++;

									}

//									FOR FRONTEND UPDATE ACTION
									for (JsonElement ar : jsonArray) {

										List<String> updatetable = new ArrayList<>();
										List<String> updatebody = new ArrayList<>();

										JsonObject obj1 = ar.getAsJsonObject();

										String field_value1 = obj1.get("label").getAsString();
										String field2 = field_value1.replaceAll(" ", "_");
										System.out.println(field_value1);

										String type = obj1.get("type").getAsString();
										String data_type1 = type.replaceAll(" ", "_");

										if (data_type1.contains("button")) {
											String actiontype = obj1.get("actiontype").toString().replaceAll(" ", "_")
													.replaceAll("\"", " ").toLowerCase();

											if (actiontype.contains("update")) {
												String entity1 = obj1.get("entity1").toString().replaceAll(" ", "_")
														.replaceAll("\"", "").toLowerCase();
												String body1 = obj1.get("body1").getAsString();

												if (!entity1.isEmpty() && !body1.isEmpty()) {
													updateaction.put(entity1, body1);

												}

												String entity2 = obj1.get("entity2").toString().replaceAll(" ", "_")
														.replaceAll("\"", "").toLowerCase();
												String body2 = obj1.get("body2").getAsString();
												if (!entity2.isEmpty() && !body2.isEmpty()) {

													updateaction.put(entity2, body2);
												}

												String entity3 = obj1.get("entity3").toString().replaceAll(" ", "_")
														.replaceAll("\"", "").toLowerCase();
												String body3 = obj1.get("body3").getAsString();
												if (!entity3.isEmpty() && !body3.isEmpty()) {

													updateaction.put(entity3, body3);
												}

//												String update_entity = updateaction.toString().replace("{", "@")
//														.replace("}", "$");

												actionBuilderService.updateactionfront(proj_id, prj_name, moduleName,
														tab_name.getAsString(), k, updateaction);

												updateaction.clear();

											}
//											else if (actiontype.contains("insert")) {
//												String entity1 = obj1.get("entity1").toString().replaceAll(" ", "_")
//														.replaceAll("\"", "").toLowerCase();
//												String body1 = obj1.get("body1").getAsString();
//
//												if (!entity1.isEmpty() && !body1.isEmpty()) {
//													updateaction.put(entity1, body1);
//
//												}
//
//												String entity2 = obj1.get("entity2").toString().replaceAll(" ", "_")
//														.replaceAll("\"", "").toLowerCase();
//												String body2 = obj1.get("body2").getAsString();
//												if (!entity2.isEmpty() && !body2.isEmpty()) {
//
//													updateaction.put(entity2, body2);
//												}
//
//												String entity3 = obj1.get("entity3").toString().replaceAll(" ", "_")
//														.replaceAll("\"", "").toLowerCase();
//												String body3 = obj1.get("body3").getAsString();
//												if (!entity3.isEmpty() && !body3.isEmpty()) {
//
//													updateaction.put(entity3, body3);
//												}
//
//												actionService_2.insertactionfront(proj_id, prj_name, moduleName,
//														tab_name.getAsString(), k, updateaction);
//
//												updateaction.clear();
//
//											}
										}

									}

								}
							} catch (Exception e) {
							}

						}
					}
				} else {
					return new ResponseEntity<>(new EntityResponse("header is empty"), HttpStatus.BAD_REQUEST);

				}

//				************* MAKE TABLE *******************

			}

		} else {
			return new ResponseEntity<>(new EntityResponse("modules is empty"), HttpStatus.BAD_REQUEST);

		}

		// WIREFRAME CODE END ***************

		String resp_body = i + " table build";
		if (i != 0) {

			status_code = 200;
			current_state++;
			String push_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
					+ "/entityBuilder/push_script/" + proj_id + "/" + count + "/" + workflow_json_id + "/"
					+ current_state;

			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + push_url + "&resp_body=" + resp_body;
			GET(update_url);
			return new ResponseEntity<>(new EntityResponse(i + " table build"), HttpStatus.CREATED);

		} else {
			String push_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
					+ "/entityBuilder/push_script/" + proj_id + "/" + count + "/" + workflow_json_id + "/"
					+ current_state;

			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + push_url + "&resp_body=" + resp_body;
			GET(update_url);

			return new ResponseEntity<>(new EntityResponse(i + " table build"), HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping(value = "/push_script/{proj_id}/{count}/{workflow_json_id}/{current_state}")
	public ResponseEntity<?> push_script(@PathVariable Integer proj_id, @PathVariable int count,
			@PathVariable int workflow_json_id, @PathVariable int current_state) throws IOException {

		String workflow_id = "58";

		ResponseEntity<?> get = script_serviceMaking.CreateFiles(proj_id, workflow_id);

		int status_code = get.getStatusCodeValue();
		String responsebody = "null_body";

		if (status_code == 500) {

			if (get.getBody() != null) {

				responsebody = get.getBody().toString().replace("{", "").replace("}", "");

			}

			String job_url = "no url";

			// for update workflow json
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + job_url + "&resp_body=" + responsebody;
			GET(update_url);
			return new ResponseEntity<>("script not created", HttpStatus.BAD_REQUEST);

		} else {

			if (get.getBody() != null) {

				responsebody = get.getBody().toString().replace("{", "").replace("}", "");

			}
			current_state++;

			String delete_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
					+ "/entityBuilder/runscript/" + proj_id + "/" + count + "/" + workflow_json_id + "/"
					+ current_state;

			// for update workflow json
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + delete_url + "&resp_body="
					+ responsebody;
			GET(update_url);

			// run next job
//				GET(delete_url);

			return new ResponseEntity<>("push script created", HttpStatus.OK);
		}

	}

	@GetMapping("/runscript/{proj_id}/{count}/{workflow_json_id}/{current_state}")
	public ResponseEntity<?> runscript(@PathVariable Integer proj_id, @PathVariable Integer count,
			@PathVariable Integer workflow_json_id, @PathVariable Integer current_state) {

		String filepath = projectPath + File.separator + "cns-portal/code-extractor/builders" + File.separator + proj_id
				+ File.separator + "index";

		String index_file = "index.sh";

		String gitpush_file = "git_push_exec.sh";

		String script_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
				+ "/entityBuilder/runScript?filepath=" + filepath + "&filename=";
		int status_code = 500;

		String responsebody = "null_body";

		try {

			ResponseEntity<Object> index = GET(script_url + index_file);

			if (index.getStatusCodeValue() == 200) {
				log.info("index file run");

				ResponseEntity<Object> gitpush = GET(script_url + gitpush_file);

				if (gitpush.getStatusCodeValue() == 200) {
					log.info("gitpush file run");

				}

			}

			if (index.getBody() != null) {

				responsebody = index.getBody().toString().replace("{", "").replace("}", "");
			}

			status_code = index.getStatusCodeValue();
			current_state++;

			String delete_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
					+ "/entityBuilder/delete_folder/" + proj_id + "/" + count + "/" + workflow_json_id + "/"
					+ current_state;

			// for update workflow json
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + delete_url + "&resp_body="
					+ responsebody;
			GET(update_url);

			// run next job
//				GET(delete_url);

		} catch (RestClientException e) {

			responsebody = e.toString().replace("{", "").replace("}", "");
			System.out.println(responsebody);
			String delete_url = "no url";

			System.out.println("rest client error");
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + delete_url + "&resp_body="
					+ responsebody;
			GET(update_url);

		}

		if (status_code == 500) {
			return new ResponseEntity<>("file not run", HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<>("file run", HttpStatus.ACCEPTED);

		}

	}

	@GetMapping(value = "/delete_folder/{proj_id}/{count}/{workflow_json_id}/{current_state}")
	public ResponseEntity<?> delete_folder(@PathVariable Integer proj_id, @PathVariable int count,
			@PathVariable int workflow_json_id, @PathVariable int current_state) throws IOException {

		int status_code = 500;
		String responsebody = "delete folder";

		String filepath = projectPath + File.separator + "cns-portal/code-extractor/builders" + File.separator
				+ proj_id;

		File index = new File(filepath);

		FileUtils.deleteDirectory(index);

		if (!index.exists()) {

			status_code = 200;

			current_state++;

			String job_url = "no url";

			// for update workflow json
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + job_url + "&resp_body=" + responsebody;
			GET(update_url);
			return new ResponseEntity<>("folder deleted", HttpStatus.OK);

		} else {

			String resp_body = "folder not deleted";
			System.out.println(resp_body);

			System.out.println(" error");

			String job_url = "no url";

			// for update workflow json
			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + job_url + "&resp_body=" + resp_body;
			GET(update_url);
			return new ResponseEntity<>("folder not deleted ! please give right path", HttpStatus.BAD_REQUEST);

		}

	}

	// WIREFRAME BUILD BY HEADER ID
	@GetMapping(value = "/json/{header_id}")
	public ResponseEntity<?> createbyjson(@PathVariable Integer header_id) throws IOException {

		int j = 1;
		String wf_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/wfline/" + header_id;
		System.out.println("wf url " + wf_url + "\n");

		ResponseEntity<Object> wf_get = GET(wf_url);
		ObjectMapper mapper = new ObjectMapper();
		JsonParser parser = new JsonParser();

		String Wf_str = mapper.writeValueAsString(wf_get.getBody());
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wf_get.getBody()));// print
		JsonElement wf_element = parser.parse(Wf_str);

		JsonObject wf_obj = wf_element.getAsJsonObject();
		String model = wf_obj.get("model").getAsString();

//		Optional<Rn_wf_lines_3> wireframe = repo.findheader(header_id);

		List<String> tablename = new ArrayList<>();
		HashMap<String, String> entityname = new HashMap<>();

		JsonElement element = parser.parse(model);
		JsonObject jsonObject = element.getAsJsonObject();

		JsonElement name = jsonObject.get("name");
		System.out.println(name);
		tablename.add(name.getAsString());

		JsonElement desc = jsonObject.get("description");
		System.out.println(desc);

		JsonElement element2 = jsonObject.get("attributes");
		System.out.println(element2);

		JsonArray jsonArray = element2.getAsJsonArray();
		System.out.println(jsonArray);

		for (JsonElement ar : jsonArray) {

			JsonObject obj = ar.getAsJsonObject();

			JsonElement type = obj.get("type");
			System.out.println(type);

			JsonElement description = obj.get("description");
			System.out.println(description);

			JsonElement placeholder = obj.get("placeholder");

			String field_value = obj.get("label").getAsString();
			System.out.println(field_value);

			String datatype = obj.get("datatype").getAsString();
			entityname.put(field_value, datatype);

		}

		Date d = new Date();
//		String addString = "_"+d.getTime();
		String addString = "_t";

		// CALL BACKEND
		System.out.println("call backend " + "\n");
		backendservice.buildbackend(94, tablename, entityname, addString, j, "aa", "project_name");

		// call deploy
		script_serviceMaking.CreateFiles(94, addString);

		return new ResponseEntity<>(new EntityResponse("entity created"), HttpStatus.CREATED);

	}

	// INSERT IN JOB PRO TO DELETE FOLDER
	public void insertin_jobpro_delete(Integer prj_id, String filepath, Integer w_id, Integer current_state,
			Integer count) throws JsonProcessingException {

		Map<String, String> jobdata = new HashMap<String, String>();
//				jobdata.put("parameters", builder.toString());
		jobdata.put("url", "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
				+ "/sureops/file_delete/delete_folder?filepath=" + filepath);
		jobdata.put("method", "GET");
		jobdata.put("connection_name", "jobprtal");
		jobdata.put("createdBy", "2022");
		jobdata.put("updatedBy", "2022");
		jobdata.put("job_type", "deletefolder");
		jobdata.put("ref", prj_id.toString());
		System.out.println(jobdata);

		RestTemplate restTemplate = new RestTemplate();
		String jobprourl = "http://" + Constant.LOCAL_HOST + ":" + Constant.JOBPRO_PORT_8087 + "/jobpro/pipiline";
		HttpHeaders headers = getHeaders();
		HttpEntity<Object> request = new HttpEntity<Object>(jobdata, headers);
		ResponseEntity<Object> res1 = restTemplate.postForEntity(jobprourl, request, Object.class);
		if (res1.getStatusCodeValue() == 200) {
			System.out.println(" for delete folder data inserted in job pro");

			String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + w_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + 200;
			GET(update_url);
		}
	}

	// CURRENTLY NOT IN USE
	// BUILD ALL BY PROJECT ID
//	@GetMapping(value = "/file_builder/{proj_id}/{count}/{workflow_json_id}/{current_state}")
	public ResponseEntity<?> BuildByProject(@PathVariable Integer proj_id, @PathVariable int count,
			@PathVariable int workflow_json_id, @PathVariable int current_state) throws IOException {

		int i = 0;
		int j = 1;
		List<String> tablename = new ArrayList<>();
		HashMap<String, String> entityname = new HashMap<>();

		String prj_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/getproject/" + proj_id;

		System.out.println(prj_url + "\n");
		// get project
		ResponseEntity<Object> prj = GET(prj_url);
		Object prj_body = prj.getBody();

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(prj_body);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();
		String prj_prefix = obj.get("projectPrefix").getAsString();
		String prj_name = obj.get("projectName").getAsString();

		JsonElement modules = obj.get("modules");

		JsonArray module_list = modules.getAsJsonArray();

		if (!modules.isJsonNull()) {

			for (JsonElement modue : module_list) {
				JsonObject module = modue.getAsJsonObject();
				JsonElement fb_header = module.get("rn_fb_headers");
				JsonArray rn_fb_headers = fb_header.getAsJsonArray();
				if (!rn_fb_headers.isJsonNull()) {

					for (JsonElement header : rn_fb_headers) {

						JsonObject header_obj = header.getAsJsonObject();
						boolean build = header_obj.get("build").getAsBoolean();
						String header_id = header_obj.get("id").toString();
//				if (!header.isBuild()) {
//					if (!build) {

						String wf_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
								+ "/token/fnd1/callingsureops/wfline/" + header_id;
						System.out.println("wf url " + wf_url + "\n");
						try {

							ResponseEntity<Object> wf_get = GET(wf_url);

							if (wf_get.hasBody()) {

								String Wf_str = mapper.writeValueAsString(wf_get.getBody());
								JsonElement wf_element = parser.parse(Wf_str);

								JsonObject wf_obj = wf_element.getAsJsonObject();
								String model = wf_obj.get("model").getAsString();

//					Optional<Rn_wf_lines_3> wireframe = repo.findheader(header.getId());
								i++;

								JsonElement model_element = parser.parse(model);
								JsonObject jsonObject = model_element.getAsJsonObject();

								JsonElement name = jsonObject.get("name");
								tablename.add(name.getAsString());

								JsonElement desc = jsonObject.get("description");

								JsonElement element2 = jsonObject.get("attributes");
//							System.out.println(element2);

								JsonArray jsonArray = element2.getAsJsonArray();
								System.out.println(jsonArray);

								for (JsonElement ar : jsonArray) {

									JsonObject obj1 = ar.getAsJsonObject();

									JsonElement type = obj1.get("type");
//								System.out.println(type);

									JsonElement description = obj1.get("description");
//								System.out.println(description);

									JsonElement placeholder = obj1.get("placeholder");

									String field_value = obj1.get("label").getAsString();
									System.out.println(field_value);

									String datatype = obj1.get("datatype").getAsString();
									entityname.put(field_value, datatype);

								}

								Date d = new Date();
								String addString = "_t";

								// CALL BACKEND
								System.out.println("call backend with " + tablename + "\n");
								backendservice.buildbackend(proj_id, tablename, entityname, addString, j, prj_prefix,
										prj_name);

								tablename.removeAll(tablename);
								entityname.clear();

//							if (buildbackend) {
//								header.setBuild(true);
//								header_Repository.save(header);
//							}
								j++;
//							

							}
						} catch (Exception e) {
						}
//				}

					}
				} else {
					return new ResponseEntity<>(new EntityResponse("header is empty"), HttpStatus.BAD_REQUEST);

				}

			}
		} else {
			return new ResponseEntity<>(new EntityResponse("modules is empty"), HttpStatus.BAD_REQUEST);

		}

		current_state++;
		String push_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
				+ "/entityBuilder/push_script/" + proj_id + "/" + count + "/" + workflow_json_id + "/" + current_state;

		String update_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.SUREOPS_PORT_9090
				+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
				+ current_state + "&status=" + 200 + "&job_url=" + push_url + "&resp_body=" + "all file created";
		GET(update_url);

		return new ResponseEntity<>(new EntityResponse(i + " wireframe build"), HttpStatus.CREATED);
	}

	public ResponseEntity<Object> GET(String get) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> u = restTemplate.getForEntity(get, Object.class);

		return u;

	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
