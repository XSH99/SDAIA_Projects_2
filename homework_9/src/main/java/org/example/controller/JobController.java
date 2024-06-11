package org.example.controller;
import jakarta.ws.rs.core.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.jobDAO;
import org.example.dto.JobDto;
import org.example.dto.JobFilterDto;
import org.example.exceptions.DataNotFoundException;
import org.example.mappers.JobMapper;
import org.example.models.Job;

import java.net.URI;

import java.sql.SQLException;
import java.util.ArrayList;

@Path("/JOB")

public class JobController {
    jobDAO dao = new jobDAO();
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,"text/csv"})
    public Response getAllJob(
            @BeanParam JobFilterDto filter
    ) {
        try {
            GenericEntity<ArrayList<Job>> jobs = new GenericEntity<ArrayList<Job>>(dao.selectAllJobs(filter)) {
            };
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(jobs)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            }
            else if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(jobs)
                        .type("text/csv")
                        .build();
            }

            return Response
//                    .ok()
//                    .entity(jobs)
//                    .type(MediaType.APPLICATION_JSON)
                    .ok(jobs, MediaType.APPLICATION_JSON)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

// i have to chnge all this file all type in controler need to change
//    @GET
//    @Path("{jobId}")
//    public Response getJOB(@PathParam("jobId") int deptId) {
//
//        try {
//            Job jobs = dao.selectJobs(jobId);
//            if (headers.getAcceotableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
//                return Response
//                        .ok(dao)
//                        .type(MediaType.APPLICATION_XML)
//                        .build();
//            }
//            JobDto dto = new JobDto();
//            dto.setJob_id(jobs.getJob_id());
//            dto.setJob_title(jobs.getJob_title());
//            dto.setMin_salary(jobs.getMin_salary());
//            dto.setMax_salary(jobs.getMax_salary());
//            return Response.ok(dto).build();
//        }
//    }

    @GET
    @Path("{jobId}")
    public Response getJob(@PathParam("jobId") int jobId) throws SQLException {

        try {
            Job job = dao.selectJobs(jobId);

            if (job == null) {
                throw new DataNotFoundException("Job with ID " + job + " not found");
            }
//            dto.setJob_id(job.getJob_id());
//            dto.setMax_salary(job.getMax_salary());
//            dto.setMin_salary(job.getMin_salary());
//            dto.setJob_title(job.getJob_title());

            JobDto dto = JobMapper.INSTANCE.toJobDto(job);

            addLinks(dto);
            return Response.ok(dto).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void addLinks(JobDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI empsUri = uriInfo.getAbsolutePathBuilder()
                .path(JobController.class)
                .build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(empsUri.toString(), "employees");
    }

    @DELETE
    @Path("{jobId}")
    public void deleteJob(@PathParam("jobId") int jobId) {

        try {
            dao.setDeleteJobs(jobId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting job with ID " + jobId, e);
        }
    }


    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response insertJob(JobDto dto) {

        try {
            Job job = JobMapper.INSTANCE.toModel(dto);
            dao.setInsertJobs(job);
            NewCookie cookie = (new NewCookie.Builder("username")).value("OOOOO").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(job.getJob_id() + "").build();
            return Response
                    .created(uri)
                    .cookie(cookie)
                    .header("Created by", "Wael")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PUT
    @Path("{jobId}")
    public void updateJob(@PathParam("jobId") int jobId, Job job) {

        try {
            job.setJob_id(jobId);
            dao.setUpdateJobs(job);
        } catch (Exception e) {
            throw new RuntimeException("Error updating job with ID " + jobId, e);
        }
    }



}

