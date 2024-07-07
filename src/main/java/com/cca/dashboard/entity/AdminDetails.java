package com.cca.dashboard.entity;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminDetails {
    String id;
    String name;
    private double fees;
    List<Transaction> transactions;
    String transactionStatus;
    String profileType;
    double totlerevenue;
    double debibted;
    double credited;
}
