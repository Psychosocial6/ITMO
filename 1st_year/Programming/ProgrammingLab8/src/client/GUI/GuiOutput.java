package client.GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GuiOutput {
    private JTextArea outputArea;

    public GuiOutput() {
        JFrame frame = new JFrame("Вывод данных");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);



    }

    public void printToGui(String response) {
        outputArea.append(response + "\n"); // как System.out.println
    }


}