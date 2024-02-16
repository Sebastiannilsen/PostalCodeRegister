package no.ntnu.idata2001.PostalCodeRegister.model;

import no.ntnu.idata2001.PostalCodeRegister.Exceptions.RemoveException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type post-town register.
 * Used arrayList and not HashMap because the same zip code can be used in multiple municipalities.
 */
public class PostTownRegister {
    /**
     * The Post towns.
     */
    ArrayList<NorwayPostTown> norwayPostTowns;

    /**
     * Instantiates a new Post town register.
     * Created an arrayList to hold the PostTowns
     */
    public PostTownRegister() {
        this.norwayPostTowns = new ArrayList<>();
    }

    /**
     * Add post-town.
     *
     * @param norwayPostTown the post-town
     */
    public void addPostTown(NorwayPostTown norwayPostTown){
        if (norwayPostTown == null){
            throw new IllegalArgumentException("Post town cannot be null");
        }else if (!isInRegister(norwayPostTown)){
            this.norwayPostTowns.add(norwayPostTown);
        } else {
            throw new IllegalArgumentException("Post town already in the register");
        }
    }

    /**
     * Remove post-town.
     *
     * @param norwayPostTown the post-town
     * @throws RemoveException the remove exception
     */
    public void removePostTown(NorwayPostTown norwayPostTown) throws RemoveException {
        if (norwayPostTown == null){
            throw new RemoveException("Post town cannot be null");
        }else if (!norwayPostTowns.contains(norwayPostTown)){
            throw new RemoveException("Post town not in the register");
        }else{
            this.norwayPostTowns.remove(norwayPostTown);
        }
    }

    /**
     * Get post-town list.
     *
     * @return the list
     */
    public List<NorwayPostTown> getPostTownList(){
        return this.norwayPostTowns;
    }

    /**
     * Fill register with dummies.
     */
    public void fillRegisterWithDummies(){
        this.norwayPostTowns.add(new NorwayPostTown("1395","HVALSTAD","ASKER"));
        this.norwayPostTowns.add(new NorwayPostTown("1383","ASKER","ASKER"));
        this.norwayPostTowns.add(new NorwayPostTown("6005","ÅLESUND","ÅLESUND"));
        this.norwayPostTowns.add(new NorwayPostTown("5259","HJELLESTAD","BERGEN"));
    }

    /**
     * Takes in a String with absolute csv file path and adds the file info to the register.
     * @param fileToRead the file to read.
     * @throws IOException the io exception.
     * @return number of error objects in file.
     */
    public int readFromFile(File fileToRead) throws IOException {
        int errors = 0;

        Scanner sc = new Scanner(fileToRead, StandardCharsets.ISO_8859_1);
        sc.useDelimiter("\n");

        while (sc.hasNextLine()){

            String postTown = sc.nextLine();
            String[] info = postTown.split("\t", 5);

            String postCode = info[0];
            String nameOfPlace = info[1];
            String municipality = info[3];

            try {
                NorwayPostTown norwayPostTownFromFile = new NorwayPostTown(postCode, nameOfPlace, municipality);
                if (!isInRegister(norwayPostTownFromFile)) {
                    this.norwayPostTowns.add(norwayPostTownFromFile);
                }else{
                    errors++;
                }
            }catch (IllegalArgumentException e){
                errors++;
            }

        }
        sc.close();

        return errors;
    }

    /**
     * @param norwayPostTownFromFile  the norway post-town from file
     * @return true if the post-town is already in the register
     */
    private boolean isInRegister(NorwayPostTown norwayPostTownFromFile) {
        boolean duplicate = false;

        for (NorwayPostTown norwayPostTown : getPostTownList()){
            if (norwayPostTownFromFile.getPostalCode().equals(norwayPostTown.getPostalCode())
                    && norwayPostTownFromFile.getNameOfPlace().equals(norwayPostTown.getNameOfPlace())
                    && norwayPostTownFromFile.getMunicipality().equals(norwayPostTown.getMunicipality())) {
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }
}
