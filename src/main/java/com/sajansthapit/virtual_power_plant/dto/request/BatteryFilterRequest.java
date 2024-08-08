package com.sajansthapit.virtual_power_plant.dto.request;

public record BatteryFilterRequest(
        String postCodeFrom,
        String postCodeTo
) {
}
