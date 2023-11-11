package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class InMemoryDrugCombinationEffectRepositoryTest {

	@InjectMocks
	private InMemoryDrugCombinationEffectRepository drugCombinationEffectRepository;

	@Test
	public void GIVEN_setOfDrugs_WITH_sideEffect_THEN_findSideEffectBy_SHOULD_returnSideEffect() {
		Set<DrugEnum> drugsWithSideEffect = Set.of(DrugEnum.I, DrugEnum.AN);

		Optional<HealthStateEnum> sideEffect = drugCombinationEffectRepository.findSideEffectBy(drugsWithSideEffect);

		assertTrue(sideEffect.isPresent());
		assertEquals(HealthStateEnum.F, sideEffect.get());
	}

	@Test
	public void GIVEN_setOfDrugs_WITH_deadlyEffect_THEN_findSideEffectBy_SHOULD_returnEmpty() {
		Set<DrugEnum> drugsWithDeadlyEffect = Set.of(DrugEnum.P, DrugEnum.AS);

		Optional<HealthStateEnum> sideEffect = drugCombinationEffectRepository.findSideEffectBy(drugsWithDeadlyEffect);

		assertTrue(sideEffect.isEmpty());
	}

	@Test
	public void GIVEN_setOfDrugs_WITHOUT_sideEffect_THEN_findSideEffectBy_SHOULD_returnEmpty() {
		Set<DrugEnum> drugsWithoutSideEffect = Set.of(DrugEnum.AN, DrugEnum.AS);

		Optional<HealthStateEnum> sideEffect = drugCombinationEffectRepository.findSideEffectBy(drugsWithoutSideEffect);

		assertTrue(sideEffect.isEmpty());
	}

	@Test
	public void GIVEN_setOfDrugs_WITHOUT_deadlyEffect_THEN_isDeadlyCombination_SHOULD_returnFalse() {
		Set<DrugEnum> drugsWithoutDeadlyEffect = Set.of(DrugEnum.AN, DrugEnum.AS);

		Boolean isDeadlyCombination = drugCombinationEffectRepository.isDeadlyCombination(drugsWithoutDeadlyEffect);

		assertFalse(isDeadlyCombination);
	}

	@Test
	public void GIVEN_setOfDrugs_WITH_deadlyEffect_THEN_isDeadlyCombination_SHOULD_returnTrue() {
		Set<DrugEnum> drugsWithDeadlyEffect = Set.of(DrugEnum.P, DrugEnum.AS);

		Boolean isDeadlyCombination = drugCombinationEffectRepository.isDeadlyCombination(drugsWithDeadlyEffect);

		assertTrue(isDeadlyCombination);
	}
}
