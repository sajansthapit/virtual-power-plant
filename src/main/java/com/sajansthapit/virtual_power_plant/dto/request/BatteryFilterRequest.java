package com.sajansthapit.virtual_power_plant.dto.request;

import com.sajansthapit.virtual_power_plant.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record BatteryFilterRequest(
        @NotEmpty(message = "Post Code from value must not be empty")
        @Pattern(regexp = Constants.POSTCODE_PATTERN, message = "Post Code from must contain 5 digits")
        String postCodeFrom,
        @NotEmpty(message = "Post Code to value must not be empty")
        @Pattern(regexp = Constants.POSTCODE_PATTERN, message = "Post Code to must contain 5 digits")
        String postCodeTo
) {
}
