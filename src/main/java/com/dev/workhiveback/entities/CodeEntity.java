package com.dev.workhiveback.entities;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "code")
public class CodeEntity {
    private String code;
    private String displayText;
}
