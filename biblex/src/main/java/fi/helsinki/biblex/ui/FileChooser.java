package fi.helsinki.biblex.ui;

import javax.swing.*;

public class FileChooser extends JFileChooser {
    public FileChooser() {
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public String getFileName() {
        if (this.showDialog(null, "Export") != JFileChooser.APPROVE_OPTION)
            return null;
        return this.getSelectedFile().getAbsolutePath();
    }
}
