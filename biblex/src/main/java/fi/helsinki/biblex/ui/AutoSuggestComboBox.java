package fi.helsinki.biblex.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.List;

public class AutoSuggestComboBox extends JComboBox{
    private class AutoSuggestKeyHandler implements KeyListener {
        private AutoSuggestComboBox p_box;

        public AutoSuggestKeyHandler(AutoSuggestComboBox box) {
            p_box = box;
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    String text = ((JTextField) e.getSource()).getText();
                    p_box.updateSuggestionModel(text);
                }
            });
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                p_box.updateSuggestionModel("");
                p_box.transferFocusBackward();

            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                String text = ((JTextField) e.getSource()).getText();
                for (String str : p_box.p_content) {
                    if (str.startsWith(text)) {
                        ((JTextField) e.getSource()).setText(str);
                        return;
                    }
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {}
    }

    private String[] p_content;


    public AutoSuggestComboBox() {
        p_content = new String[0];
        updateSuggestionModel("");

        this.setEditable(true);
        this.getEditor().getEditorComponent().addKeyListener(new AutoSuggestKeyHandler(this));
    }


    public void setContent(List<String> content) {
        Collections.sort(content);

        p_content = new String[content.size()];
        content.toArray(p_content);

        updateSuggestionModel(
                ((JTextField) this.getEditor().getEditorComponent()).getText()
        );
    }


    public void clearEntry() {
        updateSuggestionModel("");
    }


    public String getEntry() {
        return ((JTextField) this.getEditor().getEditorComponent()).getText();
    }


    private void updateSuggestionModel(String text) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (String str : p_content) {
            if (str.startsWith(text)) {
                model.addElement(str);
            }
        }

        this.setModel(model);
        this.setSelectedIndex(-1);
        ((JTextField) this.getEditor().getEditorComponent()).setText(text);

        // hide suggestion-list if there are no suggestions
        if (text.length() > 0 && model.getSize() > 0) {
            this.showPopup();
        } else {
            this.hidePopup();
        }
    }
}
