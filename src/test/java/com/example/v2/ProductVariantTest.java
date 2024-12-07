package com.example.v2;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ProductVariantTest {

    @Test
    void testVariantCombinationGeneration() {
        // Create size option group
        VariantOptionGroup sizeGroup = new VariantOptionGroup(1L, "Size", 1);
        sizeGroup.addOption(new VariantOptionValue(1L, "Small", 1, "S"));
        sizeGroup.addOption(new VariantOptionValue(2L, "Medium", 2, "M"));
        sizeGroup.addOption(new VariantOptionValue(3L, "Large", 3, "L"));

        // Create color option group
        VariantOptionGroup colorGroup = new VariantOptionGroup(2L, "Color", 2);
        colorGroup.addOption(new VariantOptionValue(4L, "Red", 1, "RED"));
        colorGroup.addOption(new VariantOptionValue(5L, "Blue", 2, "BLUE"));

        // Build product variant
        ProductVariant variant = new ProductVariantBuilder()
                .withProductCode("TSHIRT")
                .withSkuCode("TSH")
                .withOptionGroup(sizeGroup)
                .withOptionGroup(colorGroup)
                .build();

        // Generate combinations
        List<VariantCombination> combinations = variant.generateVariantCombinations();

        // Verify results
        assertEquals(6, combinations.size());
        
        // Verify combinations are generated in correct order
        List<String> skuCodes = combinations.stream()
                .map(VariantCombination::generateSkuCode)
                .collect(Collectors.toList());

        assertTrue(skuCodes.contains("S-RED"));
        assertTrue(skuCodes.contains("S-BLUE"));
        assertTrue(skuCodes.contains("M-RED"));
        assertTrue(skuCodes.contains("M-BLUE"));
        assertTrue(skuCodes.contains("L-RED"));
        assertTrue(skuCodes.contains("L-BLUE"));

        // print combinations for debugging
        combinations.forEach(System.out::println);
    }

    @Test
    void testOptionOrdering() {
        // Create option group
        VariantOptionGroup sizeGroup = new VariantOptionGroup(1L, "Size", 1);
        
        // Add options in random order
        sizeGroup.addOption(new VariantOptionValue(2L, "Medium", 2, "M"));
        sizeGroup.addOption(new VariantOptionValue(3L, "Large", 3, "L"));
        sizeGroup.addOption(new VariantOptionValue(1L, "Small", 1, "S"));

        // Get sorted options
        List<VariantOption> sortedOptions = sizeGroup.getSortedOptions();

        // Verify order
        assertEquals("S", ((VariantOptionValue)sortedOptions.get(0)).getValue());
        assertEquals("M", ((VariantOptionValue)sortedOptions.get(1)).getValue());
        assertEquals("L", ((VariantOptionValue)sortedOptions.get(2)).getValue());

        // print sorted options for debugging
        sortedOptions.forEach(System.out::println);
    }
}
