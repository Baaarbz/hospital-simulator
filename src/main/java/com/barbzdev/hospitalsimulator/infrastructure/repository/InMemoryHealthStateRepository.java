package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.HealthStateRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.entity.HealthStateEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryHealthStateRepository implements HealthStateRepository {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final List<HealthStateEntity> inMemoryHealthStates;

	public InMemoryHealthStateRepository() {
		this.inMemoryHealthStates = new ArrayList<>();
		this.inMemoryHealthStates.add(new HealthStateEntity(HealthStateEnum.F, false, Set.of(DrugEnum.AS, DrugEnum.P), Collections.emptySet()));
		this.inMemoryHealthStates.add(new HealthStateEntity(HealthStateEnum.T, false, Set.of(DrugEnum.AN), Collections.emptySet()));
		this.inMemoryHealthStates.add(new HealthStateEntity(HealthStateEnum.D, true, Collections.emptySet(), Set.of(DrugEnum.I)));
	}

	@Override
	public Boolean hasCure(HealthStateEnum healthState, Set<DrugEnum> drugs) {
		logger.debug("Checking healthState::{} can be cured with drugs::{}", healthState, drugs);
		return inMemoryHealthStates.stream()
			.anyMatch(hs -> hs.healthState() == healthState && hs.cure().stream().anyMatch(drugs::contains));
	}

	@Override
	public Boolean preventsDeath(HealthStateEnum healthState, Set<DrugEnum> drugs) {
		logger.debug("Checking if is deadly healthState::{} and if yes can prevent the death with drugs::{}", healthState, drugs);
		return inMemoryHealthStates.stream()
			.filter(hs -> healthState == hs.healthState())
			.anyMatch(hs -> !hs.isDeadly() || hs.preventDeath().stream().anyMatch(drugs::contains));
	}
}
