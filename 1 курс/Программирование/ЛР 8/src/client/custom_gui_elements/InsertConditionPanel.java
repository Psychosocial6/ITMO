package client.custom_gui_elements;

import client.other.InsertCondition;

import javax.swing.*;
import java.awt.*;

public class InsertConditionPanel extends JPanel {

    private JRadioButton insertIfMaxRadioButton;
    private JRadioButton insertIfMinRadioButton;
    private ButtonGroup buttonsGroup;
    private String title;
    private String buttonMin;
    private String buttonMax;

    public InsertConditionPanel(String title, String buttonMin, String buttonMax) {
        this.title = title;
        this.buttonMax = buttonMax;
        this.buttonMin = buttonMin;
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        insertIfMaxRadioButton = new JRadioButton(buttonMax);
        insertIfMinRadioButton = new JRadioButton(buttonMin);

        buttonsGroup = new ButtonGroup();
        buttonsGroup.add(insertIfMaxRadioButton);
        buttonsGroup.add(insertIfMinRadioButton);
    }

    private void layoutComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        insertIfMaxRadioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        insertIfMinRadioButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(insertIfMaxRadioButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(insertIfMinRadioButton);

        setBorder(BorderFactory.createTitledBorder(title));
    }


    public InsertCondition getSelectedCondition() {
        if (insertIfMaxRadioButton.isSelected()) {
            return InsertCondition.IF_MAX;
        } else if (insertIfMinRadioButton.isSelected()) {
            return InsertCondition.IF_MIN;
        } else {
            return InsertCondition.NONE;
        }
    }

    public void clearSelection() {
        buttonsGroup.clearSelection();
    }

    public void setSelectedCondition(InsertCondition condition) {
        clearSelection();
        switch (condition) {
            case IF_MAX:
                insertIfMaxRadioButton.setSelected(true);
                break;
            case IF_MIN:
                insertIfMinRadioButton.setSelected(true);
                break;
            case NONE:
                break;
        }
    }
}