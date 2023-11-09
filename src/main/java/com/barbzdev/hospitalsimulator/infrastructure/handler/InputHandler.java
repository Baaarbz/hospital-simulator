package com.barbzdev.hospitalsimulator.infrastructure.handler;

import com.barbzdev.hospitalsimulator.application.DrugAdministrationService;
import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class InputHandler {

	private DrugAdministrationService drugAdministrationService;

	public void handle(String[] input) {
		if (input.length == 0) {
			// TODO Response with 0 in all cases
		} else {
			List<HealthStateEnum> healthStates = transformHealthStateInputFunction.apply(input[0]);
			List<DrugEnum> drugs = input.length == 2
				? transformDrugInputFunction.apply(input[1])
				: Collections.emptyList();

		}
	}

	private final Function<String, List<HealthStateEnum>> transformHealthStateInputFunction = healthStateInput -> Arrays
		.stream(healthStateInput.toUpperCase().split(","))
		.map(HealthStateEnum::valueOf)
		.toList();

	private final Function<String, List<DrugEnum>> transformDrugInputFunction = drugsInput -> Arrays
		.stream(drugsInput.toUpperCase().split(","))
		.map(DrugEnum::valueOf)
		.toList();
}
