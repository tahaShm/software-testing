package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.ClauseCoverage;
import com.github.mryf323.tractatus.ClauseDefinition;
import com.github.mryf323.tractatus.Valuation;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
public class TriCongruenceClauseCoverageTest {
	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@ClauseDefinition(clause = 'a', def = "t1arr[0] < 0")
	@ClauseDefinition(clause = 'b', def = "t1arr[0] + t1arr[1] < t1arr[2]")
	@ClauseCoverage(
		predicate = "a || b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		}
	)
	@Test
	public void when_sidesAreEqualAndOneSideIsNegativeAndTriangleInequalityIsInValid_expect_notCongruent() {
		Triangle t1 = new Triangle(-4, 3, 5);
		Triangle t2 = new Triangle(3, -4, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@ClauseDefinition(clause = 'a', def = "t1arr[0] < 0")
	@ClauseDefinition(clause = 'b', def = "t1arr[0] + t1arr[1] < t1arr[2]")
	@ClauseCoverage(
		predicate = "a || b",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true)
		}
	)
	@Test
	public void when_sidesAreEqualAndAllSidesArePositiveAndTriangleInequalityIsValid_expect_congruent() {
		Triangle t1 = new Triangle(4, 3, 5);
		Triangle t2 = new Triangle(3, 4, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}
}
