package fi.helsinki.biblex.exporter;

import fi.helsinki.biblex.domain.BibTexEntry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Exports bibtex from Storage to file.
 * @author jtmikkon
 */
public class ExportToFile extends Exporter {

    /**
     * Enum for OS type (needed for setNewLineFormat).
     */
    public enum OS {UNIX, WIN, MAC};
    
    private String endOfLine;
    private BufferedWriter outWriter;
    private OS os;

    
    public ExportToFile(Iterable<BibTexEntry> s) {
        super(s);
        endOfLine = System.getProperty("line.separator");
        os = endOfLine.equals("\r\n") ? OS.WIN : OS.UNIX;
    }
    
    @Override
    public void write(File file) throws IOException {
        outWriter = new BufferedWriter(new FileWriter(file));
        writeToFile();
    }

    @Override
    public void write(String filename) throws IOException {
        outWriter = new BufferedWriter(new FileWriter(filename));
        writeToFile();
    }
    
    /**
     * Does all the writing to file.
     * @throws IOException 
     */
    private void writeToFile() throws IOException {
        Iterator<BibTexEntry> bibtexIterator = storage.iterator();
        
        while(bibtexIterator.hasNext()) {
            outWriter.write((os == OS.WIN) ? bibtexIterator.next().toString().replaceAll("[\\r]?\\n", endOfLine) :
                    bibtexIterator.next().toString().replaceAll("\r\n", endOfLine));
            outWriter.write(endOfLine);
            outWriter.write(endOfLine);
        }
        outWriter.close();
    }
    
    /**
     * Change line separator of file to be exported.
     * @param os ExportToFile.OS.WIN (\r\n) / ExportToFile.OS.UNIX (\n)
     */
    public void setNewLineFormat(OS os) {
        if(os == OS.UNIX || os == OS.MAC) {
            endOfLine = "\n";
        }
        //Heretics from Redmond do it differently!
        else {
            endOfLine = "\r\n";
        }
    }
    
    /**
     * Here for test cases. Hooray.
     */
    public String getNewLineFormat() {
        return endOfLine;
    }
}
