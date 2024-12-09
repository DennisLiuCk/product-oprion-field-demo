package com.example;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ProductOption
 * options for product
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOption {
    private List<OptionField> optionFields;
    private List<OptionSetting> optionSettings;

    /**
     * Generate all possible option settings based on the product's option fields
     * The settings are ordered by the priority defined in OptionField.order and OptionValue.order
     */
    public void generateOptionSettings(String productCode) {
        if (optionFields == null) {
            return;
        }

        // Sort option fields by order
        List<OptionField> sortedFields = optionFields.stream()
               .map(field -> {
                List<OptionValue> sortedValues = field.getValues().stream()
                    .sorted(Comparator.comparing(OptionValue::getOrder))
                    .collect(Collectors.toList());
                field.setValues(sortedValues);
                return field;
               })
               .sorted(Comparator.comparing(OptionField::getOrder))
               .collect(Collectors.toList());

        List<OptionSetting> settings = new ArrayList<>();
        generateSettingsCombinations(productCode, sortedFields, 0, new ArrayList<>(), settings);

        optionSettings = settings;
    }

    private void generateSettingsCombinations(String productCode, List<OptionField> sortedFields, int fieldIndex,
                                              List<OptionFieldValue> currentCombination,
                                              List<OptionSetting> result) {
        if (fieldIndex == sortedFields.size()) {
            // Create a new setting when we've processed all fields
            String generatedSkuCode = generateSkuCode(productCode, currentCombination);
            result.add(new OptionSetting(generatedSkuCode, new ArrayList<>(currentCombination)));
            return;
        }

        OptionField currentField = sortedFields.get(fieldIndex);
        for (OptionValue value : currentField.getValues()) {
            OptionFieldValue settingField = new OptionFieldValue(currentField.getPid(), value.getSid());
            currentCombination.add(settingField);
            generateSettingsCombinations(productCode, sortedFields, fieldIndex + 1, currentCombination, result);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private String generateSkuCode(String productCode, List<OptionFieldValue> combination) {
        return productCode + "-" + combination.stream()
                .map(field -> field.getPid() + "-" + field.getSid())
                .collect(Collectors.joining("-"));
    }

    /**
     * Print the product option structure as a tree
     * This method is used for debugging and visualization purposes
     */
    public void printTree() {
        System.out.println("Product Option Tree:");
        System.out.println("├── Option Fields");
        
        // Print option fields and their values
        for (int i = 0; i < optionFields.size(); i++) {
            OptionField field = optionFields.get(i);
            boolean isLast = i == optionFields.size() - 1;
            
            System.out.printf("%s── %s (pid: %d)%n", 
                isLast ? "└" : "├",
                field.getName(),
                field.getPid());

            // Print values for this field
            List<OptionValue> values = field.getValues();
            for (int j = 0; j < values.size(); j++) {
                OptionValue value = values.get(j);
                boolean isLastValue = j == values.size() - 1;
                
                System.out.printf("%s    %s── %s (sid: %d, order: %d)%n",
                    isLast ? " " : "│",
                    isLastValue ? "└" : "├",
                    value.getName(),
                    value.getSid(),
                    value.getOrder());
            }
        }

        // Print all option values of one sku in one line: SkuCode: Option1: Option1Value, Option2: Option2Value
        // e.g. TSHIRT-001: Size: M, Color: Red
        System.out.println("├── Option Settings");
        for (int i = 0; i < optionSettings.size(); i++) {
            OptionSetting setting = optionSettings.get(i);
            boolean isLast = i == optionSettings.size() - 1;
            
            System.out.printf("%s── %s%n", 
                isLast ? "└" : "├",
                setting.getSkuCode()
                + ": "
                + setting.getFields().stream()
                    .map(field -> field.getPid() + ": " + field.getSid())
                    .collect(Collectors.joining(", ")));
        }

        // Print SKU combinations
        System.out.println("├── SKU Combinations");
        
        // Create a map of pid to field name for easy lookup
        Map<Long, String> fieldNames = optionFields.stream()
            .collect(Collectors.toMap(OptionField::getPid, OptionField::getName));
        
        // Create a map of sid to value name for easy lookup
        Map<Long, String> valueNames = optionFields.stream()
            .flatMap(field -> field.getValues().stream())
            .collect(Collectors.toMap(OptionValue::getSid, OptionValue::getName));

        // Print each SKU combination
        for (int i = 0; i < optionSettings.size(); i++) {
            OptionSetting setting = optionSettings.get(i);
            boolean isLast = i == optionSettings.size() - 1;
            
            System.out.printf("%s── %s%n", 
                isLast ? "└" : "├",
                setting.getSkuCode());

            // Print field-value pairs for this SKU
            List<OptionFieldValue> fields = setting.getFields();
            for (int j = 0; j < fields.size(); j++) {
                OptionFieldValue fieldValue = fields.get(j);
                boolean isLastField = j == fields.size() - 1;
                
                System.out.printf("%s    %s── %s: %s%n",
                    isLast ? " " : "│",
                    isLastField ? "└" : "├",
                    fieldNames.get(fieldValue.getPid()),
                    valueNames.get(fieldValue.getSid()));
            }
        }
    }
}
