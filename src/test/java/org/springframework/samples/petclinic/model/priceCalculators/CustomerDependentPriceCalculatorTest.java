package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerDependentPriceCalculatorTest {

	private CustomerDependentPriceCalculator customerDependentPriceCalculator;

	@Before
	public void initial() {
		customerDependentPriceCalculator = new CustomerDependentPriceCalculator();
	}

	@Test
	public void when_petIsRareAndInfantAndDiscountCounterIsLessThanMinAndUserIsGold() {
		List<Pet> pets = new ArrayList<>();
		Pet pet = mock(Pet.class);
		pet.setType(new PetType());
		PetType petType = mock(PetType.class);
		when(pet.getType()).thenReturn(petType);
		Date birthDate = new Date();
		when(pet.getBirthDate()).thenReturn(birthDate);
		when(petType.getRare()).thenReturn(true);
		pets.add(pet);
		double baseCharge = 0.1;
		double basePricePerPet = 0.2;
		UserType userType = UserType.GOLD;
		double totalPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(totalPrice, baseCharge + (basePricePerPet * 1.2 * 1.4 * userType.discountRate));
	}

	@Test
	public void when_petIsRareAndNotInfantAndDiscountCounterIsLessThanMinAndUserIsSilver() {
		List<Pet> pets = new ArrayList<>();
		Pet pet = mock(Pet.class);
		pet.setType(new PetType());
		PetType petType = mock(PetType.class);
		when(pet.getType()).thenReturn(petType);
		Date birthDate = new Date();
		birthDate.setTime(0);
		when(pet.getBirthDate()).thenReturn(birthDate);
		when(petType.getRare()).thenReturn(true);
		pets.add(pet);
		double baseCharge = 0.1;
		double basePricePerPet = 0.2;
		UserType userType = UserType.SILVER;
		double totalPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(totalPrice, basePricePerPet * 1.2);
	}

	@Test
	public void when_petIsNotRareAndInfantAndDiscountCounterIsMoreThanMinAndUserIsNew() {
		List<Pet> pets = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			Pet pet = mock(Pet.class);
			pet.setType(new PetType());
			PetType petType = mock(PetType.class);
			when(pet.getType()).thenReturn(petType);
			Date birthDate = new Date();
			when(pet.getBirthDate()).thenReturn(birthDate);
			when(petType.getRare()).thenReturn(false);
			pets.add(pet);
		}
		double baseCharge = 0.1;
		double basePricePerPet = 0.2;
		UserType userType = UserType.NEW;
		double expectedPrice = 0;
		for (int i = 0; i < 11; i++) {
			expectedPrice += basePricePerPet * 1.2;
		}
		double totalPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(totalPrice, expectedPrice * userType.discountRate + baseCharge);
	}

	@Test
	public void when_petIsNotRareAndNotInfantAndDiscountCounterIsMoreThanMinAndUserIsGold() {
		List<Pet> pets = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			Pet pet = mock(Pet.class);
			pet.setType(new PetType());
			PetType petType = mock(PetType.class);
			when(pet.getType()).thenReturn(petType);
			Date birthDate = new Date();
			birthDate.setTime(0);
			when(pet.getBirthDate()).thenReturn(birthDate);
			when(petType.getRare()).thenReturn(false);
			pets.add(pet);
		}
		double baseCharge = 0.1;
		double basePricePerPet = 0.2;
		UserType userType = UserType.GOLD;
		double expectedPrice = 0;
		for (int i = 0; i < 11; i++) {
			expectedPrice += basePricePerPet;
		}
		double totalPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(totalPrice, (expectedPrice + baseCharge) * userType.discountRate);
	}
}
