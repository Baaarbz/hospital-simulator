package com.barbzdev.hospitalsimulator.domain.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Set;

public interface HealthStateRepository {
	Boolean hasCure(HealthStateEnum healthState, Set<DrugEnum> drugs);


	Boolean preventsDeath(HealthStateEnum healthState, Set<DrugEnum> drugs);
}
