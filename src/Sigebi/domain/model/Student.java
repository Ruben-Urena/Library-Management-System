package Sigebi.domain.model;

import Sigebi.domain.common.objectValue.FullName;

public class Student{
    FullName fullName;

    public Student(FullName fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Student{" + fullName +
                '}';
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }
}

