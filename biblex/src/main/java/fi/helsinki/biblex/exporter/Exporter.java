package fi.helsinki.biblex.exporter;

import fi.helsinki.biblex.storage.Storage;
import java.io.File;

/**
 *
 * @author jtmikkon
 */
public interface Exporter {

    public void write(String filename, Storage storage) throws Exception;
    
    public void write(File file, Storage storage) throws Exception;
    
}
