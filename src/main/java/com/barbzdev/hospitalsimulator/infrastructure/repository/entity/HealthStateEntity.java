package com.barbzdev.hospitalsimulator.infrastructure.repository.entity;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Set;

public record HealthStateEntity(HealthStateEnum healthState, Boolean isDeadly, Set<DrugEnum> cure, Set<DrugEnum> preventDeath) {
}
