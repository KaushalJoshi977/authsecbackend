"package com.realnet.bugTracker.Repository;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.data.jpa.repository.JpaRepository;" + "\r\n" + 
"import org.springframework.stereotype.Repository;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.bugTracker.Entity.BugTracker;" + "\r\n" + 
"" + "\r\n" + 
"@Repository" + "\r\n" + 
"public interface BugTrackerRepository extends JpaRepository<BugTracker, Long> {" + "\r\n" + 
"	" + "\r\n" + 
"}" 