"package com.realnet."+repo_name+".Repository;" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.data.jpa.repository.JpaRepository;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.stereotype.Repository;" + "\r\n" + 
" " + "\r\n" + 
"" + "\r\n" + 
"import com.realnet."+repo_name+".Entity."+table_name+";" + "\r\n" + 
"" + "\r\n" + 
"@Repository" + "\r\n" + 
"public interface  "+table_name+"Repository  extends  JpaRepository<"+table_name+", Long>  { " + "\r\n" + 
"}" 