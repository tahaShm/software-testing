package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.visit.Visit;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class PetManagerTest {

	// Double: PetTimedCache -> Dummy, Logger -> Dummy, OwnerRepository -> Stub
	// Verification: State
	// Style: Mockisty
	@Test
	void findOwnerTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		Owner owner1 = new Owner();
		owner1.setId(1);
		when(owners.findById(1)).thenReturn(owner1);
		assertEquals(petManager.findOwner(1), owner1);
	}

	// Double: PetTimedCache -> Dummy, Logger -> Dummy, OwnerRepository -> Stub
	// Verification: State
	// Style: Mockisty
	@Test
	void notFindOwnerTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		when(owners.findById(1)).thenThrow(new EntityNotFoundException());
		assertThrows(EntityNotFoundException.class, () -> {
			petManager.findOwner(1);
		});
	}

	// Double: PetTimedCache -> Dummy, Logger -> Dummy, OwnerRepository -> Dummy, Owner -> Mock
	// Verification: Behavioural
	// Style: Mockisty
	@Test
	void newPetTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		Owner owner = mock(Owner.class);
		Pet pet = petManager.newPet(owner);
		verify(owner, times(1)).addPet(pet);
	}

	// Double: PetTimedCache -> Stub, Logger -> Dummy, OwnerRepository -> Dummy
	// Verification: State
	// Style: Mockisty
	@Test
	void findPetTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		Pet pet1 = new Pet();
		pet1.setId(1);
		when(pets.get(1)).thenReturn(pet1);
		assertEquals(petManager.findPet(1), pet1);
	}

	// Double: PetTimedCache -> Stub, Logger -> Dummy, OwnerRepository -> Dummy
	// Verification: State
	// Style: Mockisty
	@Test
	void notFindPetTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		when(pets.get(1)).thenThrow(new EntityNotFoundException());
		assertThrows(EntityNotFoundException.class, () -> {
			petManager.findPet(1);
		});
	}

	// Double: PetTimedCache -> Mock, Logger -> Dummy, OwnerRepository -> Dummy, Owner -> Mock
	// Verification: Behavioural
	// Style: Mockisty
	@Test
	void savePetTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		Owner owner = mock(Owner.class);
		Pet pet = new Pet();
		petManager.savePet(pet, owner);
		verify(pets, times(1)).save(pet);
		verify(owner, times(1)).addPet(pet);
	}

	// Double: PetTimedCache -> Dummy, Logger -> Dummy, OwnerRepository -> Stub
	// Verification: State
	// Style: Mockisty
	@Test
	void getOwnerPetsTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet = new Pet();
		owner.addPet(pet);
		when(owners.findById(1)).thenReturn(owner);
		List<Pet> petList = new ArrayList<>();
		petList.add(pet);
		assertEquals(petManager.getOwnerPets(1), petList);
	}

	// Double: PetTimedCache -> Dummy, Logger -> Dummy, OwnerRepository -> Stub
	// Verification: State
	// Style: Mockisty
	@Test
	void getOwnerPetTypesTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet = new Pet();
		owner.addPet(pet);
		when(owners.findById(1)).thenReturn(owner);
		Set<PetType> petTypes = new HashSet<>();
		petTypes.add(pet.getType());
		assertEquals(petManager.getOwnerPetTypes(1), petTypes);
	}

	// Double: PetTimedCache -> Stub, Logger -> Dummy, OwnerRepository -> Dummy
	// Verification: State
	// Style: Mockisty
	@Test
	void getVisitsBetweenTest() {
		PetTimedCache pets = mock(PetTimedCache.class);
		Logger criticalLogger = mock(Logger.class);
		OwnerRepository owners = mock(OwnerRepository.class);
		PetManager petManager = new PetManager(pets, owners, criticalLogger);
		Pet pet = new Pet();
		pet.setId(1);
		when(pets.get(1)).thenReturn(pet);
		LocalDate now = LocalDate.now();
		Visit visit = new Visit();
		visit.setDate(now);
		List<Visit> visits = new ArrayList<>();
		visits.add(visit);
		pet.addVisit(visit);
		assertEquals(petManager.getVisitsBetween(1, now.minusDays(2), now.plusDays(2)), visits);
	}

}
