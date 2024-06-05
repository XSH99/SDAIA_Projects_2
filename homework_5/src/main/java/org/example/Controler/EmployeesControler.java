package org.example.Controler;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.Models.Employees;
import org.example.dao.EmployeeDAO;
import org.example.dto.EmployeesFilterDto;

import java.util.ArrayList;

@Path("/Employees")
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


//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public ArrayList<Employees> getAllEmployees(
////            @QueryParam("salary") Double min_salary,
////            @QueryParam("limit") Integer limit,
////            @QueryParam("offset") int offset
//            @BeanParam EmployeesFilterDto filter
//    ) {
//        try {
//            return dao.selectAllEmployee(filter);
//            // return dao.getAllEmployees(min_salary, limit, offset);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @GET
    @Path("{employeeId}")
    public Employees getEmployees(@PathParam("employeeId") int employeeId) {

        try {
            return dao.selectEmployees(employeeId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @DELETE
    @Path("{employeeId}")
    public void deleteEmployees(@PathParam("employeeId") int employeeId) {

        try {
            dao.setDeleteEmployee(employeeId);
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
    @Path("{employeeId}")
    public void updateEmployees(@PathParam("employeeId") int employeeId, Employees dept) {

        try {
            dept.setJob_id(employeeId);
            dao.setUpdateEmployees(dept);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
