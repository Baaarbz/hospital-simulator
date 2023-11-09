package com.barbzdev.hospitalsimulator.infrastructure.printer;

import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.ResponsePrinter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class TerminalResponsePrinter implements ResponsePrinter {
	@Override
	public void print(List<HealthStateEnum> patientHealthStates) {
		// TODO
	}
}
