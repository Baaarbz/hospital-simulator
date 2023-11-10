package com.barbzdev.hospitalsimulator.infrastructure.component;

import com.barbzdev.hospitalsimulator.domain.component.SpaghettiMonster;
import java.util.Random;

public class FlyingSpaghettiMonster implements SpaghettiMonster {

	private final Random random;

	public FlyingSpaghettiMonster(Random random) {
		this.random = random;
	}

	@Override
	public Boolean showsNoodlyPower() {
		int randomNumber = random.nextInt(1000000) + 1;
		return randomNumber == 4;
	}
}
