package org.example.dto;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@XmlRootElement
public class JobDto {

    private String job_title;
    private int job_id;
    private double min_salary;
    private double max_salary;

    private ArrayList<LinkDto> links = new ArrayList<>();

    public JobDto() {
    }


    public JobDto(String job_title, double max_salary, double min_salary, int job_id) {
        this.job_title = job_title;
        this.max_salary = max_salary;
        this.min_salary = min_salary;
        this.job_id = job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public double getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(double min_salary) {
        this.min_salary = min_salary;
    }

    public double getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(double max_salary) {
        this.max_salary = max_salary;
    }

    public JobDto(ResultSet rs) throws SQLException {
        job_id = rs.getInt("job_id");
        job_title = rs.getString("job_title");
        min_salary = rs.getDouble("min_salary");
        max_salary = rs.getDouble("max_salary");
    }


    @XmlElementWrapper
    @XmlElement(name = "link")
    public ArrayList<LinkDto> getLinks() {
        return links;
    }

    public void addLink(String url, String rel) {
        LinkDto link = new LinkDto();
        link.setLink(url);
        link.setRel(rel);
        links.add(link);
    }

    @Override
    public String toString() {
        return "Job{" +
                "job_title='" + job_title + '\'' +
                ", job_id=" + job_id +
                ", min_salary=" + min_salary +
                ", max_salary=" + max_salary +
                '}';
    }
}
