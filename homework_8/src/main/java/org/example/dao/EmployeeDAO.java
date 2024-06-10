package org.example.dao;

import jakarta.ws.rs.BeanParam;
import org.example.Models.Employees;
import org.example.dto.EmployeesFilterDto;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO {


    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\homework_5\\hr (1).db";
    private static final String SELECT_ALL_EMPLOYEE = "select * from employees";
    private static final String SELECT_ONE_EMPLOYEE = "select * from employees where employee_id = ?";
    private static final String INSERT_EMPLOYEE = "insert into employees values  (?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_EMPLOYEE = "update employees set first_name = ? ,last_name = ?, email = ? phone_number = ? ,hire_date =? ,job_id = ? ,salary = ? ,manager_id = ? ,department_id = ? where employee_id =?";
    private static final String DELETE_EMPLOYEE = "delete from employees where employee_id = ?";
    //==============================================
    private static final String SELECT_DEPT_WITH_MIN = "select * from Jobs where min_salary = ?";
    // CHEEK IF I BUTET AS SALARY
    private static final String SELECT_DEPT_WITH_LOC_PAGINATION = "select * from employees where salary = ? order by employee_id limit ? offset ?";
    private static final String SELECT_DEPT_WITH_PAGINATION = "select * from employees order by employee_id limit ? offset ?";

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
        st.setInt(1, d.getJob_id());
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
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_EMPLOYEE);
        st.setInt(1, employeesId);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return new Employees(rs);
        }
        else {
            return null;
        }
    }

    public ArrayList<Employees> selectAllEmployee() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ALL_EMPLOYEE);
        ResultSet rs = st.executeQuery();
        ArrayList<Employees> employee = new ArrayList<>();
        while (rs.next()) {
            employee.add(new Employees(rs));
        }

        return employee;
    }

    public ArrayList<Employees> selectAllEmployee(EmployeesFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ALL_EMPLOYEE);
        ResultSet rs = st.executeQuery();
        ArrayList<Employees> employee = new ArrayList<>();
        while (rs.next()) {
            employee.add(new Employees(rs));
        }

        return employee;
    }

//    public ArrayList<Employees> selectAllJobs(EmployeesFilterDto filter) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);
//        PreparedStatement st;
//        if (filter.getSalary() != null && filter.getLimit() != null) {
//            st = conn.prepareStatement(SELECT_DEPT_WITH_LOC_PAGINATION);
//            st.setDouble(1, filter.getSalary());
//            st.setInt(2, filter.getLimit());
//            st.setInt(3, filter.getOffset());
//
//        } else if (filter.getSalary() != null) {
//            st = conn.prepareStatement(SELECT_DEPT_WITH_MIN);
//            st.setDouble(1, filter.getSalary());
//
//        } else if (filter.getLimit() != null) {
//            st = conn.prepareStatement(SELECT_DEPT_WITH_PAGINATION);
//            st.setInt(1, filter.getLimit());
//            st.setInt(2, filter.getOffset());
//
//        } else {
//            st = conn.prepareStatement(SELECT_ALL_EMPLOYEE);
//        }
//        ResultSet rs = st.executeQuery();
//        ArrayList<Employees> depts = new ArrayList<>();
//        while (rs.next()) {
//            depts.add(new Employees(rs));
//        }
//
//        return depts;
//    }




    // new one for hier_date
    public ArrayList<Employees> selectEmployeesByHireYear(String year) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        String query = "SELECT * FROM employees WHERE hire_date LIKE ? || '%' ";
        PreparedStatement st;

        if(year != null)
        {
            st = conn.prepareStatement(query);
            st.setString(1, year); // Assuming hire_date is stored as a string with the format 'YYYY-MM-DD'

        }
        else
        {
            st = conn.prepareStatement(SELECT_ALL_EMPLOYEE);
        }
        ResultSet rs = st.executeQuery();

        ArrayList<Employees> employees = new ArrayList<>();
        while (rs.next()) {
            employees.add(new Employees(rs));
        }

        return employees;
    }


    // new one for job_id
    public ArrayList<Employees> selectEmployeesByJobId(Integer job_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        String query = "SELECT * FROM employees WHERE job_id = ?";
        PreparedStatement st;

        if(job_id != null)
        {
            st =conn.prepareStatement(query);
            st.setInt(1, job_id);
        }
        else
        {
            st = conn.prepareStatement(SELECT_ALL_EMPLOYEE);
        }
        ResultSet rs = st.executeQuery();

        ArrayList<Employees> employees = new ArrayList<>();
        while (rs.next()) {
            employees.add(new Employees(rs));
        }

        return employees;
    }

}
