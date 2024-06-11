package org.example.controller;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dto.JobDto;
import org.example.mappers.EmployeeMapper;
import org.example.models.Employees;
import org.example.dao.EmployeeDAO;
import org.example.dto.EmployeesDto;
import org.example.dto.EmployeesFilterDto;
import org.example.exceptions.DataNotFoundException;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/Employees")
public class EmployeesControler {

    JobDto jobDao = new JobDto();
    EmployeeDAO dao = new EmployeeDAO();
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;

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

//    @GET
//    public Response getAllEmployees(
//            @BeanParam EmployeesFilterDto filter
//    ) {
//
//        try {
//            GenericEntity<ArrayList<Employees>> employees = new GenericEntity<ArrayList<Employees>>(dao.SELECT_ALL_EMPLOYEE()) {};
//            GenericEntity<ArrayList<EmployeesDto>> employeesDtos = new GenericEntity<ArrayList<EmployeesDto>>(new ArrayList<EmployeesDto>()) {};
//            for (Employees e : employees.getEntity()) {
//                EmployeesDto dto = EmployeeMapper.INSTANCE.toEmpDto(e);
////                System.out.println(dto);
//                addLinks(dto);
////                System.out.println(dto);
//                employeesDtos.getEntity().add(dto);
//            }
//
//            if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
//                return Response
//                        .ok(employeesDtos)
//                        .type(MediaType.APPLICATION_XML)
//                        .build();
//            }
//            else if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
//                return Response
//                        .ok(employeesDtos)
//                        .type("text/csv")
//                        .build();
//            }
//            return Response
//                    .ok(employeesDtos, MediaType.APPLICATION_JSON)
//                    .build();
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @GET
    @Path("{employeeId}")
    public Response getEmployee(@PathParam("employeeId") int employees_id) {
        try {
            Employees employees = dao.selectEmployees(employees_id);

            if (employees == null) {
                throw new DataNotFoundException("employee with ID " + employees_id + " not found");
            }
//            EmployeesDto dto = new EmployeesDto();
//            dto.setJobId(employees.getJob_id());
//            dto.setMaxSalary(employees.getMax_salary());
//            dto.setMinSalary(employees.getMin_salary());
//            dto.setJob_title(employees.getJob_title());
            EmployeesDto dto = EmployeeMapper.INSTANCE.toEmpDto(employees);
            addLinks(dto);

            return Response.ok(dto).build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        return null;
    }

    private void addLinks(EmployeesDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI empsUri = uriInfo.getAbsolutePathBuilder()
                .path(EmployeesControler.class)
                .build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(empsUri.toString(), "employees");
    }



    @DELETE
    @Path("{employeeId}")
    public void deleteJob(@PathParam("employeeId") int empId) {

        try {
            dao.setDeleteEmployee(empId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting employee with ID " + empId, e);
        }
    }

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
