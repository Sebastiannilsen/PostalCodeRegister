package no.ntnu.idata2001.PostalCodeRegister.model;

/**
 * The type Norway post-town.
 */
public class NorwayPostTown extends PostTown {


    private String municipality;

    /**
     * Instantiates a new Norway post-town.
     *
     * @param postalCode   the postal code
     * @param nameOfPlace  the name of place
     * @param municipality the municipality
     */
    public NorwayPostTown(String postalCode, String nameOfPlace, String municipality) {
        super(postalCode, nameOfPlace);
        this.setMunicipality(municipality);
    }

    /**
     * Gets municipality.
     *
     * @return the municipality
     */
    public String getMunicipality() {
        return municipality;
    }

    /**
     * Sets municipality.
     *
     * @param municipality the municipality
     */
    public void setMunicipality(String municipality) {
        if (municipality == null) {
            throw new IllegalArgumentException("Municipality cannot be null");
        } else if (municipality.isEmpty()) {
            throw new IllegalArgumentException("Municipality cannot be empty or filled with just spaces");
        } else {
            this.municipality = municipality.substring(0, 1).toUpperCase() + municipality.substring(1).toLowerCase();
        }
    }

    /**
     * Sets postal code.
     * Checks if the postal code is null, empty, filled with just spaces,
     * not 4 digits or not only digits.
     *
     * @param postalCode the postal code
     */
    @Override
    public void setPostalCode(String postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException("Postal code cannot be null");
        } else if (postalCode.isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be empty or filled with just spaces");
        } else if (postalCode.length() != 4) {
            throw new IllegalArgumentException("Postal code must be 4 digits");
        } else if (!postalCode.matches("[0-9]+")) {
            throw new IllegalArgumentException("Postal code must be only digits");
        } else {
            super.setPostalCode(postalCode);
        }
    }

    /**
     * Gets the postTown as string.
     *
     * @return the postTown as string
     */
    @Override
    public String getAsString() {
        return (this.getPostalCode() + " " + this.getNameOfPlace() + ", " + this.getMunicipality());
    }
}
