package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.SideEffectRepository;

import java.util.List;

public class InMemorySideEffectRepository implements SideEffectRepository {
	@Override
	public HealthStateEnum findBy(List<DrugEnum> drugs) {
		// TODO("not implemented yet")
		return null;
	}
}
