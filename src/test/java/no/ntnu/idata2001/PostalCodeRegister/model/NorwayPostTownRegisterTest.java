package no.ntnu.idata2001.PostalCodeRegister.model;

import no.ntnu.idata2001.PostalCodeRegister.Exceptions.RemoveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The type Post town register test.
 */
public class NorwayPostTownRegisterTest {

    /**
     * The Post town register.
     */
    PostTownRegister postTownRegister;
    /**
     * The Post town.
     */
    NorwayPostTown norwayPostTown;


    /**
     * Sets up.
     * Creates a new post-town and a new post-town register.
     */
    @BeforeEach
    public void setUp() {
        this.postTownRegister = new PostTownRegister();
        this.norwayPostTown = new NorwayPostTown("1395", "Hvalstad", "Asker");
    }

    /**
     * Tear down.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Positive test.
     * Test to add a post-town to the register
     */
    @Test
    @DisplayName("Test correct add of postTown")
    public void addPostTown() {
        if (postTownRegister.getPostTownList().isEmpty()) {
            postTownRegister.addPostTown(norwayPostTown);
            if (postTownRegister.getPostTownList().size() == 1) {
                assertTrue(true);
            } else {
                fail();
            }
        } else {
            fail();
        }

    }

    /**
     * Negative test.
     * Test to add a duplicate post-town to the register
     */
    @Test
    @DisplayName("Test duplicate add of postTown")
    public void addDuplicatePostTown() {
        try {
            postTownRegister.addPostTown(norwayPostTown);
        } catch (IllegalArgumentException e) {
            fail();
        }
        try {
            postTownRegister.addPostTown(norwayPostTown);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Positive test.
     * Test to remove a post-town from the register.
     */
    @Test
    @DisplayName("Test correct removal of postTown")
    public void removePostTown() {
        postTownRegister.addPostTown(norwayPostTown);
        try {
            postTownRegister.removePostTown(norwayPostTown);
            if (postTownRegister.getPostTownList().isEmpty()) {
                assertTrue(true);
            } else {
                fail();
            }
        } catch (RemoveException e) {
            fail();
        }
    }

    /**
     * Negative test.
     * Test to remove a post-town not in the register.
     */
    @Test
    @DisplayName("Test removal of postTown not in register")
    public void testRemoveOfPostTownNotInRegister() {
        try {
            postTownRegister.removePostTown(norwayPostTown);
            fail();
        } catch (RemoveException e) {
            assertTrue(true);
        }
    }
}