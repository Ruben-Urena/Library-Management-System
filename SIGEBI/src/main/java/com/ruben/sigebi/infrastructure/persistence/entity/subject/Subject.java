package com.ruben.sigebi.infrastructure.persistence.entity.subject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject {
    @id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String subjectName;

    public Subject() {
    }
    public Subject(String id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
