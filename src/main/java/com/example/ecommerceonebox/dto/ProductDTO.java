package com.example.ecommerceonebox.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotNull
    @Min(value = 1, message = "Id must be greater than or equal to 1")
    @Max(value = 100, message = "Id must be less than or equal to 100")
    @Positive
    private int Id;

    @NotEmpty
    @Size(min = 10, message = "Description should have at least 10 characters")
    private String description;

    @NotNull
    @Min(value = 1, message = "Id must be greater than or equal to 1")
    @Max(value = 100, message = "Id must be less than or equal to 100")
    @Positive
    private int amount;
}