package com.barbzdev.hospitalsimulator.domain.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;

import java.util.List;

public interface DeathlyCombinationRepository {

	Boolean findBy(List<DrugEnum> drugs);
}
