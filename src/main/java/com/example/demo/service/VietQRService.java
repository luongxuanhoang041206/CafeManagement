package com.example.demo.service;


import com.example.demo.dto.response.VietQRResponse;

public interface VietQRService {
    public VietQRResponse generateQR(Long amount, String orderCode);
}
