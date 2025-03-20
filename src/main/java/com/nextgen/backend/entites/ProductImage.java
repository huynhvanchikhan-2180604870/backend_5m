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
@Table(name = "product_images")
public class ProductImage {
    @Id
    private UUID id;
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
