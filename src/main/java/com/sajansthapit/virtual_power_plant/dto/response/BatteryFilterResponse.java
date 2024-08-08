package com.sajansthapit.virtual_power_plant.dto.response;

import com.sajansthapit.virtual_power_plant.dto.BatteryDto;

import java.util.List;

public record BatteryFilterResponse (
        String message,
        List<BatteryDto> batteries,
        int totalBatteries,
        int totalWattCapacity,
        double averageWattCapacity
) {
}
