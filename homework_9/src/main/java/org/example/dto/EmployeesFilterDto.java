package org.example.dto;

import jakarta.ws.rs.QueryParam;

public class EmployeesFilterDto {

    private @QueryParam("hire_date") String hire_date;
    private @QueryParam("job_id") Integer job_id;
    private @QueryParam("limit") Integer limit;
    private @QueryParam("offset") int offset;



    public Integer getLimit() {
        return limit;
    }

    public String getHire_date() {
        return hire_date;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public int getOffset() {
        return offset;
    }


    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setEmployees_id(int employeeId) {
    }

    public void setHire_date(String hireDate) {
    }



}




