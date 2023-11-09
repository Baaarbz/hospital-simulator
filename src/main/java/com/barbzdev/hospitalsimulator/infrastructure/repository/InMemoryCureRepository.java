package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.repository.CureRepository;
import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;

import java.util.List;

public class InMemoryCureRepository implements CureRepository {
	@Override
	public List<DrugEnum> findBy(HealthStateEnum healthState) {
		// TODO("not implemented yet")
		return null;
	}
}
