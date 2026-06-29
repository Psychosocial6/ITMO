package client.GUI;

import client.LangManager;
import client.eventHandlers.DeleteHandler;
import client.eventHandlers.EditHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *The {@code GUI.ElementInfoPageGUI} describes GUI of element's info page in Collection Viewer from {@code ProgrammingLab8}.
 * Creates standard configuration with Title, Element Info text field, Edit & Delete buttons.
 * @author Andrey
 * */
public class ElementInfoPageGUI {
    public static final Dimension INFO_WINDOW_SIZE = new Dimension(300, 600);
    public static String TITLE = LangManager.get("info.page.title");

    public static final Dimension BUTTON_SIZE = new Dimension(100, 50);
    public static String BUTTON_EDIT_TITLE = LangManager.get("edit.button.title");
    public static String BUTTON_DELETE_TITLE = LangManager.get("info.delete.title");

    public static final Dimension INFO_TEXT_FIELD_SIZE = new Dimension(250, 200);

    public static final Dimension LABEL_SIZE = new Dimension(175, 20);
    public static String LABEL_TEXT =  LangManager.get("info.page.title");

    public static final int VERTICAL_STRUT = 20;
    public static final int HORIZONTAL_STRUT = 10;

    public static final Color BUTTON_COLOR = new Color(0, 169, 255);

    private DeleteHandler deleteHandler;
    private JDialog window;
    private JButton editButton;
    private JButton deleteButton;
    private JLabel label;
    private JTextArea infoField;
    private String elementData;
    private Long id;

    public ElementInfoPageGUI(String movie) {
        elementData = movie;
        window = new JDialog();
        window.setTitle(TITLE);
        window.setSize(INFO_WINDOW_SIZE);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        editButton = createEditButton();
        deleteButton = createDeleteButton();
        label = createLabel();
        infoField = createInfoTextArea();
    }


    private JButton createEditButton() {
        JButton button = new JButton();
        button.setText(BUTTON_EDIT_TITLE);
        button.setSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditPageGUI editPageGUI = new EditPageGUI();
                EditHandler editHandler = new EditHandler(id);
                editPageGUI.setEditHandler(editHandler);
                editPageGUI.createAndShowWindow();
            }
        });

        return button;
    }

    /**
     * Method which creates delete button
     * @return {@code JButton} - new {@code JButton} with pre created configuration
     * */
    private JButton createDeleteButton() {
        JButton button = new JButton();
        button.setText(BUTTON_DELETE_TITLE);
        button.setSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteHandler.delete();
            }
        });

        return button;
    }

    /**
     * Method which creates text area with element's info
     * @return {@code JTextArea} - new {@code JTextArea} with pre created configuration
     * */
    private JTextArea createInfoTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setSize(INFO_TEXT_FIELD_SIZE);
        textArea.setMaximumSize(INFO_TEXT_FIELD_SIZE);
        textArea.setText(elementData);
        textArea.setEditable(false);

        return textArea;
    }

    /**
     * Method which creates label
     * @return {@code JLabel} - new {@code JLabel} with pre created configuration
     * */
    private JLabel createLabel() {
        JLabel label = new JLabel();
        label.setSize(LABEL_SIZE);
        label.setMaximumSize(LABEL_SIZE);
        label.setText(LABEL_TEXT);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    /**
     * Method which creates element info window and shows it
     */
    public void createAndShowWindow() {
    	setLanguageInfoPage();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoField.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(editButton);
        buttonPanel.add(Box.createHorizontalStrut(HORIZONTAL_STRUT));
        buttonPanel.add(deleteButton);

        panel.add(label);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(infoField);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        window.add(panel);
        window.setVisible(true);
    }
    public void setLanguageInfoPage() {
    	TITLE = LangManager.get("info.page.title");
        BUTTON_EDIT_TITLE = LangManager.get("edit.button.title");
        BUTTON_DELETE_TITLE = LangManager.get("info.delete.title");
        LABEL_TEXT =  LangManager.get("info.page.title");
    }

    public void setDeleteHandler(DeleteHandler deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
