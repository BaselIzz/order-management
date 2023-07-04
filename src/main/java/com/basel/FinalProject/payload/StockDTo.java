package com.basel.FinalProject.payload;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDTo {
    @ApiModelProperty(value = "Stock ID", example = "1001")

    private Long id;
    @ApiModelProperty(value = "product ID", example = "1001")

    private Long productId;
    @ApiModelProperty(value = "quantity", example = "123")

    private int quantity;
    @ApiModelProperty(value = "updatedAt", example = "2000-5-4")

    private LocalDate updatedAt;
}
