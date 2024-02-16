package no.ntnu.idata2001.PostalCodeRegister.model;

/**
 * The type Gb post-town.
 */
public class GBPostTown extends PostTown {
    private String county;

    /**
     * Instantiates a new Gb post-town.
     *
     * @param postalCode   the postal code
     * @param nameOfPlace  the name of place
     * @param county       the county
     */
    public GBPostTown(String postalCode, String nameOfPlace, String county) {
        super(postalCode, nameOfPlace);
        this.setCounty(county);
    }

    /**
     * Gets county.
     *
     * @return the county
     */
    public String getCounty() {
        return county;
    }

    /**
     * Sets county.
     *
     * @param county the county
     */
    public void setCounty(String county) {
        if (county == null) {
            throw new IllegalArgumentException("County cannot be null");
        } else if (county.isEmpty()) {
            throw new IllegalArgumentException("County cannot be empty or filled with just spaces");
        } else {
            this.county = county;
        }
    }

    /**
     * Gets the postTown as string.
     *
     * @return the postTown as string
     */
    @Override
    public String getAsString() {
        return (this.getCounty() + " " + this.getPostalCode() + ", " + this.getNameOfPlace());
    }
}
