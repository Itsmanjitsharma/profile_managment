package com.cca.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cca.dashboard.entity.Attendance;
import java.util.*;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer>{

    @Query("SELECT a FROM Attendance a WHERE a.profile_id = :ids")
    List<Attendance> findAllByProfileId(@Param("ids") String ids);
    //@Query("SELECT a FROM Attendance a WHERE a.attendanceKey IN :ids")
    //List<Attendance> findAllInIds(@Param("ids") List<AttendanceKey> ids);

}
