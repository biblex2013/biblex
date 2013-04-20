package fi.helsinki.biblex.ui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

abstract public class GenericFileChooser extends JFileChooser {
    public static class BibTexFile extends FileFilter {
        public boolean accept(File f) {
            return f.isDirectory()||f.getName().toLowerCase().endsWith(".bib");
        }

        public String getDescription() {
            return "BibTeX bibliography file (*.bib)";
        }
    }


    abstract public String getFileName();
}
