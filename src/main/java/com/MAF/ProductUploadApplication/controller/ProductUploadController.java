package com.MAF.ProductUploadApplication.controller;

import com.MAF.ProductUploadApplication.service.ProductUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductUploadController {

    private final ProductUploadService productUploadService;

    public ProductUploadController(ProductUploadService productUploadService) {
        this.productUploadService = productUploadService;
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadProducts(
            @RequestParam("file") MultipartFile file) throws Exception {

        productUploadService.processFile(file);

        return ResponseEntity.ok("CSV processed successfully");
    }
}