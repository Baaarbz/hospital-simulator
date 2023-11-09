package com.barbzdev.hospitalsimulator.domain;

import java.util.List;

public interface ResponsePrinter {
	void print(List<HealthStateEnum> patientHealthStates);
}
