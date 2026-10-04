package com.MAF.ProductUploadApplication.kafka;



import com.MAF.ProductUploadApplication.event.ProductUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductEventPublisher {

    private static final String TOPIC = "product-updated";

    private final KafkaTemplate<String, ProductUpdatedEvent> kafkaTemplate;

    public ProductEventPublisher(
            KafkaTemplate<String, ProductUpdatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishProductUpdated(ProductUpdatedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getName(),
                event
        );

        System.out.println(
                "ProductUpdatedEvent published: "
                        + event.getName()
        );
    }
}
