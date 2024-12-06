package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String productCode;
    private String skuCode;
    private boolean isPrimary;
    private String optionField;
    private String optionValue;

    private ProductOption productOption;
}
