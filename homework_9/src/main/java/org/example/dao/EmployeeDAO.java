package org.example.dao;

import org.example.models.Employees;
import org.example.dto.EmployeesFilterDto;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO {



    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\homework_9\\hr.db";
    private static final String SELECT_ALL_EMPLOYEE = "select * from employees";
    private static final String SELECT_ONE_EMPLOYEE = "select * from employees where employee_id = ?";
    private static final String INSERT_EMPLOYEE = "insert into employees values  (?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_EMPLOYEE = "update employees set first_name = ? ,last_name = ?, email = ? phone_number = ? ,hire_date =? ,job_id = ? ,salary = ? ,manager_id = ? ,department_id = ? where employee_id =?";
    private static final String DELETE_EMPLOYEE = "delete from employees where employee_id = ?";
    //==============================================
    private static final String SELECT_ALL_EMP_JOIN_JOB = "select * from jobs join employees on jobs.job_id = employees.job_id";
    private static final String SELECT_ONE_EMP_JOIN_JOB = "select * from jobs join employees on jobs.job_id = employees.job_id where employee_id = ?";

    public void setInsertEmployees(Employees d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(INSERT_EMPLOYEE);
        st.setInt(1, d.getEmployee_id());
        st.setString(2, d.getFirst_name());
        st.setString(3, d.getLast_name());
        st.setString(4, d.getEmail());
        st.setString(5, d.getPhone_number());
        st.setString(6, d.getHire_date());
        st.setInt(7, d.getJob_id());
        st.setDouble(8, d.getSalary());
        st.setInt(9, d.getManager_id());
        st.setInt(10, d.getDepartment_id());
        st.executeUpdate();
    }

    public void setUpdateEmployees(Employees d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(UPDATE_EMPLOYEE);
        st.setInt(1, d.getEmployee_id());
        st.setString(2, d.getFirst_name());
        st.setString(3, d.getLast_name());
        st.setString(4, d.getEmail());
        st.setString(5, d.getPhone_number());
        st.setString(6, d.getHire_date());
        st.setInt(7, d.getJob_id());
        st.setDouble(8, d.getSalary());
        st.setInt(9, d.getManager_id());
        st.setInt(10, d.getDepartment_id());
        st.executeUpdate();
    }

    public void setDeleteEmployee(int employeesId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(DELETE_EMPLOYEE);
        st.setInt(1, employeesId);
        st.executeUpdate();
    }

    public Employees selectEmployees(int employeesId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
      //  PreparedStatement st = conn.prepareStatement(SELECT_ONE_EMPLOYEE);
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_EMP_JOIN_JOB);

        st.setInt(1, employeesId);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return new Employees(rs);
        }
        else {
            return null;
        }
    }

    public ArrayList<Employees> selectAllEmployee(EmployeesFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        //PreparedStatement st = conn.prepareStatement(SELECT_ALL_EMPLOYEE);
        PreparedStatement st = conn.prepareStatement(SELECT_ALL_EMP_JOIN_JOB);

        ResultSet rs = st.executeQuery();
        ArrayList<Employees> employee = new ArrayList<>();
        while (rs.next()) {
            employee.add(new Employees(rs));
        }

        return employee;
    }


}
