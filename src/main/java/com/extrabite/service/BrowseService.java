package com.extrabite.service;

import com.extrabite.dto.DonationResponse;
import java.util.List;
import java.util.Map;

public interface BrowseService {
    List<DonationResponse> getAllAvailableDonations();

    List<DonationResponse> searchDonations(Map<String, String> filters);
}