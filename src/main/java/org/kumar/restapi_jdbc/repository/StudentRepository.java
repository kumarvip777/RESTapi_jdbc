package org.kumar.restapi_jdbc.repository;

import org.kumar.restapi_jdbc.entity.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Student student) {

        String sql = "INSERT INTO student (name, email, age, course, department, phone_number, address) VALUES (?, ?, ?, ?, ?, ?, ?)";


        return jdbcTemplate.update(sql,
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getCourse(),
                student.getDepartment(),
                student.getPhoneNumber(),
                student.getAddress()
        );

    }


    public List<Student> findAll() {

        String sql = "SELECT * FROM student";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Student s = new Student();
            s.setId(rs.getLong("id"));
            s.setName(rs.getString("name"));
            s.setEmail(rs.getString("email"));
            s.setAge(rs.getInt("age"));
            s.setCourse(rs.getString("course"));
            s.setDepartment(rs.getString("department"));
            s.setPhoneNumber(rs.getString("phone_number"));
            s.setAddress(rs.getString("address"));
            return s;
        });
    }


    public Student findById(Long id) {

        String sql = "SELECT * FROM student WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Student s = new Student();
            s.setId(rs.getLong("id"));
            s.setName(rs.getString("name"));
            s.setEmail(rs.getString("email"));
            s.setAge(rs.getInt("age"));
            s.setCourse(rs.getString("course"));
            s.setDepartment(rs.getString("department"));
            s.setPhoneNumber(rs.getString("phone_number"));
            s.setAddress(rs.getString("address"));
            return s;
        }, id);
    }

    public int update(Student student) {

        String sql = "UPDATE student SET name=?, email=?, age=?, course=?, department=?, phone_number=?, address=? WHERE id=?";

        return jdbcTemplate.update(sql,
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getCourse(),
                student.getDepartment(),
                student.getPhoneNumber(),
                student.getAddress(),
                student.getId()
        );
    }


    public int delete(Long id) {

        String sql = "DELETE FROM student WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }


}
