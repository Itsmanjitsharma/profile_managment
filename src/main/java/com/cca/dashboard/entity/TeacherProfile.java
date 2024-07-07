package com.cca.dashboard.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherProfile {
    private String name;
    private String department;
    private String mobile;
    private String bloodGroup;
    private String address;
    private List<Attendance> attendanceRecords;
    private List<Transaction> transactions;
}

