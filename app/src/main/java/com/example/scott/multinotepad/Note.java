package com.example.scott.multinotepad;

public class Note {

    private String name;
    private String department;

    private static int ctr = 1;

    public Note() {
        this.name = "Note Name " + ctr;
        this.department = "Department " + ctr;
        ctr++;
    }

    public String getName() {
        return name;
    }



    public String getDepartment() {
        return department;
    }

//    @Override
//    public String toString() {
//        return name + department;
//    }
}