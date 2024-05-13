package com.example.ecommerceonebox.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer Id;
    private String description;
    private Integer amount;
}