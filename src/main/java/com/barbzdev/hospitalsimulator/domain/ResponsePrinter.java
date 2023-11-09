package com.barbzdev.hospitalsimulator.domain;

import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;

import java.util.List;

public interface ResponsePrinter {
	void print(List<HealthStateEnum> patientHealthStates);
}
