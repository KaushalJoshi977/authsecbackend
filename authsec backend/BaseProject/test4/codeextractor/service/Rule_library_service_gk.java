package com.realnet.codeextractor.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class Rule_library_service_gk {

	public String rule(String path, String start, String end, String replaceWith) throws ParseException, IOException {

		StringBuilder frontend = new StringBuilder();

		File staticFile = new File(path);
		String staticFileName = staticFile.getName();

		// System.out.println("========" + staticFileName + "=============");

		String fileString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
		String fileType = FilenameUtils.getExtension(staticFileName);

		// don't check empty file for replacement..
		if (!fileString.isEmpty()) {

			// RULE APPLY
			if (end.isEmpty()) {
				String f_string = stringReplacewithout_endstring(fileString, start, replaceWith, fileType);
				System.out.println(f_string.toString());
			}else {
				String finalString = stringReplace(fileString, start, end, replaceWith, fileType);
				System.out.println(finalString.toString());

			}
			
//			BufferedWriter bw = new BufferedWriter(new FileWriter(staticFile, false)); // replaced
//																						// string
//			bw.write(finalString);
//			bw.close();

		}
		
		return frontend.toString();
	}
    
	//WHEN END STRING IS PRESENT
	public static String stringReplace(String str, String start, String end, String replaceWith, String file_type) {
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
//					String data = str.substring(0, i+start.length())  +" "+ replaceWith ;
//					String temp = str.substring(j-end.length() + end.length());

				String data = str.substring(0, i) + start + " " + replaceWith + " " + end;
				String temp = str.substring(j + end.length());
				data += temp;
				str = data;
				i = str.indexOf(start, i + replaceWith.length() + end.length() + 1);
			} else {
				break;
			}
		}

		return str;
	}
	
	//WHEN END STRING IS NOT PERSENT
	public static String stringReplacewithout_endstring(String str, String start, String replaceWith, String file_type) {
		int i = str.indexOf(start);
		while (i != -1) {
			
				/*
				 * @Include starting and ending string String data = str.substring(0, i +
				 * start.length()) + "\n" + replaceWith + "\n"; String temp = str.substring(j);
				 * 
				 * @Not Include starting and ending string String data = str.substring(0, i) +
				 * "\n" + replaceWith + "\n"; String temp = str.substring(j + end.length());
				 */
//					String data = str.substring(0, i+start.length())  +" "+ replaceWith ;
//					String temp = str.substring(j-end.length() + end.length());

				String data = str.substring(0, i) + start + " " + replaceWith ;
				str = data;
				i = str.indexOf(start, i + replaceWith.length() );
			
		}

		return str;
	}


}
