package com.lambton.sqlitedemo.model;

public class Employee {
    int id;
    String name, department, joiningDate;
    double salary;


    public Employee(int id, String name, String department, String joiningDate, double salary) {
        this.id = id;

        this.name = name;
        this.department = department;
        this.joiningDate = joiningDate;

        this.salary = salary;

    }

    public double getSalary() {
        return salary;
    }



    public String getName() {
        return name;
    }



    public String getJoiningDate() {
        return joiningDate;
    }




    public String getDepartment() {
        return department;
    }



    public int getId() {
        return id;
    }


}
