package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.PreventDeathRepository;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryPreventDeathRepository implements PreventDeathRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Map<HealthStateEnum, Set<DrugEnum>> inMemoryDrugPreventDeath;

	public InMemoryPreventDeathRepository() {
		this.inMemoryDrugPreventDeath = new EnumMap<>(HealthStateEnum.class);
		this.inMemoryDrugPreventDeath.put(HealthStateEnum.D, Set.of(DrugEnum.I));
	}

	@Override
	public Boolean isDeadlyDisease(HealthStateEnum healthState) {
		logger.debug("Checking if healthState::{} is deadly", healthState);
		return inMemoryDrugPreventDeath.containsKey(healthState);
	}

	@Override
	public Boolean preventsDeath(HealthStateEnum healthState, Set<DrugEnum> drugs) {
		logger.debug("Checking if deadly healthState::{} can prevent the death with drugs::{}", healthState, drugs);
		if (inMemoryDrugPreventDeath.containsKey(healthState)) {
			Set<DrugEnum> drugsCanPreventDeath = inMemoryDrugPreventDeath.get(healthState);

			return !drugs.isEmpty() && drugs.stream().anyMatch(drugsCanPreventDeath::contains);
		}
		return false;
	}
}
