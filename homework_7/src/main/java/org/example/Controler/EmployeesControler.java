package org.example.Controler;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.Models.Employees;
import org.example.dao.EmployeeDAO;
import org.example.dto.EmployeesDto;
import org.example.dto.EmployeesFilterDto;
import org.example.exceptions.DataNotFoundException;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/Employees")
public class EmployeesControler {

    EmployeeDAO dao = new EmployeeDAO();
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;

// normel one
//    @GET
//    public ArrayList<Employees> getAllJOB() {
//
//        try {
//            return dao.selectAllEmployee();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

// after filter
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

    // after links


// bisic one
//    @GET
//    @Path("{employeeId}")
//    public Employees getEmployees(@PathParam("employeeId") int employeeId) {
//
//        try {
//            return dao.selectEmployees(employeeId);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    // after links
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllEmployees(@BeanParam EmployeesFilterDto filter) throws SQLException, ClassNotFoundException {

        GenericEntity<ArrayList<Employees>>  employee = new GenericEntity<ArrayList<Employees>> (dao.selectAllEmployee(filter)){};
        try {
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(employee)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            }

            return Response
                    .ok(employee, MediaType.APPLICATION_JSON)
                    .build();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{employeeId}")
    public Employees getEmployee(@PathParam("employeeId") int employees_id) {
        try {
            Employees employees = dao.selectEmployees(employees_id);

            if (employees == null) {
                throw new DataNotFoundException("employee with ID " + employees_id + " not found");
            }
            EmployeesFilterDto dto = new EmployeesFilterDto();
//            dto.setJobId(employees.getJob_id());
//            dto.setMaxSalary(employees.getMax_salary());
//            dto.setMinSalary(employees.getMin_salary());
//            dto.setJob_title(employees.getJob_title());
//            addLink(dto);

//            return Response.ok(dto).build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void addLinks(EmployeesDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI empsUri = uriInfo.getAbsolutePathBuilder()
                .path(EmployeesControler.class)
                .build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(empsUri.toString(), "employees");
    }

// bisic one
//    @DELETE
//    @Path("{employeeId}")
//    public void deleteEmployees(@PathParam("employeeId") int employeeId) {
//
//        try {
//            dao.setDeleteEmployee(employeeId);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @DELETE
    @Path("{employeeId}")
    public void deleteJob(@PathParam("employeeId") int empId) {

        try {
            dao.setDeleteEmployee(empId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting employee with ID " + empId, e);
        }
    }
// bisic one
//    @POST
//    public void insertEmployees(Employees dept) {
//
//        try {
//            dao.setInsertEmployees(dept);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
// after links
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response insertJob(Employees employee) {

        try {
            dao.setInsertEmployees(employee);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(employee.getEmployee_id())).build();
            return Response.created(uri).build();
        } catch (Exception e) {
            throw new RuntimeException("Error inserting Employee", e);
        }
    }
// bisic one
//    @PUT
//    @Path("{employeeId}")
//    public void updateEmployees(@PathParam("employeeId") int employeeId, Employees dept) {
//
//        try {
//            dept.setJob_id(employeeId);
//            dao.setUpdateEmployees(dept);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    // after links
    @PUT
    @Path("{employeeId}")
    public void updateJob(@PathParam("jobId") int jobId, Employees employee) {

        try {
            employee.setEmployee_id(jobId);
            dao.setUpdateEmployees(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error updating job with ID " + jobId, e);
        }
    }
}
