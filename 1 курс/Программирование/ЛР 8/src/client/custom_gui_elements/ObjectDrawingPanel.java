package client.custom_gui_elements;

import client.Client;
import client.GUI.ElementInfoPageGUI;
import client.dataStorage.CollectionView;
import client.eventHandlers.DeleteHandler;
import client.eventHandlers.InfoHandler;
import client.other.DrawableObject;
import client.other.TableElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ObjectDrawingPanel extends JPanel {

    private ArrayList<DrawableObject> objectsToDraw;
    private Timer animationTimer;
    private static final int ANIMATION_FRAME_DELAY = 5;

    public ObjectDrawingPanel(InfoHandler infoHandler) {
        objectsToDraw = new ArrayList<>();
        setBackground(Color.WHITE);

        animationTimer = new Timer(ANIMATION_FRAME_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAnimationStep();
            }
        });
        animationTimer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = objectsToDraw.size() - 1; i >= 0; i--) {
                    DrawableObject obj = objectsToDraw.get(i);
                    if (obj.contains(e.getPoint())) {
                        ArrayList<TableElement> elements = ((MovieTableModel)Client.mainPageGUI.getElementsTable().getModel()).getMovies();
                        long id = elements.get(i).getId();
                        InfoHandler infoHandler = new InfoHandler(Client.sender);
                        String movie = infoHandler.info(id);

                        ElementInfoPageGUI elementInfoPageGUI = new ElementInfoPageGUI(movie);
                        DeleteHandler deleteHandler = new DeleteHandler(elementInfoPageGUI, Client.sender);
                        elementInfoPageGUI.setDeleteHandler(deleteHandler);
                        elementInfoPageGUI.setId(id);
                        elementInfoPageGUI.createAndShowWindow();
                        break;
                    }
                }
            }
        });
    }

    private void runAnimationStep() {
        boolean needsRepaint = false;
        for (DrawableObject obj : objectsToDraw) {
            if (obj.isAnimating()) {
                if (obj.updateAnimationStep()) {
                    needsRepaint = true;
                }
                else {
                    needsRepaint = true;
                }
            }
        }

        if (needsRepaint) {
            repaint();
        }
    }


    public void addObject(DrawableObject obj) {
        if (obj != null) {
            obj.startAnimation();
            objectsToDraw.add(obj);
        }
    }

    public void clearObjects() {
        this.objectsToDraw.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ArrayList<DrawableObject> objectsSnapshot = new ArrayList<>(objectsToDraw);
        for (DrawableObject obj : objectsSnapshot) {
            obj.draw(g2d);
        }
        g2d.dispose();
    }

}