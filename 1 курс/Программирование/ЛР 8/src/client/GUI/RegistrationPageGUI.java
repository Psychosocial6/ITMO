package client.GUI;

import client.eventHandlers.LoginHandler;
import client.eventHandlers.RegistrationHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

/**
 *The {@code GUI.RegistrationPageGUI} describes GUI of registration page in Collection Viewer from {@code ProgrammingLab8}.
 * Creates standard configuration with Title, Login & Password input fields and Confirm button.
 * @author Andrey
 * */
public class RegistrationPageGUI {
    public static final Dimension REGISTRATION_WINDOW_SIZE = new Dimension(350, 300);
    public static final String TITLE = "Registration";

    public static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    public static final String REGISTER_BUTTON_TITLE = "Register";
    public static final String LOGIN_BUTTON_TITLE = "Login";

    public static final Dimension PASSWORD_FIELD_SIZE = new Dimension(200, 30);
    public static final String PASSWORD_HINT = "Password";

    public static final Dimension LOGIN_FIELD_SIZE = new Dimension(200, 30);
    public static final String LOGIN_HINT = "Login";

    public static final Dimension LABEL_SIZE = new Dimension(175, 20);
    public static final String LABEL_TEXT = "Registration";

    public static final int BIG_VERTICAL_STRUT = 40;
    public static final int SMALL_VERTICAL_STRUT = 10;

    public static final Color BUTTON_COLOR = new Color(0, 169, 255);
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color HINT_COLOR = Color.GRAY;

    private JDialog window;
    private JLabel label;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPanel button;
    private RegistrationHandler registrationHandler;
    private LoginHandler loginHandler;

    public RegistrationPageGUI() {
        JFrame parentFrame = null;
        window = new JDialog(parentFrame, TITLE, true);
        window.setSize(REGISTRATION_WINDOW_SIZE);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        label = createLabel();
        loginField = createTextField();
        passwordField = createPasswordField();
        button = createConfirmButtons();
    }

    /**
     * Method which creates confirming button
     * @return {@code JButton} - new {@code JButton} with pre created configuration
     * */
    private JPanel createConfirmButtons() {
        JButton buttonRegister = new JButton();
        buttonRegister.setText(REGISTER_BUTTON_TITLE);
        buttonRegister.setSize(BUTTON_SIZE);
        buttonRegister.setMaximumSize(BUTTON_SIZE);
        buttonRegister.setBackground(BUTTON_COLOR);

        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrationHandler.register(loginField.getText(), Arrays.toString(passwordField.getPassword()));
            }
        });

        JButton buttonLogin = new JButton();
        buttonLogin.setText(LOGIN_BUTTON_TITLE);
        buttonLogin.setSize(BUTTON_SIZE);
        buttonLogin.setMaximumSize(BUTTON_SIZE);
        buttonLogin.setBackground(BUTTON_COLOR);

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginHandler.login(loginField.getText(), Arrays.toString(passwordField.getPassword()));
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(buttonRegister);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(buttonLogin);
        return panel;
    }

    /**
     * Method which creates text field for password
     * @return {@code JPasswordField} - new {@code JPasswordField} with pre created configuration
     * */
    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setSize(PASSWORD_FIELD_SIZE);
        passwordField.setMaximumSize(PASSWORD_FIELD_SIZE);
        char echoChar = passwordField.getEchoChar();
        passwordField.setText(PASSWORD_HINT);
        passwordField.setForeground(HINT_COLOR);
        passwordField.setEchoChar((char) 0);

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(PASSWORD_HINT)) {
                    passwordField.setEchoChar(echoChar);
                    passwordField.setText("");
                    passwordField.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(HINT_COLOR);
                    passwordField.setText(PASSWORD_HINT);
                }
            }
        });

        return passwordField;
    }

    /**
     * Method which creates text field for login
     * @return {@code JTextField} - new {@code JTextField} with pre created configuration
     * */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setSize(LOGIN_FIELD_SIZE);
        textField.setMaximumSize(LOGIN_FIELD_SIZE);
        textField.setText(LOGIN_HINT);
        textField.setForeground(HINT_COLOR);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(LOGIN_HINT)) {
                    textField.setText("");
                    textField.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(LOGIN_HINT);
                    textField.setForeground(HINT_COLOR);
                }
            }
        });

        return textField;
    }

    /**
     * Method which creates registration window's label
     * @return  {@code JLabel} - new {@code JLabel} with pre created configuration and label's text
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
     * Method which creates registration window and shows it
     */
    public void createAndShowWindow() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(label);
        panel.add(Box.createVerticalStrut(BIG_VERTICAL_STRUT));
        panel.add(loginField);
        panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(BIG_VERTICAL_STRUT));
        panel.add(button);
        panel.add(Box.createVerticalGlue());

        window.add(panel);
        window.setVisible(true);
    }

    public void setRegistrationHandler(RegistrationHandler registrationHandler) {
        this.registrationHandler = registrationHandler;
    }

    public void setLoginHandler(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    public JDialog getWindow() {
        return window;
    }
}
