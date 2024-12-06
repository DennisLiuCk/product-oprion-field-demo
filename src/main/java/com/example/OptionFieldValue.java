package com.example;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * OptionFieldValue e.g. optionFieldId and optionValueId
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionFieldValue {
    private Long pid;
    private Long sid;
}
