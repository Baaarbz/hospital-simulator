package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.CureRepository;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class InMemoryCureRepository implements CureRepository {

	private final Map<HealthStateEnum, Set<DrugEnum>> inMemoryDiseaseCures;

	public InMemoryCureRepository() {
		this.inMemoryDiseaseCures = new EnumMap<>(HealthStateEnum.class);
		this.inMemoryDiseaseCures.put(HealthStateEnum.F, Set.of(DrugEnum.AS, DrugEnum.P));
		this.inMemoryDiseaseCures.put(HealthStateEnum.T, Set.of(DrugEnum.AN));
	}

	@Override
	public Boolean hasCure(HealthStateEnum healthState, Set<DrugEnum> drugs) {
		if (inMemoryDiseaseCures.containsKey(healthState)) {
			Set<DrugEnum> drugsThatCanCure = inMemoryDiseaseCures.get(healthState);

			return !drugs.isEmpty() && drugs.stream().anyMatch(drugsThatCanCure::contains);
		}
		return false;
	}
}
