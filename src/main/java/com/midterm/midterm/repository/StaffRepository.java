package com.midterm.midterm.repository;

import com.midterm.midterm.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByUserName(String userName);
}