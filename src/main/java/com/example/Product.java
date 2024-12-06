package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Generate all possible option settings based on the product's option fields
     * The settings are ordered by the priority defined in OptionField.order and OptionValue.order
     */
    public void generateOptionSettings() {
        if (productOption == null || productOption.getOptionFields() == null) {
            return;
        }

        // Sort option fields by order
        List<OptionField> sortedFields = productOption.getOptionFields().stream()
                .sorted(Comparator.comparing(OptionField::getOrder))
                .peek(field -> field.setValues(
                        field.getValues().stream()
                                .sorted(Comparator.comparing(OptionValue::getOrder))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        List<OptionSetting> settings = new ArrayList<>();
        generateSettingsCombinations(sortedFields, 0, new ArrayList<>(), settings);
        
        productOption.setOptionSettings(settings);
    }

    private void generateSettingsCombinations(List<OptionField> sortedFields, int fieldIndex,
                                            List<OptionFieldValue> currentCombination,
                                            List<OptionSetting> result) {
        if (fieldIndex == sortedFields.size()) {
            // Create a new setting when we've processed all fields
            String generatedSkuCode = generateSkuCode(currentCombination);
            result.add(new OptionSetting(generatedSkuCode, new ArrayList<>(currentCombination)));
            return;
        }

        OptionField currentField = sortedFields.get(fieldIndex);
        for (OptionValue value : currentField.getValues()) {
            OptionFieldValue settingField = new OptionFieldValue(currentField.getPid(), value.getSid());
            currentCombination.add(settingField);
            generateSettingsCombinations(sortedFields, fieldIndex + 1, currentCombination, result);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private String generateSkuCode(List<OptionFieldValue> combination) {
        return productCode + "-" + combination.stream()
                .map(field -> field.getPid() + "-" + field.getSid())
                .collect(Collectors.joining("-"));
    }
}
