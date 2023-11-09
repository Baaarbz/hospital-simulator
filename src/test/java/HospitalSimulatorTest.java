import com.barbzdev.hospitalsimulator.infrastructure.HospitalSimulator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

	@Test
	public void Should_CurePatientWithFever_When_ReceiveParacetamol() {
		String patientInput = "F";
		String drugInput = "P";

		HospitalSimulator.main(new String[]{patientInput, drugInput});

		Assertions.assertEquals("F:0,H:1,D:0,T:0,X:0", outContent.toString());
	}

	@Test
	public void Should_CurePatientWithFever_When_ReceiveAspirin() {
		String patientInput = "F";
		String drugInput = "As";

		HospitalSimulator.main(new String[]{patientInput, drugInput});

		Assertions.assertEquals("F:0,H:1,D:0,T:0,X:0", outContent.toString());
	}

	@Test
	public void Should_CurePatientWithTuberculosis_When_ReceiveParacetamol() {
		String patientInput = "T";
		String drugInput = "An";

		HospitalSimulator.main(new String[]{patientInput, drugInput});

		Assertions.assertEquals("F:0,H:1,D:0,T:0,X:0", outContent.toString());
	}

	@Test
	public void Should_PatientWithDiabetesDontDie_When_ReceiveInsulin() {
		String patientInput = "D";
		String drugInput = "I";

		HospitalSimulator.main(new String[]{patientInput, drugInput});

		Assertions.assertEquals("F:0,H:0,D:1,T:0,X:0", outContent.toString());
	}

	@Test
	public void Should_HealthyPatientSideEffectFever_When_ReceiveInsulinAndAntibiotic() {
		String patientInput = "H";
		String drugInput = "I,An";

		HospitalSimulator.main(new String[]{patientInput, drugInput});

		Assertions.assertEquals("F:1,H:0,D:0,T:0,X:0", outContent.toString());
	}

	@Test
	public void Should_DieEveryPatient_When_ReceiveParacetamolAndAspirin() {
		String patientInput = "F,H,D,T";
		String drugInput = "P,An";

		HospitalSimulator.main(new String[]{patientInput, drugInput});

		Assertions.assertEquals("F:0,H:0,D:0,T:0,X:4", outContent.toString());
	}

	@Test
	public void Should_PatientWithDiabetesDies_When_NotReceiveInsulin() {
		String patientInput = "D";

		HospitalSimulator.main(new String[]{patientInput});

		Assertions.assertEquals("F:0,H:0,D:0,T:0,X:1", outContent.toString());
	}

	// TODO Move corner cases, here only happy path
	@Test
	public void Should_PreventPatientWithDiabetesToDieAntibioticCureTuberculosisButProducesFeverWithInsulin_When_ReceiveInsulinAndAntibiotic() {
		String patientInput = "T,F,D";
		String drugInput = "An,I";

		HospitalSimulator.main(new String[]{patientInput, drugInput});

		Assertions.assertEquals("F:2,H:0,D:1,T:0,X:0", outContent.toString());
	}
}
