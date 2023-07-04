package com.basel.FinalProject.payload;


import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@ApiModel(description = "Product model information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTo {
    @ApiModelProperty(value = "Product ID",example = "1101")
    private Long productId;

    @ApiModelProperty(value = "Product slug",example = "slug1")
    private String slug;

    @ApiModelProperty(value = "Product name",example = "Product1")
    private String productName;

    @ApiModelProperty(value = "Product reference",example = "Ref1")
    private String reference;

    @ApiModelProperty(value = "Product price",example = "100.5")
    private double price;

    @ApiModelProperty(value = "Product VAT",example = "100.5")
    private double vat;

    @ApiModelProperty(value = "Product stockable flag" ,example = "true")
    private boolean stockable;

    @ApiModelProperty(value = "Order ID",example = "1001")
    private Long orderId;
}