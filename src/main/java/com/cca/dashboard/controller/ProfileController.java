package com.cca.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cca.dashboard.entity.AddProfile;
import com.cca.dashboard.entity.AdminDetails;
import com.cca.dashboard.entity.Transaction;
import com.cca.dashboard.service.AttendanceService;
import com.cca.dashboard.utility.FileStoreUtility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ProfileController {
    @Autowired
    AttendanceService attendanceService;
    FileStoreUtility fileStoreUtility = new FileStoreUtility();

    @Operation(tags = "saveProfile", description = "This API is used for storing new student or teacher profile details into the database")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/save")
    public ResponseEntity<String> saveProfile(
            @Parameter(description = "Name of the profile") @RequestParam("name") String name,
            @Parameter(description = "Type of the profile (Student or Teacher)") @RequestParam("profileType") String profileType,
            @Parameter(description = "Fees associated with the profile") @RequestParam("fees") double fees,
            @Parameter(description = "Address of the profile") @RequestParam("address") String address,
            @Parameter(description = "Blood group of the profile") @RequestParam("bloodGroup") String bloodGroup,
            @Parameter(description = "Date of birth of the profile") @RequestParam("dateOfBirth") String dateOfBirth,
            @Parameter(description = "Mobile number of the profile") @RequestParam("mobile") String mobile,
            @Parameter(description = "Parents' information of the profile") @RequestParam("parents") String parents,
            @Parameter(description = "Profile image file") @RequestParam("image") MultipartFile image) {

        String customId = name.substring(0, 3).toUpperCase() + UUID.randomUUID().toString().substring(0, 5);
    
        try {
            attendanceService.saveProfile(customId, name, profileType, fees, address, bloodGroup, dateOfBirth, mobile, parents, image);
            return ResponseEntity.ok("Profile saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save profile");
        }
    }

    @Operation(tags = "getProfile", description = "This API is used for retrieving a profile by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "404", description = "Profile Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/student")
    public ResponseEntity<AddProfile> getProfile(
            @Parameter(description = "ID of the profile to retrieve") @RequestParam String id) throws IOException {
        AddProfile addProfile = attendanceService.getProfile(id).orElse(null);
        if (addProfile != null) {
            return ResponseEntity.ok(addProfile);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(tags = "getAllProfiles", description = "This API is used for retrieving all profiles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("students")
    public ResponseEntity<List<AddProfile>> getAllProfiles() {
        List<AddProfile> profiles = attendanceService.getAllProfile();
        return ResponseEntity.ok(profiles);
    }

    @Operation(tags = "getAdminDetails", description = "This API is used for retrieving admin details including transaction status and revenue")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("adminInfos")
    public ResponseEntity<List<AdminDetails>> getAdminDetails() {
        List<AddProfile> profiles = attendanceService.getAllProfile();
        List<AdminDetails> adminDetails = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Adjust the pattern based on your date format
    
        for (AddProfile addProfile : profiles) {
            List<Transaction> transactions = addProfile.getTransactions();
            boolean anyCompletedThisMonth = false;
            AtomicInteger credited = new AtomicInteger(0);
            AtomicInteger debited = new AtomicInteger(0);
    
            if (transactions != null) {
                anyCompletedThisMonth = transactions.stream()
                        .anyMatch(transaction -> {
                            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), formatter);
                            if (transactionDate.getMonth() == currentMonth && addProfile.getProfileType().equalsIgnoreCase("Student")) {
                                credited.addAndGet((int)addProfile.getFees());
                            } else if (transactionDate.getMonth() == currentMonth && addProfile.getProfileType().equalsIgnoreCase("Teacher")) {
                                debited.addAndGet((int)addProfile.getFees());
                            }
                            return transactionDate.getMonth() == currentMonth;
                        });
            }
    
            String transactionStatus = anyCompletedThisMonth ? "Completed" : "Pending";
            double totalRevenue = credited.get() - debited.get();
    
            adminDetails.add(new AdminDetails(
                    addProfile.getId(),
                    addProfile.getName(),
                    addProfile.getFees(),
                    transactions,
                    transactionStatus,
                    addProfile.getProfileType(),
                    totalRevenue,
                    debited.get(),
                    credited.get()
            ));
        }
    
        return ResponseEntity.ok(adminDetails);
    }   
}
