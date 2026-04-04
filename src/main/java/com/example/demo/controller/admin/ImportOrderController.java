package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.CreateImportOrderRequest;
import com.example.demo.dto.response.ImportOrderResponse;
import com.example.demo.service.ImportOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/import-orders")
public class ImportOrderController {

    private final ImportOrderService importOrderService;

    public ImportOrderController(ImportOrderService importOrderService) {
        this.importOrderService = importOrderService;
    }

    @PostMapping
    public ImportOrderResponse create(@Valid @RequestBody CreateImportOrderRequest request) {
        return importOrderService.create(request);
    }

    @GetMapping
    public List<ImportOrderResponse> getAll() {
        return importOrderService.getAll();
    }

    @GetMapping("/{id}")
    public ImportOrderResponse getById(@PathVariable Long id) {
        return importOrderService.getById(id);
    }
}
