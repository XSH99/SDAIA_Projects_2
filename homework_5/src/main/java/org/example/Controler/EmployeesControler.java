package org.example.Controler;

import jakarta.ws.rs.*;
import org.example.Models.Employees;
import org.example.dao.EmployeeDAO;
//import org.example.models.Job;

import java.util.ArrayList;

import jakarta.ws.rs.*;

import java.util.ArrayList;
@Path("/Employee")
public class EmployeesControler {

    EmployeeDAO dao = new EmployeeDAO();

    @GET
    public ArrayList<Employees> getAllJOB() {

        try {
            return dao.selectAllEmployee();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{jobId}")
    public Employees getEmployees(@PathParam("jobId") int deptId) {

        try {
            return dao.selectEmployees(deptId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("{deptId}")
    public void deleteEmployees(@PathParam("deptId") int deptId) {

        try {
            dao.setDeleteEmployee(deptId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    public void insertEmployees(Employees dept) {

        try {
            dao.setInsertEmployees(dept);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{deptId}")
    public void updateEmployees(@PathParam("deptId") int deptId, Employees dept) {

        try {
            dept.setJob_id(deptId);
            dao.setUpdateEmployees(dept);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
