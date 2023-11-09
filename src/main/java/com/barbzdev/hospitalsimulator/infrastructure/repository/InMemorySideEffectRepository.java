package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.SideEffectRepository;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class InMemorySideEffectRepository implements SideEffectRepository {


	private final Map<HealthStateEnum, Set<DrugEnum>> inMemoryDrugsSideEffect;

	public InMemorySideEffectRepository() {
		this.inMemoryDrugsSideEffect = new EnumMap<>(HealthStateEnum.class);
		this.inMemoryDrugsSideEffect.put(HealthStateEnum.F, Set.of(DrugEnum.I, DrugEnum.AN));
	}

	@Override
	public Optional<HealthStateEnum> findBy(Set<DrugEnum> drugs) {
		for (HealthStateEnum causedHealthState : inMemoryDrugsSideEffect.keySet()) {
			if (drugs.containsAll(inMemoryDrugsSideEffect.get(causedHealthState))) {
				return Optional.of(causedHealthState);
			}
		}
		return Optional.empty();
	}
}
