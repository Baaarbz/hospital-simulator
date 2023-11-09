package com.barbzdev.hospitalsimulator.infrastructure.bootstrap;

import com.barbzdev.hospitalsimulator.application.DrugAdministrationService;
import com.barbzdev.hospitalsimulator.domain.repository.CureRepository;
import com.barbzdev.hospitalsimulator.domain.repository.DeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.domain.repository.PreventDeathRepository;
import com.barbzdev.hospitalsimulator.domain.repository.SideEffectRepository;
import com.barbzdev.hospitalsimulator.infrastructure.handler.InputHandler;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryCureRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryDeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryPreventDeathRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemorySideEffectRepository;

public class BootstrapHospitalSimulator {

	public BootstrapHospitalSimulator() {
	}

	private InputHandler inputHandler;
	private DrugAdministrationService drugAdministrationService;
	private CureRepository cureRepository;
	private DeathlyCombinationRepository deathlyCombinationRepository;
	private SideEffectRepository sideEffectRepository;
	private PreventDeathRepository preventDeathRepository;

	public void run(String[] args) {
		initDependencies();
		this.inputHandler.handle(args);
	}

	private void initDependencies() {
		initRepositories();
		initServices();
		initHandlers();
	}

	private void initRepositories() {
		this.cureRepository = new InMemoryCureRepository();
		this.deathlyCombinationRepository = new InMemoryDeathlyCombinationRepository();
		this.sideEffectRepository = new InMemorySideEffectRepository();
		this.preventDeathRepository = new InMemoryPreventDeathRepository();
	}

	private void initServices() {
		this.drugAdministrationService = new DrugAdministrationService(
			this.cureRepository,
			this.deathlyCombinationRepository,
			this.sideEffectRepository,
			this.preventDeathRepository
		);
	}

	private void initHandlers() {
		this.inputHandler = new InputHandler(this.drugAdministrationService);
	}
}
