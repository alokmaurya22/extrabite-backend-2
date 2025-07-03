package com.extrabite.entity;

// Request ka status batata hai
public enum RequestStatus {
    PENDING, // Receiver has made the request, waiting for Donor's response
    ACCEPTED, // Donor has accepted the request
    REJECTED, // Donor has rejected the request
    AWAITING_PICKUP, // Request accepted, OTP generated, waiting for receiver to pickup
    COMPLETED, // Receiver provided OTP, donation is successfully handed over
    CANCELLED // Request was cancelled by either party
}