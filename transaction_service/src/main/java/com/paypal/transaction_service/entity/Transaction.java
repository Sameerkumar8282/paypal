package com.paypal.transaction_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "sender_name", nullable = false)
    Long senderId;

    @Column(name = "receiver_name", nullable = false)
    Long receiverId;

    @Column(name = "amount", nullable = false)
    @Min(value = 0, message = "Amount must be greater than 0")
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String status;

    @PrePersist
    public void prePersist(){
        if(timestamp == null){
            timestamp = LocalDateTime.now();
        }
        if(status == null){
            status = "PENDING";
        }
    }
}
