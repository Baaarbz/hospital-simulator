import com.barbzdev.hospitalsimulator.infrastructure.HospitalSimulator;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class HospitalSimulatorTest {
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

	@ParameterizedTest(name = "GIVEN input [healthState::{0} - drugs::{1}] SHOULD {3}")
	@MethodSource("providePatientCases")
	public void hospitalSimulator(String healthStateInput, String drugsInput, String expectedResult, String testNameResult) {
		HospitalSimulator.main(new String[]{healthStateInput, drugsInput});

		Assertions.assertEquals(expectedResult, outContent.toString());
	}

	private static Stream<Arguments> providePatientCases() {
		return Stream.of(
			Arguments.of("F", "P", "F:0,H:1,D:0,T:0,X:0", "cure fever"),
			Arguments.of("F", "As", "F:0,H:1,D:0,T:0,X:0", "cure fever"),
			Arguments.of("T", "An", "F:0,H:1,D:0,T:0,X:0", "cure tuberculosis"),
			Arguments.of("D", "I", "F:0,H:0,D:1,T:0,X:0", "not die"),
			Arguments.of("H", "I,An", "F:1,H:0,D:0,T:0,X:0", "side effect fever"),
			Arguments.of("H", "An,I", "F:1,H:0,D:0,T:0,X:0", "side effect fever"),
			Arguments.of("D", "", "F:0,H:0,D:0,T:0,X:1", "die if not receive insulin"),
			Arguments.of("T,F,D,H,X", "P,As", "F:0,H:0,D:0,T:0,X:5", "die all"),
			Arguments.of("T,F,D,H,X", "As,P", "F:0,H:0,D:0,T:0,X:5", "die all"),
			Arguments.of("T,F,D,H,X", "An,P,I,As", "F:0,H:0,D:0,T:0,X:5", "die all"),
			Arguments.of("T,F,D", "An,I", "F:2,H:0,D:1,T:0,X:0", "patient with diabetes not die, tuberculosis cure with antibiotic but produces fever with insulin")
		);
	}
}
