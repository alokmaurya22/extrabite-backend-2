package com.extrabite.service;

import com.extrabite.dto.UserProfileResponse;

import java.util.List;
import java.util.Map;

public interface DirectoryService {
    List<UserProfileResponse> searchUsers(Map<String, String> filters);
}