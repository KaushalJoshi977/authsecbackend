package com.realnet.fnd.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Rn_Ext_Fields;
import com.realnet.fnd.repository.ExtFieldRepository;
import com.realnet.fnd.repository.Rn_LookUpRepository;
import com.realnet.utils.WireFrameConstant;

@Service
public class ExtFieldServiceImpl implements ExtFieldService {

	@Value("${angularProjectPath}")
	private String angularProjectPath;
	
	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private ExtFieldRepository extFieldRepository;

	@Autowired
	Rn_LookUpRepository lookUpRepository;

	@Override
	public List<Rn_Ext_Fields> getAll() {
		return extFieldRepository.findAll();
	}

//	@Override
//	public Page<Rn_Ext_Fields> getAll(Pageable page) {
//		return extFieldRepository.findAll(page);
//	}

	@Override
	public Rn_Ext_Fields getById(int id) {
		Rn_Ext_Fields rn_ext_fields = extFieldRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Extension Field not found :: " + id));
		return rn_ext_fields;
	}

	@Override
	public Rn_Ext_Fields save(Rn_Ext_Fields rn_ext_fields) {
		Rn_Ext_Fields savedRn_Ext_Fields = extFieldRepository.save(rn_ext_fields);
		return savedRn_Ext_Fields;
	}

	@Override
	public Rn_Ext_Fields updateById(int id, Rn_Ext_Fields extensionRequest) {
		Rn_Ext_Fields old_ext_field = extFieldRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Extension Field not found :: " + id));

		old_ext_field.setField_name(extensionRequest.getField_name());
		old_ext_field.setMapping(extensionRequest.getMapping());
		old_ext_field.setData_type(extensionRequest.getData_type());
		old_ext_field.setType(extensionRequest.getType());
		old_ext_field.setActive(extensionRequest.isActive());
		final Rn_Ext_Fields updated_ext_field = extFieldRepository.save(old_ext_field);
		return updated_ext_field;
	}

	@Override
	public boolean deleteById(int id) {
		if (!extFieldRepository.existsById(id)) {
			throw new ResourceNotFoundException("Extension Field not found :: " + id);
		}
		Rn_Ext_Fields rn_ext_fields = extFieldRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Extension Field not found :: " + id));
		extFieldRepository.delete(rn_ext_fields);
		return true;
	}

//	@Override
//	public List<String> getLookupValues() {
//		return lookUpRepository.findLookupValues();
//	}
//
//	@Override
//	public List<String> getDataTypeValues() {
//		return lookUpRepository.findDataTypeValues();
//	}

	@Override
	public void buildExtensionByFormCode(String acc_id, String f_code) {
		List<Rn_Ext_Fields> extensions = extFieldRepository.getExtensionFieldByFormCode(acc_id, f_code);

		if (extensions == null || extensions.isEmpty()) {
			throw new ResourceNotFoundException("Extension Fields Not Found");
		}
		StringBuilder extension_grid_form = new StringBuilder();
		StringBuilder extension_entry_form = new StringBuilder();
		extension_entry_form.append("<table [formGroup]=\"extensionForm\">\n");
		// extension_code.append("<table [formGroup]=\"extensionForm\">\n" + " <div
		// formGroupName=\"extensions\">\n");

		for (Rn_Ext_Fields extension : extensions) {
			// String form_code = extension.getForm_code();
			String type = extension.getType(); // ho, hl
			String data_type = extension.getData_type();
			String mapping = extension.getMapping();
			String field_name = extension.getField_name();

			if (WireFrameConstant.DT_TEXTFIELD.equals(data_type)) {
				extension_entry_form.append(" <tr>\r\n" + "        <td style=\"width:125px;\">" + field_name
						+ " </td>\r\n"
						+ "        <td><input colspan=\"2\" style=\"width:180px\" type=\"text\" formControlName=\""
						+ mapping + "\" placeholder=\"Enter " + field_name + "\"></td>\r\n" + "    </tr>\n");
			}

			if (WireFrameConstant.DT_DATE.equals(data_type)) {
				extension_entry_form.append(" <tr>\r\n" + "        <td style=\"width:125px;\">" + field_name
						+ " </td>\r\n"
						+ "        <td><input colspan=\"2\" style=\"width:180px\" type=\"date\" formControlName=\""
						+ mapping + "\" placeholder=\"Enter " + field_name + "\"></td>\r\n" + "    </tr>\n");
			}

			if (WireFrameConstant.DT_LONGTEXT.equals(data_type)) {
				extension_entry_form.append(" <tr>\r\n" + "        <td style=\"width:125px;\">" + field_name
						+ " </td>\r\n"
						+ "        <td><textarea rows=\"4\" cols=\"50\" colspan=\"2\" style=\"width:180px\" formControlName=\""
						+ mapping + "\">" + "Enter " + field_name + "</textarea></td>\r\n" + "    </tr>\n");
			}

			if (WireFrameConstant.FIELD_CHECKBOX.equals(data_type)) {
				extension_entry_form.append(" <tr>\r\n" + "        <td style=\"width:125px;\">" + field_name
						+ " </td>\r\n"
						+ "        <td><input colspan=\"2\" style=\"width:180px\" type=\"checkbox\" formControlName=\""
						+ mapping + "\"></td>\r\n" + "    </tr>\n");
			}

//			if (TypeConstants.FIELD_CHECKBOX.equals(data_type)) {
//				extension_entry_form.append(" <tr>\r\n" + "        <td style=\"width:125px;\">" + field_name
//						+ " </td>\r\n"
//						+ "        <td><input colspan=\"2\" style=\"width:180px\" type=\"radio\" formControlName=\""
//						+ mapping + "\"></td>\r\n" + "    </tr>\n");
//			}
			if (WireFrameConstant.FIELD_AUTOCOMPLETE.equals(data_type)) {
				extension_entry_form.append(" <tr>\r\n" + "        <td style=\"width:125px;\">" + field_name
						+ " </td>\r\n"
						+ "        <td><input colspan=\"2\" style=\"width:180px\" type=\"text\" formControlName=\""
						+ mapping + "\" autocomplete=\"on\"></td>\r\n" + "    </tr>\n");
			}

			// extension grid-view code
			extension_grid_form.append("{prop: \"" + mapping + "\", name: \"" + field_name + "\", width: 200},\n");

			// extension read-only code

			// extension update code

		}
		// extension_code.append("\n</div>\n</table>");
		extension_entry_form.append("\n</table>");

		FileWriter fw = null;
		BufferedWriter bw = null;
		try {

			// ENTRY FORM
//			String ngExtEntryPath = angularProjectPath
//					+ "/src/app/pages/university/teacher/extensions/add-ext/teacher-add-extension.component.html";
			
			String ngExtEntryPath = projectPath
					+ "/src/main/resources/pages/teacher-add-extension.component.html";
			File ngExtEntryFile = new File(ngExtEntryPath);
			if (!ngExtEntryFile.exists()) {
				ngExtEntryFile.createNewFile();
			}
			fw = new FileWriter(ngExtEntryFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(extension_entry_form.toString());
			bw.close();

			// GRID VIEW FORM
//			final String start = "// EXTENSION COLUMN START";
//			final String end = "// EXTENSION COLUMN END";
//			String replaceWith = extension_grid_form.toString();
//			String ngExtGridPath = angularProjectPath
//					+ "/src/app/pages/university/teacher/all/all-teacher.component.ts";
//			File ngExtGridFile = new File(ngExtGridPath);
//			String fileString = FileUtils.readFileToString(ngExtGridFile, StandardCharsets.UTF_8);
//			String finalString = stringReplace(fileString, start, end, replaceWith);
//			
//			bw = new BufferedWriter(new FileWriter(ngExtGridFile, false)); // replaced string
//			bw.write(finalString);
//			bw.close();
			
			// UPDATE FORM
			
			// READ-ONLY FORM

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String stringReplace(String str, String start, String end, String replaceWith) {
		int i = str.indexOf(start);
		while (i != -1) {
			int j = str.indexOf(end, i + 1);
			if (j != -1) {
				/*
				 * @Include starting and ending string String data = str.substring(0, i +
				 * start.length()) + "\n" + replaceWith + "\n"; String temp = str.substring(j);
				 * 
				 * @Not Include starting and ending string String data = str.substring(0, i) +
				 * "\n" + replaceWith + "\n"; String temp = str.substring(j + end.length());
				 */
				String data = str.substring(0, i + start.length()) + "\n" + replaceWith + "\n";
				String temp = str.substring(j);
				data += temp;
				str = data;
				i = str.indexOf(start, i + replaceWith.length() + end.length() + 1);
			} else {
				break;
			}
		}
		return str;
	}
}
