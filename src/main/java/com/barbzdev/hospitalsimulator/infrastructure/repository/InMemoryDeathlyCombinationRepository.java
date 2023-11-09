package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.repository.DeathlyCombinationRepository;
import java.util.HashSet;
import java.util.Set;

public class InMemoryDeathlyCombinationRepository implements DeathlyCombinationRepository {

	private final Set<Set<DrugEnum>> inMemoryDeathlyCombinations;

	public InMemoryDeathlyCombinationRepository() {
		this.inMemoryDeathlyCombinations = new HashSet<>();
		this.inMemoryDeathlyCombinations.add(Set.of(DrugEnum.P, DrugEnum.AS));
	}

	@Override
	public Boolean hasDeathlyCombination(Set<DrugEnum> drugs) {
		return inMemoryDeathlyCombinations.stream()
			.anyMatch(drugs::containsAll);
	}
}
