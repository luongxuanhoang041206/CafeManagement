package com.example.demo.controller.client;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dto.response.VietQRResponse;
import com.example.demo.service.VietQRService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/qr-code")
public class QRCodeController {

    @Autowired
    private VietQRService vietQRService;

    /**
     * Tao qr dua tren VietQR API
     * @param orderId - ma hoa don(ID)
     * @param totalAmount - so tien can thanh toan
     * @param orderCode - ma code hoa don
     * @return QR code từ VietQR API
     */
    @GetMapping("/generate")
    public ResponseEntity<VietQRResponse> generatePaymentQR(
            @RequestParam Long orderId,
            @RequestParam Long totalAmount,
            @RequestParam String orderCode
    ) {
        try {
            VietQRResponse response = vietQRService.generateQR(totalAmount, orderCode);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
