package com.campuschatbot.model;

import java.util.ArrayList;
import java.util.List;

public class UserSession {
    private String state; // e.g., "MAIN", "COURSES", "COURSE_LIST", "LIBRARY_BOOKS", "SELECT_SUBJECTS"
    private List<String> selectedSubjects; // For multiple subject selections

    public UserSession() {
        this.state = "MAIN";
        this.selectedSubjects = new ArrayList<>();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getSelectedSubjects() {
        return selectedSubjects;
    }

    public void addSubject(String subject) {
        this.selectedSubjects.add(subject);
    }

    public void clearSubjects() {
        this.selectedSubjects.clear();
    }
}