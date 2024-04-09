package com.essen.bookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookstore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String location;
    private Double priceModifier;
    private Double moneyInCashRegister;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Book, Integer> Inventory;

}
