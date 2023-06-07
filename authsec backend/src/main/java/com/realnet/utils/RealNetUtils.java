package com.realnet.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RealNetUtils {

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
				String data = str.substring(0, i) + "\n" + replaceWith + "\n";
				String temp = str.substring(j + end.length());
				data += temp;
				str = data;
				i = str.indexOf(start, i + replaceWith.length() + end.length() + 1);
			} else {
				break;
			}
		}

//		if (replaced) {
//			String newStart = "";
//			String newEnd = "";
//			if(file_type.equals("html") || file_type.equals("jsp")) {
//				newStart = "<!-- bcf-fieldloop-startshere-processed -->";
//				newEnd = "<!-- bcf-fieldloop-endshere-processed -->";
//				str = str.replace(start, newStart);
//				str = str.replace(end, newEnd);
//			}
//			if(file_type.equals("java") || file_type.equals("ts") || file_type.equals("js")) {
//				newStart = "/* bcf-fieldloop-startshere-processed */";
//				newEnd = "/* bcf-fieldloop-endshere-processed */";
//				str = str.replace(start, newStart);
//				str = str.replace(end, newEnd);
//			}
//		}
		return str;
	}

	public static String stringReplace(String str, String start, String end, String replaceWith) {
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
				String data = str.substring(0, i) + "\n" + replaceWith + "\n";
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

	// file and folder present in a zip file
	public static void printFileList(String filePath) {
		FileInputStream fis = null;
		ZipInputStream zipIs = null;
		ZipEntry zEntry = null;
		try {
			fis = new FileInputStream(filePath);
			zipIs = new ZipInputStream(new BufferedInputStream(fis));
			while ((zEntry = zipIs.getNextEntry()) != null) {
				System.out.println(zEntry.getName());
			}
			zipIs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * SPRING_MVC_HIBERNATE_MYSQL (sphmy), ANGULAR_SPRING_BOOT_MYSQL (aspmy),
	 * REACT_REACT_NATIVE_MYSQL (rrnmy), REACT_REACT_NATIVE_MONGODB (rrnmo),
	 * ANGULAR_SPRING_BOOT_MONGODB (aspmo), PHP_LARAVEL_MYSQL (plvmy), MEAN (mean)
	 */
	public static String getTechnologyStackKey(String techStack) {
		if (TechnologyConstant.ANGULAR_SPRING_BOOT_MYSQL.equalsIgnoreCase(techStack)) {
			return TechnologyConstant.TechStackKey.aspmy.toString();
		} else if (TechnologyConstant.SPRING_MVC_HIBERNATE_MYSQL.equalsIgnoreCase(techStack)) {
			return TechnologyConstant.TechStackKey.sphmy.toString();
		} else if (TechnologyConstant.REACT_REACT_NATIVE_MYSQL.equalsIgnoreCase(techStack)) {
			return TechnologyConstant.TechStackKey.rrnmy.toString();
		} else if (TechnologyConstant.REACT_REACT_NATIVE_MONGODB.equalsIgnoreCase(techStack)) {
			return TechnologyConstant.TechStackKey.rrnmo.toString();
		} else if (TechnologyConstant.ANGULAR_SPRING_BOOT_MONGODB.equalsIgnoreCase(techStack)) {
			return TechnologyConstant.TechStackKey.aspmo.toString();
		} else if (TechnologyConstant.PHP_LARAVEL_MYSQL.equalsIgnoreCase(techStack)) {
			return TechnologyConstant.TechStackKey.plvmy.toString();
		} else if (TechnologyConstant.MEAN.equalsIgnoreCase(techStack)) {
			return TechnologyConstant.TechStackKey.mean.toString();
		} else {
			return null;
		}
	}
	
	public static String toFirstUpperCase(String input) {
		String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
		return output;
	}
}
