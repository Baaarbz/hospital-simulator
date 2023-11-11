package com.barbzdev.hospitalsimulator.infrastructure.handler;

import com.barbzdev.hospitalsimulator.application.DrugAdministrationService;
import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.infrastructure.printer.TerminalResponsePrinter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InputHandlerTest {

	@Mock
	private TerminalResponsePrinter terminalResponsePrinter;

	@Mock
	private DrugAdministrationService drugAdministrationService;

	@InjectMocks
	private InputHandler inputHandler;

	@Test
	public void GIVEN_existingHealthStateAndDrugs_SHOULD_sendToService() {
		when(drugAdministrationService.execute(List.of(HealthStateEnum.D), Set.of(DrugEnum.I))).thenReturn(Map.of(HealthStateEnum.D, 1));
		doNothing().when(terminalResponsePrinter).print(any());

		inputHandler.handle(new String[]{"D", "I"});

		InOrder inOrder = inOrder(drugAdministrationService, terminalResponsePrinter);
		inOrder.verify(drugAdministrationService).execute(List.of(HealthStateEnum.D), Set.of(DrugEnum.I));
		inOrder.verify(terminalResponsePrinter).print(any());
	}

	@Test
	public void GIVEN_nonExistingHealthStateAndDrugs_SHOULD_ignore() {
		when(drugAdministrationService.execute(Collections.emptyList(), Collections.emptySet())).thenReturn(Collections.emptyMap());
		doNothing().when(terminalResponsePrinter).print(any());

		inputHandler.handle(new String[]{"foo", "bar"});

		InOrder inOrder = inOrder(drugAdministrationService, terminalResponsePrinter);
		inOrder.verify(drugAdministrationService).execute(Collections.emptyList(), Collections.emptySet());
		inOrder.verify(terminalResponsePrinter).print(any());
	}

	@Test
	public void GIVEN_healthStateAndDrugs_WITH_wrongFormats_SHOULD_ignoreWrongsAndSendRightOnes() {
		List<HealthStateEnum> validHealthStates = List.of(HealthStateEnum.T, HealthStateEnum.F, HealthStateEnum.X);
		Set<DrugEnum> validDrugs = Set.of(DrugEnum.I, DrugEnum.AN);
		when(drugAdministrationService.execute(validHealthStates, validDrugs)).thenReturn(Collections.emptyMap());
		doNothing().when(terminalResponsePrinter).print(any());

		inputHandler.handle(new String[]{",,T,f,foo,,X", "bar,,I,,an,"});

		InOrder inOrder = inOrder(drugAdministrationService, terminalResponsePrinter);
		inOrder.verify(drugAdministrationService).execute(validHealthStates, validDrugs);
		inOrder.verify(terminalResponsePrinter).print(any());
	}

	@Test
	public void GIVEN_healthState_WITHOUT_drugs_SHOULD_sendNoDrugs() {
		List<HealthStateEnum> inputHealthStates = List.of(HealthStateEnum.T, HealthStateEnum.F, HealthStateEnum.X);
		Set<DrugEnum> inputDrugs = Collections.emptySet();
		when(drugAdministrationService.execute(inputHealthStates, inputDrugs)).thenReturn(Collections.emptyMap());
		doNothing().when(terminalResponsePrinter).print(any());

		inputHandler.handle(new String[]{",,T,f,foo,,X"});

		InOrder inOrder = inOrder(drugAdministrationService, terminalResponsePrinter);
		inOrder.verify(drugAdministrationService).execute(inputHealthStates, inputDrugs);
		inOrder.verify(terminalResponsePrinter).print(any());
	}
}
