package com.barbzdev.hospitalsimulator.infrastructure.repository.entity;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Set;

public record DrugCombinationEffectEntity(Set<DrugEnum> drugCombination, HealthStateEnum effect) {
}
