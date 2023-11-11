package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class InMemoryHealthStateRepositoryTest {

	@InjectMocks
	private InMemoryHealthStateRepository healthStateRepository;

	@Test
	public void GIVEN_healthState_WITH_cure_THEN_hasCure_SHOULD_returnTrue() {
		Set<DrugEnum> drugs = Set.of(DrugEnum.P);
		HealthStateEnum healthStateWithCure = HealthStateEnum.F;

		Boolean hasCure = healthStateRepository.hasCure(healthStateWithCure, drugs);

		assertTrue(hasCure);
	}

	@Test
	public void GIVEN_healthState_WITHOUT_cure_THEN_hasCure_SHOULD_returnFalse() {
		Set<DrugEnum> drugs = Set.of(DrugEnum.I);
		HealthStateEnum healthStateWithCure = HealthStateEnum.F;

		Boolean hasCure = healthStateRepository.hasCure(healthStateWithCure, drugs);

		assertFalse(hasCure);
	}

	@Test
	public void GIVEN_deadlyHealthState_WITH_deathPrevention_THEN_preventsDeath_SHOULD_returnTrue() {
		Set<DrugEnum> drugs = Set.of(DrugEnum.I);
		HealthStateEnum healthStateWithCure = HealthStateEnum.D;

		Boolean preventsDeath = healthStateRepository.preventsDeath(healthStateWithCure, drugs);

		assertTrue(preventsDeath);
	}

	@Test
	public void GIVEN_notDeadlyHealthState_WITHOUT_drugs_THEN_preventsDeath_SHOULD_returnTrue() {
		Set<DrugEnum> drugs = Set.of(DrugEnum.I);
		HealthStateEnum healthStateWithCure = HealthStateEnum.F;

		Boolean preventsDeath = healthStateRepository.preventsDeath(healthStateWithCure, drugs);

		assertTrue(preventsDeath);
	}

	@Test
	public void GIVEN_deadlyHealthState_WITHOUT_deathPrevention_THEN_preventsDeath_SHOULD_returnFalse() {
		Set<DrugEnum> drugs = Set.of(DrugEnum.P);
		HealthStateEnum healthStateWithCure = HealthStateEnum.D;

		Boolean preventsDeath = healthStateRepository.preventsDeath(healthStateWithCure, drugs);

		assertFalse(preventsDeath);
	}

}
