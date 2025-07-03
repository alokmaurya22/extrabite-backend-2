package com.extrabite.repository;

import com.extrabite.entity.DonationRequest;
import com.extrabite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Donation request ke liye repository hai
@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {
    // Find all requests made by a specific receiver
    List<DonationRequest> findByReceiver(User receiver);

    // Find all requests sent to a specific donor
    List<DonationRequest> findByDonor(User donor);
}