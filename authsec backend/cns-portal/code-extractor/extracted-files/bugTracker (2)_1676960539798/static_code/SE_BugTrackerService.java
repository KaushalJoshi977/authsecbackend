"package com.realnet.bugTracker.Service;" + "\r\n" + 
"" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import javax.persistence.EntityNotFoundException;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"import org.springframework.stereotype.Service;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.bugTracker.Entity.BugTracker;" + "\r\n" + 
"import com.realnet.bugTracker.Repository.BugTrackerRepository;" + "\r\n" + 
"" + "\r\n" + 
"@Service" + "\r\n" + 
"public class BugTrackerService {" + "\r\n" + 
"	" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private BugTrackerRepository bugTrackerRepository;" + "\r\n" + 
"" + "\r\n" + 
"    public BugTracker createIssue(BugTracker tracker) {" + "\r\n" + 
"        return bugTrackerRepository.save(tracker);" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    public BugTracker getIssueById(Long id) {" + "\r\n" + 
"        return bugTrackerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(\"Issue not found\"));" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    public List<BugTracker> getAllIssues() {" + "\r\n" + 
"        return bugTrackerRepository.findAll();" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    public BugTracker updateIssue(Long id, BugTracker tracker) {" + "\r\n" + 
"        BugTracker existingIssue = getIssueById(id);" + "\r\n" + 
"        existingIssue.setType(tracker.getType());" + "\r\n" + 
"        existingIssue.setKey_no(tracker.getKey_no());" + "\r\n" + 
"        existingIssue.setSummary(tracker.getSummary());" + "\r\n" + 
"        existingIssue.setAssignee(tracker.getAssignee());" + "\r\n" + 
"        existingIssue.setReporter(tracker.getReporter());" + "\r\n" + 
"        existingIssue.setSelect_project(tracker.getSelect_project());" + "\r\n" + 
"        existingIssue.setTitle(tracker.getTitle());" + "\r\n" + 
"        existingIssue.setSelect_status(tracker.getSelect_status());" + "\r\n" + 
"        existingIssue.setSelect_priority(tracker.getSelect_priority());" + "\r\n" + 
"        existingIssue.setSteps(tracker.getSteps());" + "\r\n" + 
"        existingIssue.setDescription(tracker.getDescription());" + "\r\n" + 
"        existingIssue.setFileName(tracker.getFileName());" + "\r\n" + 
"        existingIssue.setFileType(tracker.getFileType());" + "\r\n" + 
"        existingIssue.setData(tracker.getData());" + "\r\n" + 
"" + "\r\n" + 
"        return bugTrackerRepository.save(existingIssue);" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
"" + "\r\n" + 
"    public void deleteIssue(Long id) {" + "\r\n" + 
"    	bugTrackerRepository.deleteById(id);" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"}" 