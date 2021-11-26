package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);


	/** CC and CACC **/

	@ClauseDefinition(clause = 'a', def = "t1arr[0] < 0")
	@ClauseDefinition(clause = 'b', def = "t1arr[0] + t1arr[1] < t1arr[2]")
	@ClauseCoverage(
		predicate = "a || b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		}
	)
	@CACC(
		majorClause = 'a',
		predicateValue = false,
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
	@CACC(
		majorClause = 'a',
		predicateValue = true,
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

	/**
	 * CUTPNFP
	 **/
	// For a: TFF -> UTP, FFF -> NFP
	// For a: FTF -> UTP, FFF -> NFP
	// For a: FFT -> UTP, FFF -> NFP
	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@Test
	public void when_firstElementInTrianglesAreNotEqual_expect_notCongruent() {
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(4, 3, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@Test
	public void when_secondElementInTrianglesAreNotEqual_expect_notCongruent() {
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(2, 5, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true),
		}
	)
	@Test
	public void when_ThirdElementInTrianglesAreNotEqual_expect_notCongruent() {
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(2, 3, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@Test
	public void when_allElementInTrianglesAreNotEqualAndElemsAreNegativeOrTriangleInequalityIsValid_expect_congruent() {
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(2, 3, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	/**
	 consider p = ab + bc'
	 then p' = b' + a'c
	 so all implicants = {ab, bc', b', a'c}
	 unique true points:
	 ab: {TTT},
	 bc': {FTF},
	 b': {FFF, TFF, TFT},
	 A'c: {FTT}
	 now CUTPNFP:
	 UTP: {TTT, FTF},
	 NFP: {FTT, TFT, FFF}
	 as it is clear, "TFF" is in UTPC, however, it is not in CUTPNFP set -> CUTPNFP doesn't subsume UTPC
	 */
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = a && b || b && !c;
//		predicate = ab + bc'
		return predicate;
	}
}
