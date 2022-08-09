package com.example.workshoptymeleaf2.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryForm {
    private int id;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;
}
