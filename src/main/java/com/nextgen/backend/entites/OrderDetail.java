package com.nextgen.backend.entites;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetail {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    @Column(name = "final_price")
    private Integer finalPrice;
}
