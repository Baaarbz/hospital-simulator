package com.barbzdev.hospitalsimulator.infrastructure.printer;

import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.infrastructure.HospitalSimulator;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TerminalResponsePrinterTest {

	@InjectMocks
	private TerminalResponsePrinter terminalResponsePrinter;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@BeforeEach
	public void setUpStream() {
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
	}

	@ParameterizedTest(name = "GIVEN {0} SHOULD {1}")
	@MethodSource("provideResponseToPrint")
	public void print(Map<HealthStateEnum, Integer> responseToPrint, String expectedResult) {
		terminalResponsePrinter.print(responseToPrint);

		Assertions.assertEquals(expectedResult, outContent.toString());
	}

	private static Stream<Arguments> provideResponseToPrint() {
		return Stream.of(
			Arguments.of(Map.of(HealthStateEnum.F, 1), "F:1,H:0,D:0,T:0,X:0"),
			Arguments.of(Map.of(HealthStateEnum.H, 1), "F:0,H:1,D:0,T:0,X:0"),
			Arguments.of(Map.of(HealthStateEnum.D, 1), "F:0,H:0,D:1,T:0,X:0"),
			Arguments.of(Map.of(HealthStateEnum.T, 1), "F:0,H:0,D:0,T:1,X:0"),
			Arguments.of(Map.of(HealthStateEnum.X, 1), "F:0,H:0,D:0,T:0,X:1"),
			Arguments.of(Map.of(HealthStateEnum.F, 1, HealthStateEnum.H, 1, HealthStateEnum.D, 1, HealthStateEnum.T, 1, HealthStateEnum.X, 1), "F:1,H:1,D:1,T:1,X:1")
		);
	}
}
