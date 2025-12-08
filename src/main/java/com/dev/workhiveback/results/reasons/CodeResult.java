package com.dev.workhiveback.results.reasons;

import com.dev.workhiveback.entities.CodeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CodeResult {
    private boolean result;
    List<CodeEntity> codes;
}
