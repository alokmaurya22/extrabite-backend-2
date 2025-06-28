package com.extrabite.dto;

import com.extrabite.entity.RatingType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingResponse {
    private Long id;
    private Long donationRequestId;
    private Long raterId;
    private String raterName;
    private Long ratedUserId;
    private String ratedUserName;
    private Integer rating;
    private String comment;
    private RatingType ratingType;
    private LocalDateTime createdAt;
}