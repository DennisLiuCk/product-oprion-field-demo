package com.example.v2;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Main product variant class that manages option combinations
 */
@Data
public class ProductVariant {
    private String productCode;
    private String skuCode;
    private final List<VariantOptionGroup> optionGroups = new ArrayList<>();

    public void addOptionGroup(VariantOptionGroup group) {
        optionGroups.add(group);
    }

    /**
     * Generate all possible variant combinations based on option groups
     */
    public List<VariantCombination> generateVariantCombinations() {
        List<VariantCombination> combinations = new ArrayList<>();
        generateCombinationsRecursive(new ArrayList<>(), 0, combinations);
        return combinations;
    }

    private void generateCombinationsRecursive(List<VariantOptionValue> current, 
                                             int groupIndex, 
                                             List<VariantCombination> result) {
        if (groupIndex == optionGroups.size()) {
            result.add(new VariantCombination(new ArrayList<>(current)));
            return;
        }

        VariantOptionGroup group = optionGroups.get(groupIndex);
        for (VariantOption option : group.getSortedOptions()) {
            if (option instanceof VariantOptionValue) {
                current.add((VariantOptionValue) option);
                generateCombinationsRecursive(current, groupIndex + 1, result);
                current.remove(current.size() - 1);
            }
        }
    }
}
