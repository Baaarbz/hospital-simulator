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

public class DrugAdministrationService {

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
			healthStatesAfterApplyingDrugs.put(HealthStateEnum.X, healthStates.size());
			return healthStatesAfterApplyingDrugs;
		}

		for (HealthStateEnum healthState : healthStates) {
			HealthStateEnum updatedHealthState = applyDrugEffects(healthState, availableDrugs);
			healthStatesAfterApplyingDrugs.compute(updatedHealthState, (key, value) -> value == null ? 1 : value + 1);
		}

		return healthStatesAfterApplyingDrugs;
	}

	private HealthStateEnum applyDrugEffects(HealthStateEnum healthState, Set<DrugEnum> availableDrugs) {
		HealthStateEnum updatedHealthState = healthState;

		if (isHealthyOrHasCure(healthState, availableDrugs)) {
			Optional<HealthStateEnum> sideEffectStateOptional = sideEffectRepository.findBy(availableDrugs);
			updatedHealthState = sideEffectStateOptional.orElse(HealthStateEnum.H);
		} else if (isDeadlyDisease(healthState)) {
			updatedHealthState = preventDeathRepository.preventsDeath(healthState, availableDrugs)
				? healthState
				: HealthStateEnum.X;
		}

		return updatedHealthState;
	}

	private boolean hasDeathlyCombination(Set<DrugEnum> availableDrugs) {
		return deathlyCombinationRepository.hasDeathlyCombination(availableDrugs);
	}

	private boolean isHealthyOrHasCure(HealthStateEnum healthState, Set<DrugEnum> availableDrugs) {
		return healthState == HealthStateEnum.H || cureRepository.hasCure(healthState, availableDrugs);
	}

	private boolean isDeadlyDisease(HealthStateEnum healthState) {
		return preventDeathRepository.isDeadlyDisease(healthState);
	}
}
