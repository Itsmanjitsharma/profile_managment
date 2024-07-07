package com.cca.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cca.dashboard.entity.AddProfile;

@Repository
public interface ProfileRespository extends JpaRepository<AddProfile,String> {
    public Optional<AddProfile> findById(String id);
}
