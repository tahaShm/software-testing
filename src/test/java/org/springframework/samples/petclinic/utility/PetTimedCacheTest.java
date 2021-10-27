package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class PetTimedCacheTest {
	@Test
	void memoryMissBehaviouralTest() {
		PetRepository repository = mock(PetRepository.class);
		PetTimedCache cache = new PetTimedCache(repository);
		when(repository.findById(1)).thenReturn(null);
		cache.get(1);
		verify(repository, times(1)).findById(1);
	}

	@Test
	void memoryMissStateTest() {
		PetRepository repository = mock(PetRepository.class);
		PetTimedCache cache = new PetTimedCache(repository);
		when(repository.findById(1)).thenReturn(null);
		assertEquals(cache.get(1), null);
	}

	@Test
	void memoryHitBehaviouralTest() {
		PetRepository repository = mock(PetRepository.class);
		PetTimedCache cache = new PetTimedCache(repository);
		Pet pet = new Pet();
		pet.setId(1);
		when(repository.findById(1)).thenReturn(pet);
		cache.get(1);
		verify(repository, times(1)).findById(1);
	}

	@Test
	void memoryHitStateTest() {
		PetRepository repository = mock(PetRepository.class);
		PetTimedCache cache = new PetTimedCache(repository);
		Pet pet = new Pet();
		pet.setId(1);
		when(repository.findById(1)).thenReturn(pet);
		assertEquals(cache.get(1), pet);
	}

	@Test
	void cacheHitBehaviouralTest() {
		PetRepository repository = mock(PetRepository.class);
		PetTimedCache cache = new PetTimedCache(repository);
		Pet pet = new Pet();
		pet.setId(1);
		when(repository.findById(1)).thenReturn(pet);
		cache.get(1);
		cache.get(1);
		verify(repository, times(1)).findById(1);
	}

	@Test
	void cacheHitStateTest() {
		PetRepository repository = mock(PetRepository.class);
		PetTimedCache cache = new PetTimedCache(repository);
		Pet pet = new Pet();
		pet.setId(1);
		when(repository.findById(1)).thenReturn(pet);
		cache.get(1);
		assertEquals(cache.get(1), pet);
	}
}
