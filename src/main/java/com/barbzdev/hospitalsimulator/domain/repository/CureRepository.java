package com.barbzdev.hospitalsimulator.domain.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;

import java.util.List;

public interface CureRepository {
	List<DrugEnum> findBy(HealthStateEnum healthState);
}
