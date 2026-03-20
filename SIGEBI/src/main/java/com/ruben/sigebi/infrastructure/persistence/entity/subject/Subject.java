package com.ruben.sigebi.infrastructure.persistence.entity.subject;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String subjectName;

    public Subject() {
    }
    public Subject(UUID id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
