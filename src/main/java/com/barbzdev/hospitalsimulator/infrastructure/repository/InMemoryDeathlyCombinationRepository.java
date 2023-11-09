package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.repository.DeathlyCombinationRepository;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryDeathlyCombinationRepository implements DeathlyCombinationRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Set<Set<DrugEnum>> inMemoryDeathlyCombinations;

	public InMemoryDeathlyCombinationRepository() {
		this.inMemoryDeathlyCombinations = new HashSet<>();
		this.inMemoryDeathlyCombinations.add(Set.of(DrugEnum.P, DrugEnum.AS));
	}

	@Override
	public Boolean hasDeathlyCombination(Set<DrugEnum> drugs) {
		logger.debug("Checking drugs::{} are a deathly combination for patients", drugs);
		return inMemoryDeathlyCombinations.stream()
			.anyMatch(drugs::containsAll);
	}
}
