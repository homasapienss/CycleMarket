package com.example.cyclemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateRequest {
    private String productName;
    private String productDescription;
    private Integer productPrice;
    private Long manufacturerId;
    private MultipartFile image;
}
