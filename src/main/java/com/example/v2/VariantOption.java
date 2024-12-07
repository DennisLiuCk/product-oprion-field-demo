package com.example.v2;

import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * Base abstract class for all variant options
 */
@Data
@AllArgsConstructor
public abstract class VariantOption implements Comparable<VariantOption> {
    private Long id;
    private String name;
    private Integer order;

    @Override
    public int compareTo(VariantOption other) {
        return this.order.compareTo(other.order);
    }
}
