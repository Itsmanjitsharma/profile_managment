package com.cca.dashboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class AddProfile {
    private String name;
    private String parents;
    private String mobile;
    private String bloodGroup;
    private String address;
    @Id
    private String id;
    private String dateOfBirth;
    @Lob
    @Column(length = 1000000)
    private String image;
    private double fees;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<Attendance> attendances;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<Transaction> transactions;

    private String profileType;


   
}
