package com.barbzdev.hospitalsimulator.infrastructure.component;

import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlyingSpaghettiMonsterTest {

	@Mock
	private Random random;

	@InjectMocks
	private FlyingSpaghettiMonster flyingSpaghettiMonster;

	@Test
	void WHEN_randomHitsNumber4_SHOULD_returnTrue() {
		when(random.nextInt(1000000)).thenReturn(3);

		Boolean hasShownNoodlyPower = flyingSpaghettiMonster.showsNoodlyPower();

		assertTrue(hasShownNoodlyPower);
	}

	@Test
	void WHEN_randomMissNumber4_SHOULD_returnFalse() {
		when(random.nextInt(1000000)).thenReturn(6);

		Boolean hasShownNoodlyPower = flyingSpaghettiMonster.showsNoodlyPower();

		assertFalse(hasShownNoodlyPower);
	}
}
