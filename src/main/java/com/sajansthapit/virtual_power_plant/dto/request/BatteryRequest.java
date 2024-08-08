package com.sajansthapit.virtual_power_plant.dto.request;import com.sajansthapit.virtual_power_plant.dto.BatteryDto;import jakarta.validation.Valid;import jakarta.validation.constraints.NotEmpty;import java.util.List;public record BatteryRequest(        @NotEmpty(message = "Batteries List can't be empty")        List<@Valid BatteryDto> batteries) {}