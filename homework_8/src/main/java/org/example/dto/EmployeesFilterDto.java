package org.example.dto;

import jakarta.ws.rs.QueryParam;

public class EmployeesFilterDto {

    private @QueryParam("year") String year;
    private @QueryParam("id") Integer id;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}




