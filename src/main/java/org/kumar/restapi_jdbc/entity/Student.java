package org.kumar.restapi_jdbc.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Long id;
    private String name;
    private String email;
    private int age;
    private String course;
    private String department;
    private String phoneNumber;
    private String address;
}

