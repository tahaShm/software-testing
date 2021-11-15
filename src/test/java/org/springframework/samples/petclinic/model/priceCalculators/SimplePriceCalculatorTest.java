package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimplePriceCalculatorTest {

	private SimplePriceCalculator simplePriceCalculator;

	@Before
	public void initial() {
		simplePriceCalculator = new SimplePriceCalculator();
	}

	@Test
	public void when_rareIsTrueAndUserTypeIsNotNew_expect_considerRareCoefAndNotConsiderDiscountRate() {
		List<Pet> pets = new ArrayList<>();
		Pet pet = mock(Pet.class);
		pet.setType(new PetType());
		PetType petType = mock(PetType.class);
		when(pet.getType()).thenReturn(petType);
		when(petType.getRare()).thenReturn(true);
		pets.add(pet);
		double baseCharge = 0.1;
		double basePricePerPet = 0.2;
		UserType userType = UserType.GOLD;
		double totalPrice = simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(totalPrice, baseCharge + basePricePerPet * 1.2);
	}

	@Test
	public void when_rareIsTrueAndUserTypeIsNew_expect_considerRareCoefAndConsiderDiscountRate() {
		List<Pet> pets = new ArrayList<>();
		Pet pet = mock(Pet.class);
		pet.setType(new PetType());
		PetType petType = mock(PetType.class);
		when(pet.getType()).thenReturn(petType);
		when(petType.getRare()).thenReturn(true);
		pets.add(pet);
		double baseCharge = 0.1;
		double basePricePerPet = 0.2;
		UserType userType = UserType.NEW;
		double totalPrice = simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(totalPrice, (baseCharge + basePricePerPet * 1.2) * userType.discountRate);
	}

	@Test
	public void when_rareIsFalseAndUserTypeIsNotNew_expect_notConsiderRareCoefAndnotConsiderDiscountRate() {
		List<Pet> pets = new ArrayList<>();
		Pet pet = mock(Pet.class);
		pet.setType(new PetType());
		PetType petType = mock(PetType.class);
		when(pet.getType()).thenReturn(petType);
		when(petType.getRare()).thenReturn(false);
		pets.add(pet);
		double baseCharge = 0.1;
		double basePricePerPet = 0.2;
		UserType userType = UserType.GOLD;
		double totalPrice = simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(totalPrice, baseCharge + basePricePerPet);
	}

	@Test
	public void when_rareIsFalseAndUserTypeIsNew_expect_notConsiderRareCoefAndConsiderDiscountRate() {
		List<Pet> pets = new ArrayList<>();
		Pet pet = mock(Pet.class);
		pet.setType(new PetType());
		PetType petType = mock(PetType.class);
		when(pet.getType()).thenReturn(petType);
		when(petType.getRare()).thenReturn(false);
		pets.add(pet);
		double baseCharge = 0.1;
		double basePricePerPet = 0.2;
		UserType userType = UserType.NEW;
		double totalPrice = simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(totalPrice, (baseCharge + basePricePerPet) * userType.discountRate);
	}

}
