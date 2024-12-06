package com.example;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class ProductTest {

    private Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    
    @Test
    void testProductWithOptions() {
        // Create a T-shirt product with size and color options
        Product tshirt = createTShirtProduct();
        assertNotNull(tshirt.getProductOption());
        assertEquals(2, tshirt.getProductOption().getOptionFields().size());
        
        // Verify SKU settings
        List<OptionSetting> settings = tshirt.getProductOption().getOptionSettings();
        assertEquals(6, settings.size()); // 2 sizes * 3 colors = 6 combinations

        // Print the product option tree
        System.out.println("\nPrinting T-shirt product option tree:");
        System.out.println("=====================================");
        tshirt.getProductOption().printTree();
        System.out.println("=====================================\n");
    }

    @Test
    void testGenerateOptionSettings() {
        // Create a product with options but no settings
        Product product = Product.builder()
            .id(1L)
            .productCode("TSHIRT-001")
            .isPrimary(true)
            .productOption(ProductOption.builder()
                .optionFields(Arrays.asList(
                    // Size field with order 1
                    OptionField.builder()
                        .pid(100L)
                        .name("Size")
                        .order(1)
                        .values(Arrays.asList(
                            // M size with order 2
                            OptionValue.builder()
                                .sid(101L)
                                .name("M")
                                .order(2)
                                .build(),
                            // S size with order 1
                            OptionValue.builder()
                                .sid(102L)
                                .name("S")
                                .order(1)
                                .build()
                        ))
                        .build(),
                    // Color field with order 2
                    OptionField.builder()
                        .pid(200L)
                        .name("Color")
                        .order(2)
                        .values(Arrays.asList(
                            // Blue with order 2
                            OptionValue.builder()
                                .sid(201L)
                                .name("Blue")
                                .order(2)
                                .build(),
                            // Red with order 1
                            OptionValue.builder()
                                .sid(202L)
                                .name("Red")
                                .order(1)
                                .build()
                        ))
                        .build()
                ))
                .build())
            .build();


        
        // Generate option settings
        product.getProductOption().generateOptionSettings(product.getProductCode());

        // Verify the settings were generated
        List<OptionSetting> settings = product.getProductOption().getOptionSettings();
        assertNotNull(settings);
        assertEquals(4, settings.size()); // 2 sizes * 2 colors = 4 combinations

        // Verify the first setting (should be S-Red due to ordering)
        OptionSetting firstSetting = settings.get(0);
        List<OptionFieldValue> firstSettingFields = firstSetting.getFields();
        assertEquals(2, firstSettingFields.size());
        assertEquals(100L, firstSettingFields.get(0).getPid()); // Size field
        assertEquals(102L, firstSettingFields.get(0).getSid()); // S value
        assertEquals(200L, firstSettingFields.get(1).getPid()); // Color field
        assertEquals(202L, firstSettingFields.get(1).getSid()); // Red value

        // Verify SKU code format
        assertEquals("TSHIRT-001-100-102-200-202", firstSetting.getSkuCode());

        // Verify the complete order of combinations (S-Red, S-Blue, M-Red, M-Blue)
        String[] expectedSkuSuffixes = {
            "100-102-200-202", // S-Red
            "100-102-200-201", // S-Blue
            "100-101-200-202", // M-Red
            "100-101-200-201"  // M-Blue
        };

        for (int i = 0; i < settings.size(); i++) {
            String expectedSku = "TSHIRT-001-" + expectedSkuSuffixes[i];
            assertEquals(expectedSku, settings.get(i).getSkuCode());
        }

        // print the product option tree
        System.out.println("\nPrinting T-shirt product option tree:");
        System.out.println("=====================================");
        product.getProductOption().printTree();
        System.out.println("=====================================\n");

        // print the product option in beautiful/formatted JSON
        System.out.println("\nPrinting T-shirt product option in JSON:");
        System.out.println("=====================================");
        System.out.println(gson.toJson(product.getProductOption()));
        System.out.println("=====================================\n");
        

    }


    @Test
    void testGenerateOptionSettings2() {
        // Create a product with options but no settings
        Product product = Product.builder()
            .id(1L)
            .productCode("TSHIRT-001")
            .isPrimary(true)
            .productOption(ProductOption.builder()
                .optionFields(Arrays.asList(
                    // Size field with order 1
                    OptionField.builder()
                        .pid(100L)
                        .name("Size")
                        .order(1)
                        .values(Arrays.asList(
                            // M size with order 2
                            OptionValue.builder()
                                .sid(101L)
                                .name("M")
                                .order(2)
                                .build(),
                            // S size with order 1
                            OptionValue.builder()
                                .sid(102L)
                                .name("S")
                                .order(1)
                                .build(),
                            // L size with order 3
                            OptionValue.builder()
                                .sid(103L)
                                .name("L")
                                .order(3)
                                .build()
                        ))
                        .build(),
                    // Color field with order 2
                    OptionField.builder()
                        .pid(200L)
                        .name("Color")
                        .order(2)
                        .values(Arrays.asList(
                            // Blue with order 2
                            OptionValue.builder()
                                .sid(201L)
                                .name("Blue")
                                .order(2)
                                .build(),
                            // Red with order 1
                            OptionValue.builder()
                                .sid(202L)
                                .name("Red")
                                .order(1)
                                .build()
                        ))
                        .build(),
                    // Material field with order 3
                    OptionField.builder()
                        .pid(300L)
                        .name("Material")
                        .order(3)
                        .values(Arrays.asList(
                            // Cotton with order 2
                            OptionValue.builder()
                                .sid(301L)
                                .name("Cotton")
                                .order(2)
                                .build(),
                            // Polyester with order 1
                            OptionValue.builder()
                                .sid(302L)
                                .name("Polyester")
                                .order(1)
                                .build()
                        ))
                        .build()
                ))
                .build())
            .build();


        
        // Generate option settings
        product.getProductOption().generateOptionSettings(product.getProductCode());

        // print the product option tree
        System.out.println("\nPrinting T-shirt product option tree:");
        System.out.println("=====================================");
        product.getProductOption().printTree();
        System.out.println("=====================================\n");

        // print the product option in beautiful/formatted JSON
        System.out.println("\nPrinting T-shirt product option in JSON:");
        System.out.println("=====================================");
        System.out.println(gson.toJson(product.getProductOption()));
        System.out.println("=====================================\n");
    }

    private Product createTShirtProduct() {
        // Create size values
        OptionValue sizeM = OptionValue.builder()
            .sid(101L)
            .name("M")
            .order(1)
            .build();

        OptionValue sizeL = OptionValue.builder()
            .sid(102L)
            .name("L")
            .order(2)
            .build();

        // Create size option field
        OptionField sizeField = OptionField.builder()
            .pid(100L)
            .name("Size")
            .order(1)
            .values(Arrays.asList(sizeM, sizeL))
            .build();

        // Create color values
        OptionValue red = OptionValue.builder()
            .sid(201L)
            .name("Red")
            .order(1)
            .build();

        OptionValue blue = OptionValue.builder()
            .sid(202L)
            .name("Blue")
            .order(2)
            .build();

        OptionValue green = OptionValue.builder()
            .sid(203L)
            .name("Green")
            .order(3)
            .build();

        // Create color option field
        OptionField colorField = OptionField.builder()
            .pid(200L)
            .name("Color")
            .order(2)
            .values(Arrays.asList(red, blue, green))
            .build();

        // Create all combinations
        List<OptionSetting> settings = new ArrayList<>();
        for (OptionValue size : sizeField.getValues()) {
            for (OptionValue color : colorField.getValues()) {
                OptionFieldValue sizeValue = OptionFieldValue.builder()
                    .pid(sizeField.getPid())
                    .sid(size.getSid())
                    .build();

                OptionFieldValue colorValue = OptionFieldValue.builder()
                    .pid(colorField.getPid())
                    .sid(color.getSid())
                    .build();

                OptionSetting setting = OptionSetting.builder()
                    .skuCode(String.format("TSHIRT-001-%s-%s", size.getName(), color.getName()))
                    .fields(Arrays.asList(sizeValue, colorValue))
                    .build();

                settings.add(setting);
            }
        }

        // Create product options
        ProductOption productOption = ProductOption.builder()
            .optionFields(Arrays.asList(sizeField, colorField))
            .optionSettings(settings)
            .build();

        // Create final product
        return Product.builder()
            .id(1L)
            .productCode("TSHIRT-001")
            .isPrimary(true)
            .productOption(productOption)
            .build();
    }
}
