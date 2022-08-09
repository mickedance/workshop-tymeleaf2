package com.example.workshoptymeleaf2.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductForm {
    private int id;
    @NotEmpty
    @NotNull
    @Size(min =2, max=100)
    private String name;
    private int categoryId;
    private LocalDate createDate;

}
