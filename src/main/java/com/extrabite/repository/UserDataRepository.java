package com.extrabite.repository;

import com.extrabite.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// UserData ke liye repository hai
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}