package fi.helsinki.biblex.exporter;

import fi.helsinki.biblex.domain.BibTexEntry;
import java.io.File;
import java.io.IOException;

/**
 * Export bibtex.
 * @author jtmikkon
 */
public abstract class Exporter {
    
    protected Iterable<BibTexEntry> storage;
        
    /**
     * Create Exporter with storage.
     * @param s storage object
     */
    public Exporter(Iterable<BibTexEntry> s) {
        storage = s;
    }
    
    /**
     * Write bibtex information from earlier specified storage to file object.
     * @param file file object to write
     * @throws IOException 
     */
    public abstract void write(File file) throws IOException;
    
    /**
     * Write bibtex information from earlier specified storage to filename.
     * @param filename filename string
     * @throws IOException 
     */
    public abstract void write(String filename) throws IOException;
}
