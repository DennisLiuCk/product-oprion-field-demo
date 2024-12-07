package com.example.v2;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Leaf class representing a concrete option value
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class VariantOptionValue extends VariantOption {
    private final String value;

    public VariantOptionValue(Long id, String name, Integer order, String value) {
        super(id, name, order);
        this.value = value;
    }
}
