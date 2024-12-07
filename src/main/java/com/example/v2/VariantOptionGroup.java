package com.example.v2;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Composite class that can contain other VariantOptions
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class VariantOptionGroup extends VariantOption {
    private final List<VariantOption> options = new ArrayList<>();

    public VariantOptionGroup(Long id, String name, Integer order) {
        super(id, name, order);
    }

    public void addOption(VariantOption option) {
        options.add(option);
    }

    public List<VariantOption> getSortedOptions() {
        Collections.sort(options);
        return Collections.unmodifiableList(options);
    }
}
