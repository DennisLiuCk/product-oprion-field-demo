package com.example;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.google.gson.Gson;

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
     * Print the product option structure as a tree
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

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
