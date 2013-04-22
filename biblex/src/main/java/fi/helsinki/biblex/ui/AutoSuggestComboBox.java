package fi.helsinki.biblex.ui;

import javax.swing.*;
import javax.swing.plaf.metal.MetalComboBoxEditor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
                for (JLabel label : p_box.p_content) {
                    String str = label.getText();
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

    private class JLabelCellRenderer implements ListCellRenderer {
        private DefaultListCellRenderer p_defaultRenderer = new DefaultListCellRenderer();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) p_defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            JLabel label = (JLabel) value;

            renderer.setText(label.getText());
            renderer.setForeground(label.getForeground());

            return renderer;
        }
    }

    private JLabel[] p_content;


    public AutoSuggestComboBox() {
        p_content = new JLabel[0];
        updateSuggestionModel("");

        this.setEditable(true);

        this.setRenderer(new JLabelCellRenderer());
        this.setEditor(new MetalComboBoxEditor() {
            @Override
            public void setItem(Object item) {
                if (JLabel.class.isInstance(item)) {
                    super.setItem(((JLabel) item).getText());
                } else {
                    super.setItem(item);
                }
            }
        });
        this.getEditor().getEditorComponent().addKeyListener(new AutoSuggestKeyHandler(this));
    }


    public void setContent(List<JLabel> content) {
        p_content = new JLabel[content.size()];
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
        DefaultComboBoxModel<JLabel> model = new DefaultComboBoxModel<JLabel>();

        for (JLabel label : p_content) {
            String str = label.getText();
            if (str.startsWith(text)) {
                model.addElement(label);
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
