package com.barbzdev.hospitalsimulator.domain.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Optional;
import java.util.Set;

public interface DrugCombinationEffectRepository {

	Optional<HealthStateEnum> findBy(Set<DrugEnum> drugs);

	Boolean isDeadlyCombination(Set<DrugEnum> drugs);
}
