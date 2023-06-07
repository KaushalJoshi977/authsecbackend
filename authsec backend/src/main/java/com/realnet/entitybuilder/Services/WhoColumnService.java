package com.realnet.entitybuilder.Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.realnet.entitybuilder.response.EntityResponse;

@Service
public class WhoColumnService {

	@Value("${projectPath}")
	private String projectPath;

//	@GetMapping(value = "/whocolumn/{proj_id}/{prj_name}/{repo_name}/{table_name}")
	public ResponseEntity<?> createwhocolumn(Integer proj_id, String prj_name, String repo_name) throws IOException {

		String project_name = repo_name;
		String object_name = "who_column";

		FileWriter fw = null;
		BufferedWriter bw = null;

		String Abc1 = object_name + ".java";

		StringBuilder colum = new StringBuilder();
		colum.append("package com.realnet." + repo_name + ".Entity;" + "\r\n" + " import lombok.*;" + "\r\n"
				+ " import javax.persistence.*;" + "\r\n" + " import java.time.LocalDateTime;" + "\r\n"
				+ " import java.util.*;" + "\n" + "import java.io.Serializable;\n" + "import java.util.Date;\n"
				+ "import javax.persistence.Column;\n" + "import javax.persistence.EntityListeners;\n"
				+ "import javax.persistence.MappedSuperclass;\n" + "import javax.persistence.Temporal;\n"
				+ "import javax.persistence.TemporalType;\n"
				+ "import org.springframework.data.annotation.CreatedDate;\n"
				+ "import org.springframework.data.annotation.LastModifiedDate;\n"
				+ "import org.springframework.data.jpa.domain.support.AuditingEntityListener;" + "\r\n" + "" + "\r\n"
				+ " " + "\r\n" + " @Data" + "\r\n" + " public class    " + object_name + "{ " + "\r\n" + "");

		colum.append("private static final long serialVersionUID = 1L;\n" + "\n"
				+ "	@Temporal(TemporalType.TIMESTAMP)\n"
				+ "	@Column(name = \"CREATED_AT\", nullable = false, updatable = false)\n" + "	@CreatedDate\n"
				+ "	private Date createdAt;\n" + "\n"
				+ "	// @JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\") private LocalDateTime createdAt;\n" + "\n" + "\n"
				+ "	@Column(name = \"CREATED_BY\", updatable = false)\n" + "	private Long createdBy;\n" + "\n"
				+ "	@Column(name = \"UPDATED_BY\")\n" + "	private Long updatedBy;\n" + "	\n"
				+ "	@Temporal(TemporalType.TIMESTAMP)\n" + "	@Column(name = \"UPDATED_AT\", nullable = false)\n"
				+ "	@LastModifiedDate\n" + "	private Date updatedAt;\n" + "\n"
				+ "	@Column(name = \"ACCOUNT_ID\")\n" + "	private Long accountId;\n" + "");

		colum.append("\n}\n");

		File Abc1File = new File(projectPath + "/cns-portal/code-extractor/builders/" + proj_id + "/" + proj_id + "/"
				+ project_name + "/Entity/" + Abc1);
		System.out.println("Directory name = " + Abc1File);
		File Abc1FileParentDir = new File(Abc1File.getParent());
		if (!Abc1FileParentDir.exists()) {
			Abc1FileParentDir.mkdirs();
		}
		if (!Abc1File.exists()) {
			Abc1File.createNewFile();
		}
		fw = new FileWriter(Abc1File.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		bw.write(colum.toString());
		bw.close();
		fw.close();
		return new ResponseEntity<>(new EntityResponse("who column created"), HttpStatus.CREATED);
	}
}
