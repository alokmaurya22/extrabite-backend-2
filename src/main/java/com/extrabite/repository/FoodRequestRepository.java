package com.extrabite.repository;

import com.extrabite.entity.FoodRequest;
import com.extrabite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Food request ke liye repository hai
public interface FoodRequestRepository extends JpaRepository<FoodRequest, Long> {
    List<FoodRequest> findByRequester(User requester);

    List<FoodRequest> findByStatus(com.extrabite.entity.FoodRequestStatus status);
}