package com.cca.dashboard.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cca.dashboard.entity.AddProfile;
import com.cca.dashboard.entity.Attendance;
import com.cca.dashboard.entity.Transaction;
import com.cca.dashboard.repository.AttendanceRepository;
import com.cca.dashboard.repository.ProfileRespository;
import com.cca.dashboard.repository.TransactionRepository;
@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    ProfileRespository profileRespository;


    @Autowired
    TransactionRepository transactionRepository;
   /*
    @Transactional
    public void recordAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendance(AttendanceKey attendanceKey) {
        return attendanceRepository.findAllInIds(Collections.singletonList(attendanceKey));
    }

    public List<Attendance> getAttendanceInRange(String studentId, String startDate, String endDate) {
        List<AttendanceKey> attendanceKeysList = attendanceKeyRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate);
        return attendanceRepository.findAllInIds(attendanceKeysList);

    }

    public List<Attendance> getAttendanceInDateRange(String startDate, String endDate) {
        List<AttendanceKey> attendanceKeysList = attendanceKeyRepository.findByDateBetween(startDate, endDate);
        return attendanceRepository.findAllInIds(attendanceKeysList);
    }
*/
    public Optional<AddProfile> getProfile(String id) {
        AddProfile addProfile = profileRespository.findById(id).get();
          List<Transaction> transactions = new ArrayList<Transaction>();
          List<Attendance> attendances = new ArrayList<Attendance>();
          transactions.add(new Transaction(1,2400.00,"Paid July month fees","12-07-2024","NEEf9cd2"));
          attendances = attendanceRepository.findAllByProfileId(id);
          addProfile.setTransactions(transactions);
          addProfile.setAttendances(attendances);
          return Optional.of(addProfile);
    }

    public List<AddProfile> getAllProfile() {
       return profileRespository.findAll();
    }

    public void saveAttendances(List<Attendance> attendances) {
       attendanceRepository.saveAll(attendances);
    }

    public void saveProfile(String id, String name, String profileType, double fees, String address, String bloodGroup, String dateOfBirth, String mobile, String parents, MultipartFile image) throws IOException {
        AddProfile profile = new AddProfile();
        profile.setId(id);
        profile.setName(name);
        profile.setProfileType(profileType);
        profile.setFees(fees);
        profile.setAddress(address);
        profile.setBloodGroup(bloodGroup);
        profile.setDateOfBirth(dateOfBirth);
        profile.setMobile(mobile);
        profile.setParents(parents);
        byte[] imageBytes = IOUtils.toByteArray( image.getInputStream());
       // System.out.println(Base64.getEncoder().encodeToString(imageBytes));
        profile.setImage(Base64.getEncoder().encodeToString(imageBytes));
        profileRespository.save(profile);
    }
}
