package com.barbzdev.hospitalsimulator.infrastructure.printer;

import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Map;

public class TerminalResponsePrinter {

	public TerminalResponsePrinter() {
	}

	public void print(Map<HealthStateEnum, Integer> patientHealthStates) {
		final String messageFormat = "F:%x,H:%x,D:%x,T:%x,X:%x";

		String message = String.format(messageFormat,
			patientHealthStates.get(HealthStateEnum.F),
			patientHealthStates.get(HealthStateEnum.H),
			patientHealthStates.get(HealthStateEnum.D),
			patientHealthStates.get(HealthStateEnum.T),
			patientHealthStates.get(HealthStateEnum.X)
		);

		System.out.print(message);
	}
}
