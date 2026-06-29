package client.other;

import java.awt.*;

public class DrawableObject {
    private Point center;
    private int radius;
    private Color color;

    private float currentAlpha;
    private int currentRadius;
    private boolean animating;
    private long animationStartTime;
    private static final int ANIMATION_DURATION_MS = 5000;

    public DrawableObject(int x, long y, int radius, Color color) {
        this.center = new Point(x, (int) y);
        this.radius = radius;
        this.color = color;

        currentRadius = 0;
        currentAlpha = 0.0f;
        animating = false;
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public boolean isAnimating() {
        return animating;
    }

    public void startAnimation() {
        animating = true;
        currentRadius = 0;
        currentAlpha = 0.0f;
        animationStartTime = System.currentTimeMillis();
    }

    public boolean updateAnimationStep() {
        if (!animating) {
            return false;
        }

        long elapsedTime = System.currentTimeMillis() - animationStartTime;
        float progress = Math.min(1.0f, (float) elapsedTime / ANIMATION_DURATION_MS);

        currentRadius = (int) (radius * progress);
        currentAlpha = progress;

        if (progress >= 1.0f) {
            animating = false;
            currentRadius = radius;
            currentAlpha = 1.0f;
            return false;
        }
        return true;
    }

    public void draw(Graphics2D g2d) {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha);
        Composite originalComposite = g2d.getComposite();
        g2d.setComposite(ac);

        g2d.setColor(this.color);
        g2d.fillOval(center.x - currentRadius, center.y - currentRadius, currentRadius * 2, currentRadius * 2);

        g2d.setComposite(originalComposite);
    }

    public boolean contains(Point p) {
        if (p == null || currentAlpha < 0.1f) {
            return false;
        }
        double distance = center.distance(p.x, p.y);
        return distance <= currentRadius;
    }
}
