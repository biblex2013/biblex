package fi.helsinki.biblex.exporter;

import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.storage.Storage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author jtmikkon
 */
public class ExportToFile implements Exporter {

    private String endOfLine;
    
    public ExportToFile() {
        endOfLine = System.getProperty("line.separator");
    }
    
    @Override
    public void write(String filename, Storage storage) throws IOException {

        BufferedWriter outWriter = new BufferedWriter(new FileWriter(filename));
        Iterator<BibTexEntry> bibtexIterator = storage.iterator();
        
        while(bibtexIterator.hasNext()) {
            outWriter.write(bibtexIterator.next().toString());
            outWriter.write(endOfLine);
        }
        outWriter.close();
    }

    @Override
    public void write(File file, Storage storage) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
