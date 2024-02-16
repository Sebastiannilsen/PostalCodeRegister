package no.ntnu.idata2001.PostalCodeRegister.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The type Gb post-town test.
 */
public class GBPostTownTest {
    /**
     * The Gb post-town.
     */
    private GBPostTown gbPostTown;

    @BeforeEach
    public void setUp() {
        this.gbPostTown = new GBPostTown("SW1A 1AA", "London", "Greater London");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Positive test.
     * Test correct creation of GB post-town.
     */
    @Test
    @DisplayName("Test if fields are correct when creating a new GB postTown")
    public void testCorrectCreationOfGBPostTown() {
        assertEquals("SW1A 1AA", gbPostTown.getPostalCode());
        assertEquals("London", gbPostTown.getNameOfPlace());
        assertEquals("Greater London", gbPostTown.getCounty());
    }


}
