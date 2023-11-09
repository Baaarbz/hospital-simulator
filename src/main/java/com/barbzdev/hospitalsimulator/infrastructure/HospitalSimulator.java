package com.barbzdev.hospitalsimulator.infrastructure;

import com.barbzdev.hospitalsimulator.infrastructure.bootstrap.BootstrapHospitalSimulator;

public class HospitalSimulator {
	public static void main(String[] args) {
		new BootstrapHospitalSimulator().run(args);
	}
}
