package com.example.v2;

import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a specific combination of variant options
 */
@Data
public class VariantCombination {
    private final List<VariantOptionValue> optionValues;

    public String generateSkuCode() {
        return optionValues.stream()
                .map(VariantOptionValue::getValue)
                .collect(Collectors.joining("-"));
    }
}
