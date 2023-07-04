package com.basel.FinalProject.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@ApiModel(description = "Customer model information")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @ApiModelProperty(value = "Customer ID", example = "1001")
    private Long id;

    @ApiModelProperty(value = "Customer name", example = "Basel")
    @NotEmpty(message = "Customer name is required")
    private String firstName;

    @ApiModelProperty(value = "Customer lastname", example = "Izz")
    @NotEmpty(message = "Customer lastname is required")
    private String lastname;
    @ApiModelProperty(value = "Customer date of birth", example = "1990-01-01")
        private LocalDate bornAt;
    @ApiModelProperty(value = "orders", example = "[1,2,4,3]")

    private List<Long> orders;




}
