package fi.helsinki.biblex.ui;

import javax.swing.*;

public class ImportFileChooser extends GenericFileChooser {
    public ImportFileChooser() {
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
				this.setApproveButtonMnemonic('e');
    }

    public String getFileName() {
        if (this.showDialog(null, "Import") != JFileChooser.APPROVE_OPTION)
            return null;
        return this.getSelectedFile().getAbsolutePath();
    }
}
