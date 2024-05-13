package com.example.ecommerceonebox.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 1, message = "Id must be greater than or equal to 1")
    @Max(value = 100, message = "Id must be less than or equal to 100")
    private Integer Id;

    @Valid
    @NotEmpty
    @NotNull
    private HashMap<Integer, ProductDTO> products = new HashMap<>();
}

