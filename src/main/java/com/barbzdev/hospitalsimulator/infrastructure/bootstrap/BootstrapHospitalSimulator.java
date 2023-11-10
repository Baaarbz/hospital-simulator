package com.barbzdev.hospitalsimulator.infrastructure.bootstrap;

import com.barbzdev.hospitalsimulator.application.DrugAdministrationService;
import com.barbzdev.hospitalsimulator.domain.repository.DrugCombinationEffectRepository;
import com.barbzdev.hospitalsimulator.domain.repository.HealthStateRepository;
import com.barbzdev.hospitalsimulator.infrastructure.handler.InputHandler;
import com.barbzdev.hospitalsimulator.infrastructure.printer.TerminalResponsePrinter;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryDrugCombinationEffectRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryHealthStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapHospitalSimulator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BootstrapHospitalSimulator() {
	}

	private InputHandler inputHandler;
	private DrugAdministrationService drugAdministrationService;
	private HealthStateRepository healthStateRepository;
	private DrugCombinationEffectRepository drugCombinationEffectRepository;

	public void run(String[] args) {
		logger.debug("Bootstrapping HospitalSimulator");
		initDependencies();
		this.inputHandler.handle(args);
	}

	private void initDependencies() {
		logger.debug("Initializing dependencies...");
		initRepositories();
		initServices();
		initHandlers();
		logger.debug("Dependencies initialized successfully");
	}

	private void initRepositories() {
		logger.debug("Initializing repositories...");
		this.healthStateRepository = new InMemoryHealthStateRepository();
		this.drugCombinationEffectRepository = new InMemoryDrugCombinationEffectRepository();
		logger.debug("Repositories initialized successfully");
	}

	private void initServices() {
		logger.debug("Initializing services...");
		this.drugAdministrationService = new DrugAdministrationService(
			this.healthStateRepository,
			this.drugCombinationEffectRepository
		);
		logger.debug("Services initialized successfully");
	}

	private void initHandlers() {
		logger.debug("Initializing handlers...");
		this.inputHandler = new InputHandler(this.drugAdministrationService, new TerminalResponsePrinter());
		logger.debug("Handlers initialized successfully");
	}
}
