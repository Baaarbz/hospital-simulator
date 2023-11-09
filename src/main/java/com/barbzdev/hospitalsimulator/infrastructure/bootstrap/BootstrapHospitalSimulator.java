package com.barbzdev.hospitalsimulator.infrastructure.bootstrap;

import com.barbzdev.hospitalsimulator.application.DrugAdministrationService;
import com.barbzdev.hospitalsimulator.domain.repository.CureRepository;
import com.barbzdev.hospitalsimulator.domain.repository.DeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.domain.repository.PreventDeathRepository;
import com.barbzdev.hospitalsimulator.domain.repository.SideEffectRepository;
import com.barbzdev.hospitalsimulator.infrastructure.handler.InputHandler;
import com.barbzdev.hospitalsimulator.infrastructure.printer.TerminalResponsePrinter;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryCureRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryDeathlyCombinationRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemoryPreventDeathRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.InMemorySideEffectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapHospitalSimulator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BootstrapHospitalSimulator() {
	}

	private InputHandler inputHandler;
	private DrugAdministrationService drugAdministrationService;
	private CureRepository cureRepository;
	private DeathlyCombinationRepository deathlyCombinationRepository;
	private SideEffectRepository sideEffectRepository;
	private PreventDeathRepository preventDeathRepository;

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
		this.cureRepository = new InMemoryCureRepository();
		this.deathlyCombinationRepository = new InMemoryDeathlyCombinationRepository();
		this.sideEffectRepository = new InMemorySideEffectRepository();
		this.preventDeathRepository = new InMemoryPreventDeathRepository();
		logger.debug("Repositories initialized successfully");
	}

	private void initServices() {
		logger.debug("Initializing services...");
		this.drugAdministrationService = new DrugAdministrationService(
			this.cureRepository,
			this.deathlyCombinationRepository,
			this.sideEffectRepository,
			this.preventDeathRepository
		);
		logger.debug("Services initialized successfully");
	}

	private void initHandlers() {
		logger.debug("Initializing handlers...");
		this.inputHandler = new InputHandler(this.drugAdministrationService, new TerminalResponsePrinter());
		logger.debug("Handlers initialized successfully");
	}
}
