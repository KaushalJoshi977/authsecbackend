package com.realnet.bugTracker.Service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.bugTracker.Entity.BugTracker;
import com.realnet.bugTracker.Repository.BugTrackerRepository;

@Service
public class BugTrackerService {
	
	@Autowired
	private BugTrackerRepository bugTrackerRepository;

    public BugTracker createIssue(BugTracker tracker) {
        return bugTrackerRepository.save(tracker);
    }

    public BugTracker getIssueById(Long id) {
        return bugTrackerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Issue not found"));
    }

    public List<BugTracker> getAllIssues() {
        return bugTrackerRepository.findAll();
    }

    public BugTracker updateIssue(Long id, BugTracker tracker) {
        BugTracker existingIssue = getIssueById(id);
        existingIssue.setType(tracker.getType());
        existingIssue.setKey_no(tracker.getKey_no());
        existingIssue.setSummary(tracker.getSummary());
        existingIssue.setAssignee(tracker.getAssignee());
        existingIssue.setReporter(tracker.getReporter());
        existingIssue.setSelect_project(tracker.getSelect_project());
        existingIssue.setTitle(tracker.getTitle());
        existingIssue.setSelect_status(tracker.getSelect_status());
        existingIssue.setSelect_priority(tracker.getSelect_priority());
        existingIssue.setSteps(tracker.getSteps());
        existingIssue.setDescription(tracker.getDescription());
        existingIssue.setFileName(tracker.getFileName());
        existingIssue.setFileType(tracker.getFileType());
        existingIssue.setData(tracker.getData());

        return bugTrackerRepository.save(existingIssue);
    }

    
    
    

    public void deleteIssue(Long id) {
    	bugTrackerRepository.deleteById(id);
    }

}
