package com.basel.FinalProject.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "Product Order  model information")

public class ProductOrderDTo {
    @ApiModelProperty(value = "ProductOrder ID", example = "1001")

    private Long id;
    @ApiModelProperty(value = "Orderid ", example = "1001")

    private Long orderId;
    @ApiModelProperty(value = "ProductId ", example = "1001")
    private Long productId;
    @ApiModelProperty(value = "quantity ", example = "100")
    private int quantity;
    @ApiModelProperty(value = "price ", example = "100.5")

    private double price;
    @ApiModelProperty(value = "vat ", example = "100.5")
    private double vat;
}
