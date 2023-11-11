package com.barbzdev.hospitalsimulator.application;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.component.SpaghettiMonster;
import com.barbzdev.hospitalsimulator.domain.repository.DrugCombinationEffectRepository;
import com.barbzdev.hospitalsimulator.domain.repository.HealthStateRepository;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DrugAdministrationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final HealthStateRepository healthStateRepository;
	private final DrugCombinationEffectRepository drugCombinationEffectRepository;
	private final SpaghettiMonster spaghettiMonster;

	public DrugAdministrationService(HealthStateRepository healthStateRepository,
																	 DrugCombinationEffectRepository drugCombinationEffectRepository,
																	 SpaghettiMonster spaghettiMonster) {
		this.healthStateRepository = healthStateRepository;
		this.drugCombinationEffectRepository = drugCombinationEffectRepository;
		this.spaghettiMonster = spaghettiMonster;
	}

	public Map<HealthStateEnum, Integer> execute(List<HealthStateEnum> healthStates, Set<DrugEnum> availableDrugs) {
		Map<HealthStateEnum, Integer> healthStatesAfterApplyingDrugs = new EnumMap<>(HealthStateEnum.class);

		if (hasDeathlyCombination(availableDrugs)) {
			logger.info("Combination of drugs are mortal::{}", availableDrugs);
			healthStatesAfterApplyingDrugs.put(HealthStateEnum.X, healthStates.size());
			invokeFlyingSpaghettiMonster(healthStatesAfterApplyingDrugs);
			return healthStatesAfterApplyingDrugs;
		}

		for (HealthStateEnum healthState : healthStates) {
			HealthStateEnum updatedHealthState = healthState == HealthStateEnum.X
				? HealthStateEnum.X
				: applyDrugEffects(healthState, availableDrugs);
			healthStatesAfterApplyingDrugs.compute(updatedHealthState, (key, value) -> value == null ? 1 : value + 1);
			logger.info("Patient with health state::{} now is {}", healthState, updatedHealthState);
		}

		invokeFlyingSpaghettiMonster(healthStatesAfterApplyingDrugs);

		return healthStatesAfterApplyingDrugs;
	}

	private HealthStateEnum applyDrugEffects(HealthStateEnum healthState, Set<DrugEnum> availableDrugs) {
		if (isHealthyOrHasCure(healthState, availableDrugs)) {
			return applyDrugsToHealthyPatientOrWithCure(availableDrugs);
		}

		if (healthStateRepository.preventsDeath(healthState, availableDrugs)) {
			logger.info("Patient with::{} prevents death with drugs::{}", healthState, availableDrugs);
			return healthState;
		}

		logger.info("Patient with::{} will die due to not receive right treatment", healthState);
		return HealthStateEnum.X;
	}

	private boolean hasDeathlyCombination(Set<DrugEnum> availableDrugs) {
		return drugCombinationEffectRepository.isDeadlyCombination(availableDrugs);
	}

	private boolean isHealthyOrHasCure(HealthStateEnum healthState, Set<DrugEnum> availableDrugs) {
		return healthState == HealthStateEnum.H || healthStateRepository.hasCure(healthState, availableDrugs);
	}

	private HealthStateEnum applyDrugsToHealthyPatientOrWithCure(Set<DrugEnum> availableDrugs) {
		Optional<HealthStateEnum> sideEffectStateOptional = drugCombinationEffectRepository.findSideEffectBy(availableDrugs);
		sideEffectStateOptional.ifPresent(sideEffect -> logger.info("Drugs combination::{} has side effects causing::{}", availableDrugs, sideEffect));
		return sideEffectStateOptional.orElse(HealthStateEnum.H);
	}

	private void invokeFlyingSpaghettiMonster(Map<HealthStateEnum, Integer> healthStatesAfterApplyingDrugs) {
		if (healthStatesAfterApplyingDrugs.containsKey(HealthStateEnum.X) && healthStatesAfterApplyingDrugs.get(HealthStateEnum.X) > 0) {
			logger.info("Invoking the Flying Spaghetti Monster...");

			if (spaghettiMonster.showsNoodlyPower()) {
				healthStatesAfterApplyingDrugs.compute(HealthStateEnum.X, (key, value) -> value == null ? 1 : value - 1);
				healthStatesAfterApplyingDrugs.compute(HealthStateEnum.H, (key, value) -> value == null ? 1 : value + 1);
				logger.info("OOHHHHH THIS IS A MIRACLE!! The Flying Spaghetti Monster decides to show his noodly power and had resurrected one death patient");
			}
		} else {
			logger.info("There are not death patients to invoke the Flying Spaghetti Monster");
		}
	}
}
