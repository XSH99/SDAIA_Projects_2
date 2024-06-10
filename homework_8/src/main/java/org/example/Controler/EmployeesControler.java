package org.example.Controler;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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



    // after links
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllEmployees(@BeanParam EmployeesFilterDto filter) throws SQLException, ClassNotFoundException {

        GenericEntity<ArrayList<Employees>>  employee;
        if(filter.getYear() != null ) {
            employee = new GenericEntity<ArrayList<Employees>> (dao.selectEmployeesByHireYear(filter.getYear())){};
        }
        else if(filter.getId() != null)
        {
            employee = new GenericEntity<ArrayList<Employees>> (dao.selectEmployeesByJobId(filter.getId())){};
        }
        else {
            employee = new GenericEntity<ArrayList<Employees>> (dao.selectAllEmployee(filter)){};
        }
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
    public Response getEmployee(@PathParam("employeeId") int employees_id) {
        try {
            Employees employees = dao.selectEmployees(employees_id);

            if (employees == null) {
                throw new DataNotFoundException("employee with ID " + employees_id + " not found");
            }
            EmployeesDto dto = new EmployeesDto();
            dto.setEmployee_id(employees.getEmployee_id());
            dto.setFirst_name(employees.getFirst_name());
            dto.setLast_name(employees.getLast_name());
            dto.setEmail(employees.getEmail());
            dto.setPhone_number(employees.getPhone_number());
            dto.setHire_date(employees.getHire_date());
            dto.setSalary(employees.getSalary());
            dto.setManager_id(employees.getManager_id());
            dto.setDepartment_id(employees.getDepartment_id());
            addLink(dto);
            return Response.ok(dto).build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


// addLink method is not exsict i have to makeit
    private void addLink(EmployeesDto dto) {
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

//    @DELETE
//    @Path("{employeeId}")
//    public void   deleteEmployee(@PathParam("employeeId") int employeeId){
//
//        try {
//            dao.setDeleteEmployee(employeeId);
//        } catch (Exception e) {
//            throw new RuntimeException("Error deleting employee with ID " + employeeId, e);
//        }
//
//    }

    @DELETE
    @Path("{employeeId}")
    public Response deleteEmployee(@PathParam("employeeId") int employeeId) {
        try {
            dao.setDeleteEmployee(employeeId);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
    public Response insertEmployee(Employees employee) {
        try {
            dao.setInsertEmployees(employee);
            URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(employee.getEmployee_id())).build();
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
    @Path("{employees_id}")
    public Response updateEmployee(@PathParam("employees_id") int employees_id, Employees employee) {
        try {
            employee.setEmployee_id(employees_id);
            dao.setUpdateEmployees(employee);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    @GET
//    public Response getEmployeesByHireYear(@QueryParam("year") Integer year, @QueryParam("id") Integer jobId) {
//        try {
//            ArrayList<Employees> employees;
//            if(year != null ) {
//                employees = dao.selectEmployeesByHireYear(year);
//            }
//            else
//            {
//                employees = dao.selectEmployeesByJobId(jobId);
//            }
//            return Response.ok(employees).build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//    @GET
//    public Response getEmployeesByHireYear(@BeanParam EmployeesFilterDto FILTER) {
//        try {
//            ArrayList<Employees> employees;
//            if(FILTER.getYear() != null ) {
//                employees = dao.selectEmployeesByHireYear(FILTER.getYear());
//            }
//            else
//            {
//                employees = dao.selectEmployeesByJobId(FILTER.getId());
//            }
//            return Response.ok(employees).build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }



}

