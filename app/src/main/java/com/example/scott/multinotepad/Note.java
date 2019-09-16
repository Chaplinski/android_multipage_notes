package com.example.scott.multinotepad;

public class Note {

    private String name;
    private String body;

    private static int ctr = 0;

    public Note() {
        this.name = "Note Name " + ctr;
        this.body = "Department " + ctr;
        ctr++;
    }

    public String getName() {
        return name;
    }

    public String setName(String newName) {
        this.name = newName + " " + ctr;
        return this.name;
    }

    public String getBody() {
        return body;
    }

    public String setBody(String newBody) {
        this.body = newBody;
        return this.body;
    }



//    @Override
//    public String toString() {
//        return name + department;
//    }
}