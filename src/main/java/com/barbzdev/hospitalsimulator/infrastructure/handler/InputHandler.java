package com.barbzdev.hospitalsimulator.infrastructure.handler;

import com.barbzdev.hospitalsimulator.application.DrugAdministrationService;
import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InputHandler {

	private final DrugAdministrationService drugAdministrationService;

	public InputHandler(DrugAdministrationService drugAdministrationService) {
		this.drugAdministrationService = drugAdministrationService;
	}

	public void handle(String[] input) {
		List<HealthStateEnum> healthStates = Collections.emptyList();
		Set<DrugEnum> drugs = Collections.emptySet();

		if (input.length != 0) {
			healthStates = transformHealthStateInputFunction.apply(input[0]);
			drugs = input.length == 2
				? transformDrugInputFunction.apply(input[1])
				: Collections.emptySet();
		}

		drugAdministrationService.execute(healthStates, drugs);
	}

	private final Function<String, List<HealthStateEnum>> transformHealthStateInputFunction = healthStateInput -> Arrays
		.stream(healthStateInput.toUpperCase().split(","))
		.map(HealthStateEnum::valueOf)
		.toList();

	private final Function<String, Set<DrugEnum>> transformDrugInputFunction = drugsInput -> Arrays
		.stream(drugsInput.toUpperCase().split(","))
		.map(DrugEnum::valueOf)
		.collect(Collectors.toSet());
}
