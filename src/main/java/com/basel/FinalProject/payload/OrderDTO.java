package com.basel.FinalProject.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderDTO {
    @ApiModelProperty(value = "Orderid", example = "1011")

    private Long id;
    @ApiModelProperty(value = "orderDate", example = "2000-5-4")

    private LocalDate orderDate;
    @ApiModelProperty(value = "customerId", example = "1011")

    private Long customerId;
    @ApiModelProperty(value = "productIds", example = "[1,2,3,4]")
    private List<Long> productIds;
}