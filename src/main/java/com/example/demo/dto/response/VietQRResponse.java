package com.example.demo.dto.response;

import lombok.Data;

@Data
public class VietQRResponse {
    private String code;
    private String desc;
    private VietQRData data;

    @Data
    public static class VietQRData {
        private String qrCode;
        private String qrDataURL;
    }
}