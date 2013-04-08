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
public class ExportToFile extends Exporter {

    public enum OS {UNIX, WIN, MAC};
    
    private String endOfLine;
    private BufferedWriter outWriter;

    
    protected ExportToFile() {}
    
    public ExportToFile(Storage s) {
        super(s);
        endOfLine = System.getProperty("line.separator");
    }
    
    /*
    public void setStorage(Storage s) {
        storage = s;
    }
    */
    
    private BufferedWriter setOutWriter(File file) throws IOException {
        return new BufferedWriter(new FileWriter(file));
    }
    
    private BufferedWriter setOutWriter(String filename) throws IOException {
        return new BufferedWriter(new FileWriter(filename));
    }
    
    @Override
    public void write(File file) throws IOException {
        outWriter = setOutWriter(file);
        writeToFile();
    }

    @Override
    public void write(String filename) throws IOException {
        outWriter = setOutWriter(filename);
        writeToFile();
    }
    
    private void writeToFile() throws IOException {
        Iterator<BibTexEntry> bibtexIterator = storage.iterator();
        
        while(bibtexIterator.hasNext()) {
            outWriter.write(bibtexIterator.next().toString());
            outWriter.write(endOfLine);
        }
        outWriter.close();
    }
    
    public void setNewLineFormat(OS os) {
        if(os == OS.UNIX || os == OS.MAC) {
            endOfLine = "\n";
        }
        //Heretics from Redmond do it differently!
        else if(os == OS.WIN) {
            endOfLine = "\r\n";
        }
    }
    
}
