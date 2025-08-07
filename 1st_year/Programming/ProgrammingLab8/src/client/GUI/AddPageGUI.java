package client.GUI;

import client.LangManager;
import client.custom_gui_elements.InsertConditionPanel;
import client.dataStorage.DataForMovie;
import client.eventHandlers.AddHandler;
import client.other.InsertCondition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;
import java.util.Map;

public class AddPageGUI {

    public static final int VERTICAL_STRUT = 5;
    public static final int BIG_VERTICAL_STRUT = 10;

    public static final String NAME_HINT = "name";
    public static final String COORDINATE_X_HINT = "X";
    public static final String COORDINATE_Y_HINT = "Y";
    public static final String OSCARS_COUNT_HINT = "oscars count";
    public static final String TOTAL_BOX_OFFICE_HINT = "total box office";
    public static final String USA_BOX_OFFICE_HINT = "usa box office";
    public static final String GENRE_HINT = "genre";
    public static final String OPERATOR_NAME_HINT = "operator name";
    public static final String OPERATOR_HEIGHT_HINT = "operator height";
    public static final String OPERATOR_EYE_COLOR_HINT = "operator eye color";
    public static final String OPERATOR_HAIR_COLOR_HINT = "operator hair color";
    public static final String OPERATOR_NATIONALITY_HINT = "operator nationality";
    public static final String OPERATOR_LOCATION_X_HINT = "operator location X";
    public static final String OPERATOR_LOCATION_Y_HINT = "operator location Y";
    public static final String OPERATOR_LOCATION_Z_HINT = "operator location Z";
    public static final String LOCATION_NAME_HINT = "location name";

    public static String BUTTON_TEXT = LangManager.get("add.button.title");
    public static String WINDOW_TITLE = LangManager.get("add.window.title");

    public static String MIN_RADIO_BUTTON = LangManager.get("insertmin.button.title");
    public static String MAX_RADIO_BUTTON = LangManager.get("insertmax.button.title");
    public static String RADIO_BUTTONS_TITLE = LangManager.get("radio.buttons.title");

    public static final Dimension WINDOW_SIZE = new Dimension(300, 550);
    public static final Dimension BUTTON_SIZE = new Dimension(100, 50);
    public static final Dimension TEXT_FIELD_SIZE = new Dimension(150, 30);

    public static final Color HINT_COLOR = Color.GRAY;
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color BUTTON_COLOR = new Color(0, 169, 255);

    private JTextField nameTextField;
    private JTextField coordinateX;
    private JTextField coordinateY;
    private JTextField oscarsCount;
    private JTextField totalBoxOffice;
    private JTextField usaBoxOffice;
    private JTextField genre;
    private JTextField operatorName;
    private JTextField operatorHeight;
    private JTextField operatorEyeColor;
    private JTextField operatorHairColor;
    private JTextField operatorNationality;
    private JTextField operatorLocationX;
    private JTextField operatorLocationY;
    private JTextField operatorLocationZ;
    private JTextField locationName;
    private JButton button;
    private InsertConditionPanel insertConditionPanel;
    private JDialog window;
    private AddHandler addHandler;

    private InsertCondition condition;

    public AddPageGUI() {
        window = new JDialog();
        window.setSize(WINDOW_SIZE);
        window.setTitle(WINDOW_TITLE);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        createAddButton();
        createInsertConditionPanel();
        createTextFields();
    }

    private void createAddButton() {
        button = new JButton();
        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);
        button.setText(BUTTON_TEXT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Object> elementFields = collectData();
                condition = insertConditionPanel.getSelectedCondition();
                addHandler.add(elementFields, condition);
            }
        });
    }

    private void createTextFields() {
        nameTextField = new JTextField();
        nameTextField.setPreferredSize(TEXT_FIELD_SIZE);
        nameTextField.setMaximumSize(TEXT_FIELD_SIZE);
        nameTextField.setText(NAME_HINT);
        nameTextField.setForeground(HINT_COLOR);
        nameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameTextField.getText().equals(NAME_HINT)) {
                    nameTextField.setText("");
                    nameTextField.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameTextField.getText().isEmpty()) {
                    nameTextField.setText(NAME_HINT);
                    nameTextField.setForeground(HINT_COLOR);
                }
            }
        });

        coordinateX = new JTextField();
        coordinateX.setPreferredSize(TEXT_FIELD_SIZE);
        coordinateX.setMaximumSize(TEXT_FIELD_SIZE);
        coordinateX.setText(COORDINATE_X_HINT);
        coordinateX.setForeground(HINT_COLOR);
        coordinateX.setAlignmentX(Component.CENTER_ALIGNMENT);
        coordinateX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (coordinateX.getText().equals(COORDINATE_X_HINT)) {
                    coordinateX.setText("");
                    coordinateX.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (coordinateX.getText().isEmpty()) {
                    coordinateX.setText(COORDINATE_X_HINT);
                    coordinateX.setForeground(HINT_COLOR);
                }
            }
        });

        coordinateY = new JTextField();
        coordinateY.setPreferredSize(TEXT_FIELD_SIZE);
        coordinateY.setMaximumSize(TEXT_FIELD_SIZE);
        coordinateY.setText(COORDINATE_Y_HINT);
        coordinateY.setForeground(HINT_COLOR);
        coordinateY.setAlignmentX(Component.CENTER_ALIGNMENT);
        coordinateY.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (coordinateY.getText().equals(COORDINATE_Y_HINT)) {
                    coordinateY.setText("");
                    coordinateY.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (coordinateY.getText().isEmpty()) {
                    coordinateY.setText(COORDINATE_Y_HINT);
                    coordinateY.setForeground(HINT_COLOR);
                }
            }
        });

        oscarsCount = new JTextField();
        oscarsCount.setPreferredSize(TEXT_FIELD_SIZE);
        oscarsCount.setMaximumSize(TEXT_FIELD_SIZE);
        oscarsCount.setText(OSCARS_COUNT_HINT);
        oscarsCount.setForeground(HINT_COLOR);
        oscarsCount.setAlignmentX(Component.CENTER_ALIGNMENT);
        oscarsCount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (oscarsCount.getText().equals(OSCARS_COUNT_HINT)) {
                    oscarsCount.setText("");
                    oscarsCount.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (oscarsCount.getText().isEmpty()) {
                    oscarsCount.setText(OSCARS_COUNT_HINT);
                    oscarsCount.setForeground(HINT_COLOR);
                }
            }
        });

        totalBoxOffice = new JTextField();
        totalBoxOffice.setPreferredSize(TEXT_FIELD_SIZE);
        totalBoxOffice.setMaximumSize(TEXT_FIELD_SIZE);
        totalBoxOffice.setText(TOTAL_BOX_OFFICE_HINT);
        totalBoxOffice.setForeground(HINT_COLOR);
        totalBoxOffice.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalBoxOffice.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (totalBoxOffice.getText().equals(TOTAL_BOX_OFFICE_HINT)) {
                    totalBoxOffice.setText("");
                    totalBoxOffice.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (totalBoxOffice.getText().isEmpty()) {
                    totalBoxOffice.setText(TOTAL_BOX_OFFICE_HINT);
                    totalBoxOffice.setForeground(HINT_COLOR);
                }
            }
        });

        usaBoxOffice = new JTextField();
        usaBoxOffice.setPreferredSize(TEXT_FIELD_SIZE);
        usaBoxOffice.setMaximumSize(TEXT_FIELD_SIZE);
        usaBoxOffice.setText(USA_BOX_OFFICE_HINT);
        usaBoxOffice.setForeground(HINT_COLOR);
        usaBoxOffice.setAlignmentX(Component.CENTER_ALIGNMENT);
        usaBoxOffice.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usaBoxOffice.getText().equals(USA_BOX_OFFICE_HINT)) {
                    usaBoxOffice.setText("");
                    usaBoxOffice.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usaBoxOffice.getText().isEmpty()) {
                    usaBoxOffice.setText(USA_BOX_OFFICE_HINT);
                    usaBoxOffice.setForeground(HINT_COLOR);
                }
            }
        });

        genre = new JTextField();
        genre.setPreferredSize(TEXT_FIELD_SIZE);
        genre.setMaximumSize(TEXT_FIELD_SIZE);
        genre.setText(GENRE_HINT);
        genre.setForeground(HINT_COLOR);
        genre.setAlignmentX(Component.CENTER_ALIGNMENT);
        genre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (genre.getText().equals(GENRE_HINT)) {
                    genre.setText("");
                    genre.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (genre.getText().isEmpty()) {
                    genre.setText(GENRE_HINT);
                    genre.setForeground(HINT_COLOR);
                }
            }
        });

        operatorName = new JTextField();
        operatorName.setPreferredSize(TEXT_FIELD_SIZE);
        operatorName.setMaximumSize(TEXT_FIELD_SIZE);
        operatorName.setText(OPERATOR_NAME_HINT);
        operatorName.setForeground(HINT_COLOR);
        operatorName.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (operatorName.getText().equals(OPERATOR_NAME_HINT)) {
                    operatorName.setText("");
                    operatorName.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (operatorName.getText().isEmpty()) {
                    operatorName.setText(OPERATOR_NAME_HINT);
                    operatorName.setForeground(HINT_COLOR);
                }
            }
        });

        operatorHeight = new JTextField();
        operatorHeight.setPreferredSize(TEXT_FIELD_SIZE);
        operatorHeight.setMaximumSize(TEXT_FIELD_SIZE);
        operatorHeight.setText(OPERATOR_HEIGHT_HINT);
        operatorHeight.setForeground(HINT_COLOR);
        operatorHeight.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorHeight.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (operatorHeight.getText().equals(OPERATOR_HEIGHT_HINT)) {
                    operatorHeight.setText("");
                    operatorHeight.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (operatorHeight.getText().isEmpty()) {
                    operatorHeight.setText(OPERATOR_HEIGHT_HINT);
                    operatorHeight.setForeground(HINT_COLOR);
                }
            }
        });

        operatorEyeColor = new JTextField();
        operatorEyeColor.setPreferredSize(TEXT_FIELD_SIZE);
        operatorEyeColor.setMaximumSize(TEXT_FIELD_SIZE);
        operatorEyeColor.setText(OPERATOR_EYE_COLOR_HINT);
        operatorEyeColor.setForeground(HINT_COLOR);
        operatorEyeColor.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorEyeColor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (operatorEyeColor.getText().equals(OPERATOR_EYE_COLOR_HINT)) {
                    operatorEyeColor.setText("");
                    operatorEyeColor.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (operatorEyeColor.getText().isEmpty()) {
                    operatorEyeColor.setText(OPERATOR_EYE_COLOR_HINT);
                    operatorEyeColor.setForeground(HINT_COLOR);
                }
            }
        });

        operatorHairColor = new JTextField();
        operatorHairColor.setPreferredSize(TEXT_FIELD_SIZE);
        operatorHairColor.setMaximumSize(TEXT_FIELD_SIZE);
        operatorHairColor.setText(OPERATOR_HAIR_COLOR_HINT);
        operatorHairColor.setForeground(HINT_COLOR);
        operatorHairColor.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorHairColor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (operatorHairColor.getText().equals(OPERATOR_HAIR_COLOR_HINT)) {
                    operatorHairColor.setText("");
                    operatorHairColor.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (operatorHairColor.getText().isEmpty()) {
                    operatorHairColor.setText(OPERATOR_HAIR_COLOR_HINT);
                    operatorHairColor.setForeground(HINT_COLOR);
                }
            }
        });

        operatorNationality = new JTextField();
        operatorNationality.setPreferredSize(TEXT_FIELD_SIZE);
        operatorNationality.setMaximumSize(TEXT_FIELD_SIZE);
        operatorNationality.setText(OPERATOR_NATIONALITY_HINT);
        operatorNationality.setForeground(HINT_COLOR);
        operatorNationality.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorNationality.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (operatorNationality.getText().equals(OPERATOR_NATIONALITY_HINT)) {
                    operatorNationality.setText("");
                    operatorNationality.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (operatorNationality.getText().isEmpty()) {
                    operatorNationality.setText(OPERATOR_NATIONALITY_HINT);
                    operatorNationality.setForeground(HINT_COLOR);
                }
            }
        });

        operatorLocationX = new JTextField();
        operatorLocationX.setPreferredSize(TEXT_FIELD_SIZE);
        operatorLocationX.setMaximumSize(TEXT_FIELD_SIZE);
        operatorLocationX.setText(OPERATOR_LOCATION_X_HINT);
        operatorLocationX.setForeground(HINT_COLOR);
        operatorLocationX.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorLocationX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (operatorLocationX.getText().equals(OPERATOR_LOCATION_X_HINT)) {
                    operatorLocationX.setText("");
                    operatorLocationX.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (operatorLocationX.getText().isEmpty()) {
                    operatorLocationX.setText(OPERATOR_LOCATION_X_HINT);
                    operatorLocationX.setForeground(HINT_COLOR);
                }
            }
        });

        operatorLocationY = new JTextField();
        operatorLocationY.setPreferredSize(TEXT_FIELD_SIZE);
        operatorLocationY.setMaximumSize(TEXT_FIELD_SIZE);
        operatorLocationY.setText(OPERATOR_LOCATION_Y_HINT);
        operatorLocationY.setForeground(HINT_COLOR);
        operatorLocationY.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorLocationY.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (operatorLocationY.getText().equals(OPERATOR_LOCATION_Y_HINT)) {
                    operatorLocationY.setText("");
                    operatorLocationY.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (operatorLocationY.getText().isEmpty()) {
                    operatorLocationY.setText(OPERATOR_LOCATION_Y_HINT);
                    operatorLocationY.setForeground(HINT_COLOR);
                }
            }
        });

        operatorLocationZ = new JTextField();
        operatorLocationZ.setPreferredSize(TEXT_FIELD_SIZE);
        operatorLocationZ.setMaximumSize(TEXT_FIELD_SIZE);
        operatorLocationZ.setText(OPERATOR_LOCATION_Z_HINT);
        operatorLocationZ.setForeground(HINT_COLOR);
        operatorLocationZ.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorLocationZ.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (operatorLocationZ.getText().equals(OPERATOR_LOCATION_Z_HINT)) {
                    operatorLocationZ.setText("");
                    operatorLocationZ.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (operatorLocationZ.getText().isEmpty()) {
                    operatorLocationZ.setText(OPERATOR_LOCATION_Z_HINT);
                    operatorLocationZ.setForeground(HINT_COLOR);
                }
            }
        });

        locationName = new JTextField();
        locationName.setPreferredSize(TEXT_FIELD_SIZE);
        locationName.setMaximumSize(TEXT_FIELD_SIZE);
        locationName.setText(LOCATION_NAME_HINT);
        locationName.setForeground(HINT_COLOR);
        locationName.setAlignmentX(Component.CENTER_ALIGNMENT);
        locationName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (locationName.getText().equals(LOCATION_NAME_HINT)) {
                    locationName.setText("");
                    locationName.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (locationName.getText().isEmpty()) {
                    locationName.setText(LOCATION_NAME_HINT);
                    locationName.setForeground(HINT_COLOR);
                }
            }
        });
    }

    private void createInsertConditionPanel() {
        insertConditionPanel = new InsertConditionPanel(RADIO_BUTTONS_TITLE, MIN_RADIO_BUTTON, MAX_RADIO_BUTTON);
    }

    private Map<String, Object> collectData() {
        HashMap<String, Object> elementFields = new HashMap<>();

        elementFields.put("Name", nameTextField.getText());
        elementFields.put("Coordinates_X", Integer.parseInt(coordinateX.getText()));
        elementFields.put("Coordinates_Y", Long.parseLong(coordinateY.getText()));
        elementFields.put("OscarsCount", Integer.parseInt(oscarsCount.getText()));
        elementFields.put("TotalBoxOffice", Double.parseDouble(totalBoxOffice.getText()));
        elementFields.put("UsaBoxOffice", Double.parseDouble(usaBoxOffice.getText()));
        try {
            int index = Integer.parseInt(genre.getText());
            elementFields.put("Genre", DataForMovie.genreNames[index].toUpperCase());
        }
        catch (Exception e) {
            elementFields.put("Genre", genre.getText());
        }
        elementFields.put("Operator_Name", operatorName.getText());
        elementFields.put("Operator_Height", Integer.parseInt(operatorHeight.getText()));
        try {
            int index = Integer.parseInt(operatorEyeColor.getText());
            elementFields.put("Operator_Eye", DataForMovie.colorNames[index].toUpperCase());
        }
        catch (Exception e) {
            elementFields.put("Operator_Eye", operatorEyeColor.getText());
        }
        try {
            int index = Integer.parseInt(operatorHairColor.getText());
            elementFields.put("Operator_Hair", DataForMovie.colorNames[index].toUpperCase());
        }
        catch (Exception e) {
            elementFields.put("Operator_Hair", operatorHairColor.getText());
        }
        try {
            int index = Integer.parseInt(operatorNationality.getText());
            elementFields.put("Operator_Nation", DataForMovie.countryNames[index].toUpperCase());
        }
        catch (Exception e) {
            elementFields.put("Operator_Nation", operatorNationality.getText());
        }
        elementFields.put("Location_X", Integer.parseInt(operatorLocationX.getText()));
        elementFields.put("Location_Y", Long.parseLong(operatorLocationY.getText()));
        elementFields.put("Location_Z", Integer.parseInt(operatorLocationZ.getText()));
        elementFields.put("Location_Name", locationName.getText());

        return elementFields;
    }

    public void createAndShowWindow() {
    	setLanguageAddPage();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        coordinateX.setAlignmentX(Component.CENTER_ALIGNMENT);
        coordinateY.setAlignmentX(Component.CENTER_ALIGNMENT);
        oscarsCount.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalBoxOffice.setAlignmentX(Component.CENTER_ALIGNMENT);
        usaBoxOffice.setAlignmentX(Component.CENTER_ALIGNMENT);
        genre.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorName.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorHeight.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorEyeColor.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorHairColor.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorNationality.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorLocationX.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorLocationY.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorLocationZ.setAlignmentX(Component.CENTER_ALIGNMENT);
        locationName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        insertConditionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(button);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(insertConditionPanel);

        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(nameTextField);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(coordinateX);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(coordinateY);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(oscarsCount);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(totalBoxOffice);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(usaBoxOffice);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(genre);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(operatorName);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(operatorHeight);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(operatorEyeColor);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(operatorHairColor);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(operatorNationality);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(operatorLocationX);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(operatorLocationY);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(operatorLocationZ);
        panel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        panel.add(locationName);
        panel.add(Box.createVerticalStrut(BIG_VERTICAL_STRUT));
        panel.add(buttonsPanel);

        window.add(panel);
        window.setVisible(true);
    }
    public void setLanguageAddPage() {
    	BUTTON_TEXT = LangManager.get("add.button.title");
        WINDOW_TITLE = LangManager.get("add.window.title");
        MIN_RADIO_BUTTON = LangManager.get("insertmin.button.title");
        MAX_RADIO_BUTTON = LangManager.get("insertmax.button.title");
        RADIO_BUTTONS_TITLE = LangManager.get("radio.buttons.title");
    }
    public void setAddHandler(AddHandler addHandler) {
        this.addHandler = addHandler;
    }
}
