package com.barbzdev.hospitalsimulator.application;


import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.component.SpaghettiMonster;
import com.barbzdev.hospitalsimulator.domain.repository.DrugCombinationEffectRepository;
import com.barbzdev.hospitalsimulator.domain.repository.HealthStateRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DrugAdministrationServiceTest {

	@Mock
	private HealthStateRepository healthStateRepository;

	@Mock
	private DrugCombinationEffectRepository drugCombinationEffectRepository;

	@Mock
	private SpaghettiMonster spaghettiMonster;

	@InjectMocks
	private DrugAdministrationService drugAdministrationService;


	@Test
	public void GIVEN_healthState_WITH_Cure_SHOULD_returnHealthy() {
		List<HealthStateEnum> healthStateInput = List.of(HealthStateEnum.T);
		Set<DrugEnum> drugsInput = Set.of(DrugEnum.AN);
		when(drugCombinationEffectRepository.isDeadlyCombination(any())).thenReturn(false);
		when(healthStateRepository.hasCure(any(), any())).thenReturn(true);
		when(drugCombinationEffectRepository.findSideEffectBy(any())).thenReturn(Optional.empty());

		Map<HealthStateEnum, Integer> response = drugAdministrationService.execute(healthStateInput, drugsInput);

		assertEquals(1, response.get(HealthStateEnum.H));
		assertNull(response.get(HealthStateEnum.T));
		assertNull(response.get(HealthStateEnum.X));
		assertNull(response.get(HealthStateEnum.F));
		assertNull(response.get(HealthStateEnum.D));
		InOrder inOrder = inOrder(drugCombinationEffectRepository, healthStateRepository);
		inOrder.verify(drugCombinationEffectRepository).isDeadlyCombination(drugsInput);
		inOrder.verify(healthStateRepository).hasCure(healthStateInput.get(0), drugsInput);
		inOrder.verify(drugCombinationEffectRepository).findSideEffectBy(drugsInput);
		verifyNoInteractions(spaghettiMonster);
	}

	@Test
	public void GIVEN_healthState_WITH_cureAndDrugsSideEffect_SHOULD_returnSideEffect() {
		List<HealthStateEnum> healthStateInput = List.of(HealthStateEnum.T);
		Set<DrugEnum> drugsInput = Set.of(DrugEnum.AN);
		when(drugCombinationEffectRepository.isDeadlyCombination(any())).thenReturn(false);
		when(healthStateRepository.hasCure(any(), any())).thenReturn(true);
		when(drugCombinationEffectRepository.findSideEffectBy(any())).thenReturn(Optional.of(HealthStateEnum.F));

		Map<HealthStateEnum, Integer> response = drugAdministrationService.execute(healthStateInput, drugsInput);

		assertEquals(1, response.get(HealthStateEnum.F));
		assertNull(response.get(HealthStateEnum.T));
		assertNull(response.get(HealthStateEnum.X));
		assertNull(response.get(HealthStateEnum.H));
		assertNull(response.get(HealthStateEnum.D));
		InOrder inOrder = inOrder(drugCombinationEffectRepository, healthStateRepository);
		inOrder.verify(drugCombinationEffectRepository).isDeadlyCombination(drugsInput);
		inOrder.verify(healthStateRepository).hasCure(healthStateInput.get(0), drugsInput);
		inOrder.verify(drugCombinationEffectRepository).findSideEffectBy(drugsInput);
		verifyNoInteractions(spaghettiMonster);
	}

	@Test
	public void GIVEN_healthState_WITH_deadlyDiseaseAndNonPrevention_SHOULD_returnDeath() {
		List<HealthStateEnum> healthStateInput = List.of(HealthStateEnum.T);
		Set<DrugEnum> drugsInput = Set.of(DrugEnum.AN);
		when(drugCombinationEffectRepository.isDeadlyCombination(any())).thenReturn(false);
		when(healthStateRepository.hasCure(any(), any())).thenReturn(false);
		when(healthStateRepository.preventsDeath(any(), any())).thenReturn(false);
		when(spaghettiMonster.showsNoodlyPower()).thenReturn(false);

		Map<HealthStateEnum, Integer> response = drugAdministrationService.execute(healthStateInput, drugsInput);

		assertEquals(1, response.get(HealthStateEnum.X));
		assertNull(response.get(HealthStateEnum.T));
		assertNull(response.get(HealthStateEnum.F));
		assertNull(response.get(HealthStateEnum.H));
		assertNull(response.get(HealthStateEnum.D));
		InOrder inOrder = inOrder(drugCombinationEffectRepository, healthStateRepository, spaghettiMonster);
		inOrder.verify(drugCombinationEffectRepository).isDeadlyCombination(drugsInput);
		inOrder.verify(healthStateRepository).hasCure(healthStateInput.get(0), drugsInput);
		inOrder.verify(healthStateRepository).preventsDeath(healthStateInput.get(0), drugsInput);
		inOrder.verify(spaghettiMonster).showsNoodlyPower();
	}

	@Test
	public void GIVEN_healthState_WITH_deadlyDiseaseAndPrevention_SHOULD_returnHealthState() {
		List<HealthStateEnum> healthStateInput = List.of(HealthStateEnum.T);
		Set<DrugEnum> drugsInput = Set.of(DrugEnum.AN);
		when(drugCombinationEffectRepository.isDeadlyCombination(any())).thenReturn(false);
		when(healthStateRepository.hasCure(any(), any())).thenReturn(false);
		when(healthStateRepository.preventsDeath(any(), any())).thenReturn(true);

		Map<HealthStateEnum, Integer> response = drugAdministrationService.execute(healthStateInput, drugsInput);

		assertEquals(1, response.get(HealthStateEnum.T));
		assertNull(response.get(HealthStateEnum.X));
		assertNull(response.get(HealthStateEnum.F));
		assertNull(response.get(HealthStateEnum.H));
		assertNull(response.get(HealthStateEnum.D));
		InOrder inOrder = inOrder(drugCombinationEffectRepository, healthStateRepository);
		inOrder.verify(drugCombinationEffectRepository).isDeadlyCombination(drugsInput);
		inOrder.verify(healthStateRepository).hasCure(healthStateInput.get(0), drugsInput);
		inOrder.verify(healthStateRepository).preventsDeath(healthStateInput.get(0), drugsInput);
		verifyNoInteractions(spaghettiMonster);
	}

	@Test
	public void GIVEN_healthyPatient_SHOULD_returnHealthy() {
		List<HealthStateEnum> healthStateInput = List.of(HealthStateEnum.H);
		Set<DrugEnum> drugsInput = Set.of(DrugEnum.AN);
		when(drugCombinationEffectRepository.isDeadlyCombination(any())).thenReturn(false);
		when(drugCombinationEffectRepository.findSideEffectBy(any())).thenReturn(Optional.empty());

		Map<HealthStateEnum, Integer> response = drugAdministrationService.execute(healthStateInput, drugsInput);

		assertEquals(1, response.get(HealthStateEnum.H));
		assertNull(response.get(HealthStateEnum.T));
		assertNull(response.get(HealthStateEnum.X));
		assertNull(response.get(HealthStateEnum.F));
		assertNull(response.get(HealthStateEnum.D));
		InOrder inOrder = inOrder(drugCombinationEffectRepository, healthStateRepository);
		inOrder.verify(drugCombinationEffectRepository).isDeadlyCombination(drugsInput);
		inOrder.verify(drugCombinationEffectRepository).findSideEffectBy(drugsInput);
		verifyNoInteractions(healthStateRepository);
		verifyNoInteractions(spaghettiMonster);
	}

	@Test
	public void GIVEN_drugs_WITH_deadlyCombination_SHOULD_returnDeath() {
		List<HealthStateEnum> healthStateInput = List.of(HealthStateEnum.T);
		Set<DrugEnum> drugsInput = Set.of(DrugEnum.AN);
		when(drugCombinationEffectRepository.isDeadlyCombination(any())).thenReturn(true);
		when(spaghettiMonster.showsNoodlyPower()).thenReturn(false);

		Map<HealthStateEnum, Integer> response = drugAdministrationService.execute(healthStateInput, drugsInput);

		assertEquals(1, response.get(HealthStateEnum.X));
		assertNull(response.get(HealthStateEnum.T));
		assertNull(response.get(HealthStateEnum.H));
		assertNull(response.get(HealthStateEnum.F));
		assertNull(response.get(HealthStateEnum.D));
		InOrder inOrder = inOrder(drugCombinationEffectRepository, spaghettiMonster);
		inOrder.verify(drugCombinationEffectRepository).isDeadlyCombination(drugsInput);
		inOrder.verify(spaghettiMonster).showsNoodlyPower();
		verifyNoMoreInteractions(healthStateRepository, drugCombinationEffectRepository);
	}

	@Test
	public void GIVEN_deathPatient_SHOULD_returnDeath() {
		List<HealthStateEnum> healthStateInput = List.of(HealthStateEnum.X);
		Set<DrugEnum> drugsInput = Set.of(DrugEnum.AN);
		when(drugCombinationEffectRepository.isDeadlyCombination(any())).thenReturn(false);
		when(spaghettiMonster.showsNoodlyPower()).thenReturn(false);

		Map<HealthStateEnum, Integer> response = drugAdministrationService.execute(healthStateInput, drugsInput);

		assertEquals(1, response.get(HealthStateEnum.X));
		assertNull(response.get(HealthStateEnum.T));
		assertNull(response.get(HealthStateEnum.H));
		assertNull(response.get(HealthStateEnum.F));
		assertNull(response.get(HealthStateEnum.D));
		InOrder inOrder = inOrder(drugCombinationEffectRepository, spaghettiMonster);
		inOrder.verify(drugCombinationEffectRepository).isDeadlyCombination(drugsInput);
		inOrder.verify(spaghettiMonster).showsNoodlyPower();
		verifyNoMoreInteractions(healthStateRepository, drugCombinationEffectRepository);
	}

	@Test
	public void GIVEN_deathPatient_SHOULD_spaghettiMonsterShowsNoodlyPower() {
		List<HealthStateEnum> healthStateInput = List.of(HealthStateEnum.X);
		Set<DrugEnum> drugsInput = Set.of(DrugEnum.AN);
		when(drugCombinationEffectRepository.isDeadlyCombination(any())).thenReturn(false);
		when(spaghettiMonster.showsNoodlyPower()).thenReturn(true);

		Map<HealthStateEnum, Integer> response = drugAdministrationService.execute(healthStateInput, drugsInput);

		assertEquals(1, response.get(HealthStateEnum.H));
		assertEquals(0, response.get(HealthStateEnum.X));
		assertNull(response.get(HealthStateEnum.T));
		assertNull(response.get(HealthStateEnum.F));
		assertNull(response.get(HealthStateEnum.D));
		InOrder inOrder = inOrder(drugCombinationEffectRepository, spaghettiMonster);
		inOrder.verify(drugCombinationEffectRepository).isDeadlyCombination(drugsInput);
		inOrder.verify(spaghettiMonster).showsNoodlyPower();
		verifyNoMoreInteractions(healthStateRepository, drugCombinationEffectRepository);
	}
}
