package com.barbzdev.hospitalsimulator.domain.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;

import java.util.List;
import java.util.Set;

public interface DeathlyCombinationRepository {

	Boolean hasDeathlyCombination(Set<DrugEnum> drugs);
}
