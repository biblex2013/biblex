package fi.helsinki.biblex.Importer;

import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.storage.Storage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author jzse
 */
public class Importer {
    
    protected Storage storage;
        
    public Importer(Storage s) {
        storage = s;
    }
    /**Import .bib file to the database. */
    public void importFromFile(String path) throws FileNotFoundException, Exception {
        String fileAsString = "";
        try{
            Scanner reader = new Scanner(new File(path));
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                fileAsString += line;
            }
        } catch(Exception e) {
            System.err.println("Failed to read the file. Expection " + e + ".");
        }
        
        String[] entries = fileAsString.split("@");
        for (String string : entries) {
            storage.add(parseOneBibtexEntry(string));
        }
    }
    
    
    
    /**Parse one reference block into BibTeXEntry. */
    private BibTexEntry parseOneBibtexEntry(String entry) {    
        //Parse article type from entry
        String[] typeArray = entry.split("{", 1);
        String type = typeArray[0].trim();
        String entry1 = typeArray[1].trim();
        
        typeArray = entry1.split(",", 1);
        String name = typeArray[0].trim();
        entry1 = typeArray[1];
        
        BibTexStyle style;
        if(type.toLowerCase().equals("article"))
            style = BibTexStyle.ARTICLE;
        else if(type.toLowerCase().equals("book"))
            style = BibTexStyle.BOOK;
        else 
            style = BibTexStyle.INPROCEEDINGS;
                
        BibTexEntry returnEntry = new BibTexEntry(name, style);
        
        while(true) {
            typeArray = entry.split(",", 1);
            
            String[] field = typeArray[0].trim().split("=");
            
            String fieldname = field[0].trim();
            String value = field[1].trim();
            
            returnEntry.put(fieldname, value);
            
            if(entry1.length() <= 0) break;
        }      
        return returnEntry;
    }
}

