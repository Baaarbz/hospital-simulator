package com.barbzdev.hospitalsimulator.application;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.CureRepository;
import com.barbzdev.hospitalsimulator.domain.repository.DeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.domain.repository.SideEffectRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DrugAdministrationService {

	private CureRepository cureRepository;
	private DeathlyCombinationRepository deathlyCombinationRepository;
	private SideEffectRepository sideEffectRepository;

	public void execute(List<HealthStateEnum> healthStates, List<DrugEnum> drugs) {

	}
}
