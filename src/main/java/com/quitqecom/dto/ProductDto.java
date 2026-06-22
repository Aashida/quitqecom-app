package com.quitqecom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDto(

@NotBlank(message = "Product name is mandatory")
String productName,

@NotBlank(message = "Description is mandatory")
String description,

@NotNull(message = "Price is mandatory")
@Min(value=1 , message = "Price must be greater than 0")
double price,

@NotNull(message = "Stock is mandatory")
@Min(value = 0, message = "Stock cannot be negative")
int stock,

@NotNull(message = "Category is mandatory")
String categoryName

) {

}
