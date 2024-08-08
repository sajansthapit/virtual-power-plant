package com.sajansthapit.virtual_power_plant.service.impl;


import com.sajansthapit.virtual_power_plant.dto.BatteryDto;
import com.sajansthapit.virtual_power_plant.dto.request.BatteryFilterRequest;
import com.sajansthapit.virtual_power_plant.dto.request.BatteryRequest;
import com.sajansthapit.virtual_power_plant.dto.response.BatterySaveResponse;
import com.sajansthapit.virtual_power_plant.exception_handler.exceptions.InvalidPostCodeRangeException;
import com.sajansthapit.virtual_power_plant.model.Battery;
import com.sajansthapit.virtual_power_plant.repository.BatteryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BatteryServiceTest {

    @Mock
    private BatteryRepository batteryRepository;

    @InjectMocks
    private BatteryService batteryService;

    List<BatteryDto> validBatteryDtoList;

    List<Battery> savedBattery;

    @Before
    public void init() {
        validBatteryDtoList = List.of(
                new BatteryDto("abc Battery", "10000", 100),
                new BatteryDto("def Battery", "10003", 104),
                new BatteryDto("xyz Battery", "10005", 105)
        );

        savedBattery = List.of(
                Battery.builder()
                        .id(1)
                        .name("abc Battery")
                        .postCode("10000")
                        .wattCapacity(100)
                        .build(),
                Battery.builder()
                        .id(2)
                        .name("def Battery")
                        .postCode("10003")
                        .wattCapacity(104)
                        .build(),
                Battery.builder()
                        .id(3)
                        .name("xyz Battery")
                        .postCode("10005")
                        .wattCapacity(105)
                        .build()
        );
    }

    /**
     * Method to test saveAll method that successfully persists all data in the database
     * GIVEN: List of valid batteries
     * WHEN: saveAll method is invoked
     * THEN: Batteries are persisted to database
     */
    @Test
    public void saveAllTestCase1() {
        BatterySaveResponse batterySaveResponse = new BatterySaveResponse("Total of 3 batteries saved successfully.");
        BatteryRequest batteryRequest = new BatteryRequest(validBatteryDtoList);
        Assert.assertEquals(batteryService.saveAll(batteryRequest).message(), batterySaveResponse.message());
    }

    /**
     * Method to test saveAll method when duplicate data is present
     * GIVEN: List of batteries with repeated data
     * WHEN: saveAll method is invoked
     * THEN: Batteries are persisted to database but ignore duplicate data
     */
    @Test
    public void saveAllTestCase2() {
        List<BatteryDto> validBatteryDtoListWithRepeatedData = List.of(
                new BatteryDto("abc Battery", "10000", 100)
        );
        Battery repeatedBattery = Battery.builder()
                .id(1)
                .name("abc Battery")
                .postCode("10000")
                .wattCapacity(100)
                .build();

        when(batteryRepository.findByNameAndPostCodeAndWattCapacity(isA(String.class), isA(String.class), isA(Integer.class)))
                .thenReturn(Optional.of(repeatedBattery));

        BatterySaveResponse batterySaveResponse = new BatterySaveResponse("Total of 0 batteries saved successfully. Some errors were found: [Battery with name abc Battery and 10000 of postcode and 100 watt capacity already exists in database]");
        BatteryRequest batteryRequest = new BatteryRequest(validBatteryDtoListWithRepeatedData);
        Assert.assertEquals(batteryService.saveAll(batteryRequest).message(), batterySaveResponse.message());
    }

    /**
     * Method to test filter method when post code range is valid
     * GIVEN: Valid post code range is provided
     * WHEN: Filter method is invoked
     * THEN: BatteryFilterResponse is returned
     */
    @Test
    public void filterTestCase1() {
        when(batteryRepository.findByPostCodeRange(isA(Integer.class), isA(Integer.class)))
                .thenReturn(savedBattery);
        BatteryFilterRequest batteryFilterRequest = new BatteryFilterRequest("10000", "10005");
        Assert.assertEquals(batteryService.filter(batteryFilterRequest).message(), "Batteries list fetched successfully");
    }


    /**
     * Method to test filter method when post code range is invalid
     * GIVEN: Invalid post code range is provided
     * WHEN: Filter method is invoked
     * THEN: InvalidPostCodeRangeException is thrown
     */
    @Test
    public void filterTestCase2(){
        BatteryFilterRequest batteryFilterRequest = new BatteryFilterRequest("10005", "10000");
        Assertions.assertThrows(InvalidPostCodeRangeException.class, () -> batteryService.filter(batteryFilterRequest));
    }
}