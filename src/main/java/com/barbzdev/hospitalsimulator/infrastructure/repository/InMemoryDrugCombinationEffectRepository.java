package com.barbzdev.hospitalsimulator.infrastructure.repository;

import com.barbzdev.hospitalsimulator.domain.DrugEnum;
import com.barbzdev.hospitalsimulator.domain.HealthStateEnum;
import com.barbzdev.hospitalsimulator.domain.repository.DrugCombinationEffectRepository;
import com.barbzdev.hospitalsimulator.infrastructure.repository.entity.DrugCombinationEffectEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryDrugCombinationEffectRepository implements DrugCombinationEffectRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final List<DrugCombinationEffectEntity> inMemoryDrugs;

	public InMemoryDrugCombinationEffectRepository() {
		this.inMemoryDrugs = new ArrayList<>();
		this.inMemoryDrugs.add(new DrugCombinationEffectEntity(Set.of(DrugEnum.I, DrugEnum.AN), HealthStateEnum.F));
		this.inMemoryDrugs.add(new DrugCombinationEffectEntity(Set.of(DrugEnum.P, DrugEnum.AS), HealthStateEnum.X));
	}

	@Override
	public Optional<HealthStateEnum> findSideEffectBy(Set<DrugEnum> drugs) {
		logger.debug("Searching if drugs::{} can have a non desired effect", drugs);
		return inMemoryDrugs.stream()
			.filter(drugsCombination -> drugsCombination.effect() != HealthStateEnum.X && drugs.containsAll(drugsCombination.drugCombination()))
			.map(DrugCombinationEffectEntity::effect)
			.findFirst();
	}

	@Override
	public Boolean isDeadlyCombination(Set<DrugEnum> drugs) {
		logger.debug("Searching if drugs::{} can have a non desired effect", drugs);
		return inMemoryDrugs.stream()
			.anyMatch(drugsCombination -> drugsCombination.effect() == HealthStateEnum.X && drugs.containsAll(drugsCombination.drugCombination()));
	}
}
