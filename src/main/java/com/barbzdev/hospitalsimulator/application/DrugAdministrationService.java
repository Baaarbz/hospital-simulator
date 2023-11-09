package com.barbzdev.hospitalsimulator.application;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.CureRepository;
import com.barbzdev.hospitalsimulator.domain.repository.DeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.domain.repository.PreventDeathRepository;
import com.barbzdev.hospitalsimulator.domain.repository.SideEffectRepository;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DrugAdministrationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final CureRepository cureRepository;
	private final DeathlyCombinationRepository deathlyCombinationRepository;
	private final SideEffectRepository sideEffectRepository;
	private final PreventDeathRepository preventDeathRepository;

	public DrugAdministrationService(CureRepository cureRepository,
																	 DeathlyCombinationRepository deathlyCombinationRepository,
																	 SideEffectRepository sideEffectRepository,
																	 PreventDeathRepository preventDeathRepository) {
		this.cureRepository = cureRepository;
		this.deathlyCombinationRepository = deathlyCombinationRepository;
		this.sideEffectRepository = sideEffectRepository;
		this.preventDeathRepository = preventDeathRepository;
	}

	public Map<HealthStateEnum, Integer> execute(List<HealthStateEnum> healthStates, Set<DrugEnum> availableDrugs) {
		Map<HealthStateEnum, Integer> healthStatesAfterApplyingDrugs = new EnumMap<>(HealthStateEnum.class);

		if (hasDeathlyCombination(availableDrugs)) {
			logger.info("Combination of drugs are mortal::{}", availableDrugs);
			healthStatesAfterApplyingDrugs.put(HealthStateEnum.X, healthStates.size());
			return healthStatesAfterApplyingDrugs;
		}

		for (HealthStateEnum healthState : healthStates) {
			HealthStateEnum updatedHealthState = applyDrugEffects(healthState, availableDrugs);
			healthStatesAfterApplyingDrugs.compute(updatedHealthState, (key, value) -> value == null ? 1 : value + 1);
			logger.info("Patient with health state::{} now is {}", healthState, updatedHealthState);
		}

		return healthStatesAfterApplyingDrugs;
	}

	private HealthStateEnum applyDrugEffects(HealthStateEnum healthState, Set<DrugEnum> availableDrugs) {
		HealthStateEnum updatedHealthState = healthState;

		if (isHealthyOrHasCure(healthState, availableDrugs)) {
			updatedHealthState = applyDrugsToHealthyPatientOrWithCure(availableDrugs);
		} else if (isDeadlyDisease(healthState)) {
			updatedHealthState = applyDrugsToPatientWithDeadlyDisease(healthState, availableDrugs);
		}

		return updatedHealthState;
	}

	private boolean hasDeathlyCombination(Set<DrugEnum> availableDrugs) {
		return deathlyCombinationRepository.hasDeathlyCombination(availableDrugs);
	}

	private boolean isHealthyOrHasCure(HealthStateEnum healthState, Set<DrugEnum> availableDrugs) {
		return healthState == HealthStateEnum.H || cureRepository.hasCure(healthState, availableDrugs);
	}

	private HealthStateEnum applyDrugsToHealthyPatientOrWithCure(Set<DrugEnum> availableDrugs) {
		Optional<HealthStateEnum> sideEffectStateOptional = sideEffectRepository.findBy(availableDrugs);
		sideEffectStateOptional.ifPresent(sideEffect -> logger.info("Drugs combination::{} has side effects causing::{}", availableDrugs, sideEffect));
		return sideEffectStateOptional.orElse(HealthStateEnum.H);
	}

	private boolean isDeadlyDisease(HealthStateEnum healthState) {
		return preventDeathRepository.isDeadlyDisease(healthState);
	}

	private HealthStateEnum applyDrugsToPatientWithDeadlyDisease(HealthStateEnum healthState, Set<DrugEnum> availableDrugs) {
		if (preventsDead(healthState, availableDrugs)) {
			logger.info("Patient has the right drugs to prevent dying of::{}", healthState);
			return healthState;
		}

		logger.info("Patient dies of::{} because does not receive the right drugs", healthState);
		return HealthStateEnum.X;
	}

	private boolean preventsDead(HealthStateEnum healthState, Set<DrugEnum> availableDrugs) {
		return preventDeathRepository.preventsDeath(healthState, availableDrugs);
	}
}
