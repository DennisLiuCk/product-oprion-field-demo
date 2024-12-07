package com.example.v2;

/**
 * Builder class for creating ProductVariant instances
 */
public class ProductVariantBuilder {
    private final ProductVariant product = new ProductVariant();

    public ProductVariantBuilder withProductCode(String productCode) {
        product.setProductCode(productCode);
        return this;
    }

    public ProductVariantBuilder withSkuCode(String skuCode) {
        product.setSkuCode(skuCode);
        return this;
    }

    public ProductVariantBuilder withOptionGroup(VariantOptionGroup group) {
        product.addOptionGroup(group);
        return this;
    }

    public ProductVariant build() {
        return product;
    }
}
