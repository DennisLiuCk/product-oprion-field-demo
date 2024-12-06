package com.example;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * OptionValue e.g. Large, Small, Medium, Red, Blue, Green
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionValue {
    private Long sid;
    private String name;
    private Integer order;
}
