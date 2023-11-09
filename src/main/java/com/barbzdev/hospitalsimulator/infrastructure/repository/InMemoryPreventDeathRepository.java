package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.PreventDeathRepository;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class InMemoryPreventDeathRepository implements PreventDeathRepository {

	private final Map<HealthStateEnum, Set<DrugEnum>> inMemoryDrugPreventDeath;

	public InMemoryPreventDeathRepository() {
		this.inMemoryDrugPreventDeath = new EnumMap<>(HealthStateEnum.class);
		this.inMemoryDrugPreventDeath.put(HealthStateEnum.D, Set.of(DrugEnum.I));
	}

	@Override
	public Boolean hasDeathPrevention(HealthStateEnum healthState, Set<DrugEnum> drugs) {
		if (inMemoryDrugPreventDeath.containsKey(healthState)) {
			Set<DrugEnum> drugsCanPreventDeath = inMemoryDrugPreventDeath.get(healthState);

			return !drugs.isEmpty() && drugs.stream().anyMatch(drugsCanPreventDeath::contains);
		}
		return true;
	}
}
