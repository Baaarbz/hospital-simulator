package com.barbzdev.hospitalsimulator.infrastructure.bootstrap;

import com.barbzdev.hospitalsimulator.application.DrugAdministrationService;
import com.barbzdev.hospitalsimulator.domain.repository.CureRepository;
import com.barbzdev.hospitalsimulator.domain.repository.DeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.domain.repository.SideEffectRepository;
import com.barbzdev.hospitalsimulator.infrastructure.handler.InputHandler;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryCureRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryDeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemorySideEffectRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BootstrapHospitalSimulator {

	private InputHandler inputHandler;
	private DrugAdministrationService drugAdministrationService;
	private CureRepository cureRepository;
	private DeathlyCombinationRepository deathlyCombinationRepository;
	private SideEffectRepository sideEffectRepository;

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
	}

	private void initServices() {
		this.drugAdministrationService = new DrugAdministrationService(
			this.cureRepository,
			this.deathlyCombinationRepository,
			this.sideEffectRepository
		);
	}

	private void initHandlers() {
		this.inputHandler = new InputHandler(this.drugAdministrationService);
	}
}
