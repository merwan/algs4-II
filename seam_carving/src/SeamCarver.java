import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private static final double MAX_ENERGY = 1000;
    private final Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        checkNull(picture);
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width()) {
            throw new IndexOutOfBoundsException("x is out of bounds");
        }
        if (y < 0 || y >= height()) {
            throw new IndexOutOfBoundsException("y is out of bounds");
        }

        if (isAtBorder(x, y)) {
            return MAX_ENERGY;
        }

        return Math.sqrt(dx2(x, y) + dy2(x, y));
    }

    private double dx2(int x, int y) {
        Color color2 = picture.get(x + 1, y);
        Color color1 = picture.get(x - 1, y);
        double R = color2.getRed() - color1.getRed();
        double G = color2.getGreen() - color1.getGreen();
        double B = color2.getBlue() - color1.getBlue();
        return R*R + G*G + B*B;
    }

    private double dy2(int x, int y) {
        Color color2 = picture.get(x, y + 1);
        Color color1 = picture.get(x, y - 1);
        double R = color2.getRed() - color1.getRed();
        double G = color2.getGreen() - color1.getGreen();
        double B = color2.getBlue() - color1.getBlue();
        return R*R + G*G + B*B;
    }

    private boolean isAtBorder(int x, int y) {
        return x == 0 || x == width() -1 ||
                y == 0 || y == height() - 1;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] seam = new int[width()];
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height()];
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        checkNull(seam);
        checkLength(seam, width());
        checkValidSeam(seam, height());
        if (height() <= 1) {
            throw new IllegalArgumentException("Height is too small");
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        checkNull(seam);
        checkLength(seam, height());
        checkValidSeam(seam, width());
        if (width() <= 1) {
            throw new IllegalArgumentException("Width is too small");
        }
    }

    private void checkNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    private void checkLength(int[] seam, int length) {
        if (seam.length != length) {
            throw new IllegalArgumentException("Array length is not valid");
        }
    }

    private void checkValidSeam(int[] seam, int length) {
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= length) {
                throw new IllegalArgumentException("Seam value is out of range");
            }
        }

        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i-1]) > 1) {
                throw new IllegalArgumentException("Seam is invalid");
            }
        }
    }
}
