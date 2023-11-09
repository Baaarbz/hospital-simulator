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
		Map<HealthStateEnum, Integer> healthStatesAfterApplyingDrugs = initMapHealthStatesAfterApplyingDrugs();

		if (deathlyCombinationRepository.hasDeathlyCombination(availableDrugs)) {
			healthStatesAfterApplyingDrugs.put(HealthStateEnum.X, healthStates.size());
			return healthStatesAfterApplyingDrugs;
		}

		for (HealthStateEnum healthState : healthStates) {
			HealthStateEnum updatedPatientHealthState = healthState;

			if (healthState == HealthStateEnum.H || cureRepository.hasCure(healthState, availableDrugs)) {
				Optional<HealthStateEnum> sideEffectStateOptional = sideEffectRepository.findBy(availableDrugs);
				updatedPatientHealthState = sideEffectStateOptional.orElse(HealthStateEnum.H);
				healthStatesAfterApplyingDrugs.put(updatedPatientHealthState, 1 + healthStatesAfterApplyingDrugs.get(updatedPatientHealthState));
			} else if (!preventDeathRepository.hasDeathPrevention(healthState, availableDrugs)) {
				healthStatesAfterApplyingDrugs.put(HealthStateEnum.X, 1 + healthStatesAfterApplyingDrugs.get(HealthStateEnum.X));
			} else {
				healthStatesAfterApplyingDrugs.put(updatedPatientHealthState, 1 + healthStatesAfterApplyingDrugs.get(updatedPatientHealthState));
			}
		}
		return healthStatesAfterApplyingDrugs;
	}

	private Map<HealthStateEnum, Integer> initMapHealthStatesAfterApplyingDrugs() {
		Map<HealthStateEnum, Integer> healthStatesAfterApplyingDrugs = new EnumMap<>(HealthStateEnum.class);

		for (HealthStateEnum healthState : HealthStateEnum.values()) {
			healthStatesAfterApplyingDrugs.put(healthState, 0);
		}

		return healthStatesAfterApplyingDrugs;
	}

}
