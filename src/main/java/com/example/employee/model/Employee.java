package com.example.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee {
    @Id
    @NotNull
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String reportsTo;
    private String profileImage;
    private Integer level;
}
