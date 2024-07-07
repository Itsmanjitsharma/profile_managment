package com.cca.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cca.dashboard.entity.Attendance;
import com.cca.dashboard.service.AttendanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Operation(tags = "saveAttendance", description = "This API is used for saving attendance records")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Attendance records saved successfully"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("addAttendance")
    public ResponseEntity<Void> saveAttendance(
            @Parameter(description = "List of attendance records to be saved") @RequestBody List<Attendance> attendances) {
        System.out.println(attendances);
        try {
            attendanceService.saveAttendances(attendances);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
