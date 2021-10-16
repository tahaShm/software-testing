package org.springframework.samples.petclinic.owner;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.mockito.Mockito.mock;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

	protected Integer petId;
	protected Pet pet;

	@Mock
	private PetRepository pets;

	protected PetService petService;

	protected OwnerRepository owners;

	public PetServiceTest(Integer petId, Pet pet){
		this.petId = petId;
		this.pet = pet;
		pets = mock(PetRepository.class);
		when(pets.findById(petId)).thenReturn(pet);
		Logger log = LoggerFactory.getLogger(PetService.class);
		PetTimedCache petTimedCache = new PetTimedCache(pets);
		petService = new PetService(petTimedCache, owners, log);
	}

	@Parameterized.Parameters
	public static Collection parameters() {
		Owner owner = new Owner();
		Pet pet1 = new Pet();
		pet1.setId(1);
		pet1.setName("pet_1");
		Pet pet2 = new Pet();
		pet2.setId(2);
		pet2.setName("pet_2");

		return Arrays.asList(new Object[][] {
			{1, pet1},
			{2, pet2}
		});
	}

	@Test
	public void testFindPet() {
		assertEquals(petService.findPet(petId), pet);
	}


}
