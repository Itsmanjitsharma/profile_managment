package com.cca.dashboard.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddProfileDto {

    private String name;
    private String parents;
    private String mobile;
    private String bloodGroup;
    private String address;
    private String id;
    private String dateOfBirth;
    private MultipartFile image;
    private double fees;
    
    private List<Attendance> attendances;

    private List<Transaction> transactions;

    private String profileType;
}
