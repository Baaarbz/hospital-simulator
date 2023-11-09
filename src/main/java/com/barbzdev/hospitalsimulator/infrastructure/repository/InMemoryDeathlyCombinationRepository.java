package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.repository.DeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.domain.DrugEnum;

import java.util.List;

public class InMemoryDeathlyCombinationRepository implements DeathlyCombinationRepository {
	@Override
	public Boolean findBy(List<DrugEnum> drugs) {
		// TODO("not implemented yet")
		return null;
	}
}
