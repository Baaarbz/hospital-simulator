package com.barbzdev.hospitalsimulator.infrastructure.handler;

import com.barbzdev.hospitalsimulator.application.DrugAdministrationService;
import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.infrastructure.printer.TerminalResponsePrinter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final DrugAdministrationService drugAdministrationService;
	private final TerminalResponsePrinter terminalResponsePrinter;

	public InputHandler(DrugAdministrationService drugAdministrationService, TerminalResponsePrinter terminalResponsePrinter) {
		this.drugAdministrationService = drugAdministrationService;
		this.terminalResponsePrinter = terminalResponsePrinter;
	}

	public void handle(String[] input) {
		List<HealthStateEnum> healthStates = Collections.emptyList();
		Set<DrugEnum> availableDrugs = Collections.emptySet();

		if (input.length != 0) {
			healthStates = transformHealthStateInputFunction.apply(input[0]);
			availableDrugs = input.length == 2
				? transformDrugInputFunction.apply(input[1])
				: Collections.emptySet();
		}

		logger.info("Input received - Patients::{}  Available drugs::{}", healthStates, availableDrugs);
		Map<HealthStateEnum, Integer> treatedPatients = drugAdministrationService.execute(healthStates, availableDrugs);
		terminalResponsePrinter.print(treatedPatients);
	}

	private final Function<String, List<HealthStateEnum>> transformHealthStateInputFunction = healthStatesInput -> Arrays
		.stream(healthStatesInput.toUpperCase().split(","))
		.map(healthStateInput -> {
			try {
				return HealthStateEnum.valueOf(healthStateInput);
			} catch (IllegalArgumentException e) {
				logger.warn("Ignoring patient with health state::{} is not available in the list of health state {}", healthStateInput, HealthStateEnum.values());
				return null;
			}
		})
		.filter(Objects::nonNull)
		.toList();

	private final Function<String, Set<DrugEnum>> transformDrugInputFunction = drugsInput -> Arrays
		.stream(drugsInput.toUpperCase().split(","))
		.filter(i -> !i.isBlank())
		.map(drugInput -> {
			try {
				return DrugEnum.valueOf(drugInput);
			} catch (IllegalArgumentException e) {
				logger.warn("Ignoring drug::{} is not available in the list of drugs {}", drugInput, DrugEnum.values());
				return null;
			}
		})
		.filter(Objects::nonNull)
		.collect(Collectors.toSet());
}
