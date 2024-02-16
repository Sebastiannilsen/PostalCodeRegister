package no.ntnu.idata2001.PostalCodeRegister.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Post town test.
 */
public class NorwayPostTownTest {

    /**
     * The Post town.
     */
    NorwayPostTown norwayPostTown;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        norwayPostTown = new NorwayPostTown("1395", "Hvalstad", "asker");
    }

    /**
     * Tear down.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Positive test.
     * Test correct creation of post-town.
     */
    @Test
    @DisplayName("Test if fields are correct when creating a new postTown")
    public void testCorrectCreationOfPostTown() {
        NorwayPostTown norwayPostTown1 = new NorwayPostTown("1395", "Hvalstad", "Asker");
        assertEquals("1395", norwayPostTown1.getPostalCode());
        assertEquals("Hvalstad", norwayPostTown1.getNameOfPlace());
        assertEquals("Asker", norwayPostTown1.getMunicipality());
    }

    /**
     * Negative test.
     * Test wrong creation of post-town.
     */
    @Test
    @DisplayName("Test if exception is thrown when a post town is created with empty fields")
    public void testWrongCreationOfPostTown() {
        try {
            new NorwayPostTown("", "", "");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Negative test.
     * Test set postal code with letters.
     */
    @Test
    @DisplayName("Test if exception is thrown when postal code is set to letters.")
    public void testSetPostalCodeWithLetters() {
        try {
            norwayPostTown.setPostalCode("halla");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Negative test.
     * Test set postal code with too few digits.
     */
    @Test
    @DisplayName("Test if exception is thrown when postal code is with too few digits.")
    public void testSetPostalCodeWithTooFewDigits() {
        try {
            norwayPostTown.setPostalCode("123");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Positive test.
     * Tests to set postal code with correct value
     */
    @Test
    @DisplayName("Test if set postal code works as intended")
    public void testSetPostalCode() {
        try {
            norwayPostTown.setPostalCode("1383");
            assertEquals("1383", norwayPostTown.getPostalCode());
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    /**
     * Positive test.
     * Tests the gets municipality method.
     */
    @Test
    @DisplayName("Test the get municipality")
    public void testGetMunicipality() {
        assertEquals("Asker", norwayPostTown.getMunicipality());
    }
}