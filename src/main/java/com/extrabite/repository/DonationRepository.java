package com.extrabite.repository;

import com.extrabite.entity.Donation;
import com.extrabite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// Donation ke liye repository hai
@Repository
public interface DonationRepository extends JpaRepository<Donation, Long>, JpaSpecificationExecutor<Donation> {
    List<Donation> findByDonor(User donor);

    long countByDonor(User donor);
}