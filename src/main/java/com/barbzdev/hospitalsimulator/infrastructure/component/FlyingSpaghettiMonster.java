package com.barbzdev.hospitalsimulator.infrastructure.component;

import com.barbzdev.hospitalsimulator.domain.component.SpaghettiMonster;
import java.util.Random;

public class FlyingSpaghettiMonster implements SpaghettiMonster {

	public FlyingSpaghettiMonster() {
	}

	@Override
	public Boolean showsNoodlyPower() {
		Random random = new Random();
		int randomNumber = random.nextInt(1000000) + 1;
		return randomNumber == 4;
	}
}
