package com.sajansthapit.virtual_power_plant.repository;import com.sajansthapit.virtual_power_plant.model.Battery;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;@Repositorypublic interface BatteryRepository extends JpaRepository<Battery, Long> {}