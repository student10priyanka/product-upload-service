package com.MAF.ProductUploadApplication.service;

import com.MAF.ProductUploadApplication.event.ProductUpdatedEvent;
import com.MAF.ProductUploadApplication.kafka.ProductEventPublisher;
import com.MAF.ProductUploadApplication.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class ProductUploadService {
    private final ProductEventPublisher productEventPublisher;

    public ProductUploadService(ProductEventPublisher productEventPublisher) {
        this.productEventPublisher = productEventPublisher;
    }

    public void processFile(MultipartFile file) throws Exception {



        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream())
        );

        String line;

        // Skip header
        reader.readLine();

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            if (data.length != 3) {
                System.out.println("Invalid row: " + line);
                continue;
            }

            String name = data[0].trim();
            double price = Double.parseDouble(data[1].trim());
            int quantity = Integer.parseInt(data[2].trim());

            if (name.isEmpty()) {
                System.out.println("Invalid product: name is empty");
                continue;
            }

            if (price <= 0) {
                System.out.println("Invalid product: price must be greater than 0");
                continue;
            }

            if (quantity < 0) {
                System.out.println("Invalid product: quantity cannot be negative");
                continue;
            }

            Product product = new Product(name, price, quantity);

            System.out.println("Valid product: " + product);
            ProductUpdatedEvent event =
                    new ProductUpdatedEvent(
                            product.getName(),
                            product.getPrice(),
                            product.getQuantity()
                    );

            productEventPublisher.publishProductUpdated(event);
        }
        reader.close();
    }
}
