package com.nextgen.backend.dtos;

import com.nextgen.backend.entites.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID id;

    private UUID user_id;

    private Date orderDate;

    private Integer totalPrice;

    private String status;
}
