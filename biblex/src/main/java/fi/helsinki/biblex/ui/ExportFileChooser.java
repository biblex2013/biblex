package fi.helsinki.biblex.ui;

import java.util.MissingResourceException;
import javax.swing.*;

public class ExportFileChooser extends GenericFileChooser {
    public ExportFileChooser() {
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
				this.setApproveButtonMnemonic('e');
    }

    public String getFileName() {
        if (this.showDialog(null, "Export") != JFileChooser.APPROVE_OPTION)
            return null;
        return this.getSelectedFile().getAbsolutePath();
    }
}
