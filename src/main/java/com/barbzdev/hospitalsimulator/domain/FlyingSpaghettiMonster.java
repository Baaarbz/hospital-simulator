package com.barbzdev.hospitalsimulator.domain;

import java.util.Random;

public class FlyingSpaghettiMonster {

	public FlyingSpaghettiMonster() {
	}

	public Boolean showsNoodlyPower() {
		Random random = new Random();
		int randomNumber = random.nextInt(1000000) + 1;
		return randomNumber == 4;
	}
}
