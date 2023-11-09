package com.barbzdev.hospitalsimulator.domain.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Set;

public interface PreventDeathRepository {
	Boolean hasDeathPrevention(HealthStateEnum healthState, Set<DrugEnum> drugs);
}
