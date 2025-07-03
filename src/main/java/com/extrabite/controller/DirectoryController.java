package com.extrabite.controller;

import com.extrabite.dto.UserProfileResponse;
import com.extrabite.service.DirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// Directory ke liye controller hai
@RestController
@RequestMapping("/api/directory")
@RequiredArgsConstructor
public class DirectoryController {

    private final DirectoryService directoryService;

    @GetMapping("/users/search")
    public ResponseEntity<List<UserProfileResponse>> searchUsers(@RequestParam Map<String, String> allParams) {
        List<UserProfileResponse> users = directoryService.searchUsers(allParams);
        return ResponseEntity.ok(users);
    }
}