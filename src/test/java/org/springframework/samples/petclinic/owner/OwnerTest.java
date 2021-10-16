package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(Theories.class)
public class OwnerTest {

	private Owner owner;
	private List<Pet> pets;

	@BeforeEach
	void setup() {
		owner = new Owner();
	}

	@Test
	void testAddressAssigning() {
		String address = "Blah Blah St.";
		owner.setAddress(address);
		assertTrue(owner.getAddress().equals(address));
	}

	@Test
	void testCityAssigning() {
		String city = "Tehran";
		owner.setCity(city);
		assertTrue(owner.getCity().equals(city));
	}

	@Test
	void testTelephoneAssigning() {
		String telephone = "09371863994";
		owner.setTelephone(telephone);
		assertTrue(owner.getTelephone().equals(telephone));
	}

	@Test
	void testPetsNullInternalAssigning() {
		makePetSet(true);
		assertThat(owner.getPetsInternal()).isEqualTo(new HashSet<>());
	}

	@Test
	void testPetsInternalAssigning() {
		Set<Pet> pets = makePetSet(false);
		assertThat(owner.getPetsInternal()).isEqualTo(pets);

	}

	@Test
	void testPetsSortedList() {
		makePetSet(false);
		List<Pet> petList = owner.getPets();
		assertTrue(petList.get(0).getName().equals("pet_10") &&
					petList.get(1).getName().equals("pet_8") &&
					petList.get(2).getName().equals("pet_9"));
	}

	@Test
	void testGetPetByName() {
		makePetSet(false);
		assertTrue(owner.getPet("pet_10").getName().equals("pet_10"));
	}

	@Test
	void testAddNewPet() {
		Pet newPet = new Pet();
		newPet.setOwner(owner);
		newPet.setName("kitty");
		owner.addPet(newPet);
		assertEquals(owner.getPets().get(0).getName(), "kitty");
	}

	@Test
	void testAddRepetitivePet() {
		Set<Pet> pets = makePetSet(false);
		Pet newPet = new Pet();
		newPet.setOwner(owner);
		newPet.setName("pet_10");
		owner.addPet(newPet);
		assertEquals(owner.getPetsInternal(), pets);
	}

	@Test
	void testRemoveValidPet() {
		makePetSet(false);
		String petName = owner.getPets().get(0).getName();
		owner.removePet(owner.getPets().get(0));
		assertFalse(owner.getPets().get(0).getName().equals(petName));
	}

	@Test
	void testRemoveInvalidPet() {
		Set<Pet> pets = makePetSet(false);
		Pet newPet = new Pet();
		newPet.setOwner(owner);
		newPet.setName("bamboo");
		owner.removePet(newPet);
		assertEquals(owner.getPetsInternal(), pets);
	}

	@AfterEach
	void teardown() {
		owner.setPetsInternal(null);
	}

	private Set<Pet> makePetSet(boolean isNull) {
		Set<Pet> pets = null;
		if (!isNull) {
			pets = new HashSet<>();
			for (int i = 0; i < 3; i++){
				String iValue = Integer.toString(10-i);
				Pet newPet = new Pet();
				newPet.setOwner(owner);
				newPet.setName("pet_" + iValue);
				pets.add(newPet);
			}
		}
		owner.setPetsInternal(pets);
		return pets;
	}

	public OwnerTest() {
		pets = new ArrayList<>();
		for (int i = 0; i < 4; i++){
			String iValue = Integer.toString(i);
			Pet newPet = new Pet();
			newPet.setOwner(owner);
			newPet.setName("pet_" + iValue);
			pets.add(newPet);
		}
		owner = new Owner();
		owner.setPetsInternal(new HashSet<Pet>(pets));
	}
	@DataPoints
	public static int[] petIndexes = {3,2,1,0};

	@DataPoints
	public static Set[] petLists = {
		new HashSet(Arrays.asList(1,2,3,0)),
		new HashSet(Arrays.asList(3,1,2,0)),
		new HashSet(Arrays.asList(0,1,2,3)),
		new HashSet(Arrays.asList(1,2,0,3))
	};

	@Theory
	public void checkSortedVisits(int index, Set indexList) {
		assumeThat(indexList != null);
		List indexArray = Arrays.asList(indexList.toArray());
		List petList = new ArrayList();
		petList.add(pets.get((int) indexArray.get(0)));
		petList.add(pets.get((int) indexArray.get(1)));
		petList.add(pets.get((int) indexArray.get(2)));
		petList.add(pets.get((int) indexArray.get(3)));
		owner.setPetsInternal(new HashSet<Pet>(petList));
		assertThat(owner.getPets().get(index).equals(pets.get(index)));
	}
}
