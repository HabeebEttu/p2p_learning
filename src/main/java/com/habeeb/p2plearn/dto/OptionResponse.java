package com.habeeb.p2plearn.dto;

public record OptionResponse(
        Long id,
        String optionText,
        Integer optionIndex
) {}