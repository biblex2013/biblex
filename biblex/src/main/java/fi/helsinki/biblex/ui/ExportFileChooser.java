package fi.helsinki.biblex.ui;

import javax.swing.*;

public class ExportFileChooser extends GenericFileChooser {
    public ExportFileChooser() {
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
				this.setApproveButtonMnemonic('e');
        this.setFileFilter(new BibTexFile());
    }

    public String getFileName() {
        if (this.showDialog(null, "Export") != JFileChooser.APPROVE_OPTION)
            return null;

        String path = this.getSelectedFile().getAbsolutePath();

        // set extension if necessary
        if (BibTexFile.class.isInstance(this.getFileFilter()) && path.lastIndexOf(".") == -1) {
            path += ".bib";
        }

        return path;
    }
}
