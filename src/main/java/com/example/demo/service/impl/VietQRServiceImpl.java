package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.response.VietQRResponse;
import com.example.demo.service.VietQRService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VietQRServiceImpl implements VietQRService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public VietQRResponse generateQR(Long amount, String orderCode) {

        String url = "https://api.vietqr.io/v2/generate";

        Map<String, Object> body = new HashMap<>();
        body.put("accountNo", "0966113890");
        body.put("accountName", "NGUYEN CONG THANH");
        body.put("acqId", "970422");
        body.put("amount", amount.toString());
        body.put("addInfo", orderCode);
        body.put("format", "text");
        body.put("template", "compact");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        try {
            return objectMapper.readValue(response.getBody(), VietQRResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse VietQR response", e);
        }
    }
}

