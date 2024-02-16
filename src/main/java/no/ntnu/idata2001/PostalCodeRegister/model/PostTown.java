package no.ntnu.idata2001.PostalCodeRegister.model;

/**
 * The type Post-town.
 */
public abstract class PostTown {
    private String postalCode;
    private String nameOfPlace;

    /**
     * Instantiates a new Post town if fields are not null or not blank
     *
     * @param postalCode  the postal code
     * @param nameOfPlace the name of place
     */
    protected PostTown(String postalCode, String nameOfPlace) {
        this.setPostalCode(postalCode);
        this.setNameOfPlace(nameOfPlace);
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code if not null or not blank
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException("Postal code cannot be null");
        } else if (postalCode.isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be empty or filled with just spaces");
        } else {
            this.postalCode = postalCode;
        }
    }

    /**
     * Gets name of place.
     *
     * @return the name of place
     */
    public String getNameOfPlace() {
        return nameOfPlace;
    }

    /**
     * Sets name of place if not null or not blank
     *
     * @param nameOfPlace the name of place
     */
    public void setNameOfPlace(String nameOfPlace) {
        if (nameOfPlace == null) {
            throw new IllegalArgumentException("Name of place cannot be null");
        } else if (nameOfPlace.isEmpty()) {
            throw new IllegalArgumentException("Name of place cannot be empty or filled with just spaces");
        } else {
            this.nameOfPlace = nameOfPlace;
        }
    }

    /**
     * Gets the postTown as string.
     *
     * @return the postTown as string
     */
    public abstract String getAsString();
}
