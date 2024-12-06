package com.example;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * OptionField e.g. Size, Color
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionField {
    private Long pid;
    private String name;
    private Integer order;
    private List<OptionValue> values;
}
