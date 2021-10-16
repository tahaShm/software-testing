package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.*;

@RunWith(Theories.class)
public class PetTest {
	public List<Visit> visits;
	public Pet pet;

	@BeforeEach
	public void setup() {}

	public PetTest() {
		visits = new ArrayList<>();
		for (int i = 0; i < 4; i++){
			Visit newVisit = new Visit();
			newVisit.setDate(LocalDate.of(2020-i, 1, 8));
			visits.add(newVisit);
		}
		pet = new Pet();
		pet.setVisitsInternal(visits);

	}
	@DataPoints
	public static int[] visitIndexes = {3,2,1,0};

	@DataPoints
	public static Set[] visitLists = {
		new HashSet(Arrays.asList(1,2,3,0)),
		new HashSet(Arrays.asList(3,1,2,0)),
		new HashSet(Arrays.asList(0,1,2,3)),
		new HashSet(Arrays.asList(1,2,0,3))
	};

	@Theory public void checkSortedVisits(int index, Set indexList) {
		assumeThat(indexList != null);
		List indexArray = Arrays.asList(indexList.toArray());
		List visitList = new ArrayList();
		visitList.add(visits.get((int) indexArray.get(0)));
		visitList.add(visits.get((int) indexArray.get(1)));
		visitList.add(visits.get((int) indexArray.get(2)));
		visitList.add(visits.get((int) indexArray.get(3)));
		pet.setVisitsInternal(visitList);
		assertThat(pet.getVisits().get(index).equals(visits.get(index)));
	}

	@AfterEach
	public void teardown() {
		visits.clear();
	}
}
