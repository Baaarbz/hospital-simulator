package com.barbzdev.hospitalsimulator.infrastructure.printer;

import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Map;

public class TerminalResponsePrinter {

	public TerminalResponsePrinter() {
	}

	public void print(Map<HealthStateEnum, Integer> patientHealthStates) {
		final String messageFormat = "F:%x,H:%x,D:%x,T:%x,X:%x";

		String message = String.format(messageFormat,
			patientHealthStates.getOrDefault(HealthStateEnum.F, 0),
			patientHealthStates.getOrDefault(HealthStateEnum.H, 0),
			patientHealthStates.getOrDefault(HealthStateEnum.D, 0),
			patientHealthStates.getOrDefault(HealthStateEnum.T, 0),
			patientHealthStates.getOrDefault(HealthStateEnum.X, 0)
		);

		System.out.print(message);
	}
}
