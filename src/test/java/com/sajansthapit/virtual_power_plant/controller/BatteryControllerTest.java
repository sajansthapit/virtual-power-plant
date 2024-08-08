package com.sajansthapit.virtual_power_plant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sajansthapit.virtual_power_plant.dto.BatteryDto;
import com.sajansthapit.virtual_power_plant.dto.request.BatteryFilterRequest;
import com.sajansthapit.virtual_power_plant.dto.request.BatteryRequest;
import com.sajansthapit.virtual_power_plant.dto.response.BatteryFilterResponse;
import com.sajansthapit.virtual_power_plant.dto.response.BatterySaveResponse;
import com.sajansthapit.virtual_power_plant.service.impl.BatteryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BatteryControllerTest {

    @Mock
    private BatteryService batteryService;

    @InjectMocks
    private BatteryController batteryController;

    @InjectMocks
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(batteryController)
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void saveAllTestCase() throws Exception {
        List<BatteryDto> batteries = List.of(
                new BatteryDto("abc Battery", "10000", 100),
                new BatteryDto("def Battery", "10003", 104),
                new BatteryDto("xyz Battery", "10005", 105)
        );
        BatteryRequest batteryRequest = new BatteryRequest(batteries);

        BatterySaveResponse batterySaveResponse = new BatterySaveResponse("Total of 3 batteries saved successfully");
        when(batteryService.saveAll(isA(BatteryRequest.class)))
                .thenReturn(batterySaveResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/battery/save-all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryRequest))
        ).andExpect(status().isCreated());

        verify(batteryService, Mockito.times(1)).saveAll(batteryRequest);
    }

    @Test
    public void filterTestCase() throws Exception {
        List<BatteryDto> batteries = List.of(
                new BatteryDto("abc Battery", "10000", 100),
                new BatteryDto("def Battery", "10003", 104),
                new BatteryDto("xyz Battery", "10005", 105)
        );

        BatteryFilterResponse batteryFilterResponse = new BatteryFilterResponse(
                "Batteries list fetched successfully",
                batteries,
                3,
                309,
                100.0
                );

        BatteryFilterRequest batteryFilterRequest = new BatteryFilterRequest("10000", "10005");

        when(batteryService.filter(isA(BatteryFilterRequest.class)))
                .thenReturn(batteryFilterResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/battery/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryFilterRequest))
        ).andExpect(status().isFound());

        verify(batteryService, Mockito.times(1)).filter(batteryFilterRequest);

    }

}