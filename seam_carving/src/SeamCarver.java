import java.awt.Color;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private static final double MAX_ENERGY = 1000;
    private final Picture picture;
    private final double[][] energyArray;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        checkNull(picture);
        this.picture = new Picture(picture);
        energyArray = new double[width()][height()];
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                energyArray[i][j] = calculateEnergy(i, j);
            }
        }
    }

    private double calculateEnergy(int x, int y) {
        if (isAtBorder(x, y)) {
            return MAX_ENERGY;
        }

        return Math.sqrt(dx2(x, y) + dy2(x, y));
    }

    private boolean isAtBorder(int x, int y) {
        return x == 0 || x == width() -1 ||
                y == 0 || y == height() - 1;
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

        return energyArray[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] seam = new int[width()];
        return seam;
    }

    private class Coord {
        private int x;
        private int y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }
    }

    private Iterable<Coord> adj(int x, int y) {
        Bag<Coord> coords = new Bag<Coord>();
        if (y == height() - 1) {
            return coords;
        }
        if (x > 0) {
            coords.add(new Coord(x - 1, y + 1));
        }
        coords.add(new Coord(x, y + 1));
        if (x < width() - 1) {
            coords.add(new Coord(x + 1, y + 1));
        }
        return coords;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height()];

        Coord[][] edge2D = new Coord[width()][height()];
        double[][] dist2D = new double[width()][height()];

        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) { 
                dist2D[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < width(); i++) {
            dist2D[i][0] = MAX_ENERGY;
        }
        for (int j = 0; j < height() - 1; j++) { // No need to go to last line
            for (int i = 0; i < width(); i++) {
                for (Coord coord : adj(i, j)) {
                    int x = coord.x(), y = coord.y();
                    if (dist2D[x][y] > dist2D[i][j] + energy(x, y)) {
                        dist2D[x][y] = dist2D[i][j] + energy(x, y);
                        edge2D[x][y] = new Coord(i, j);
                    }
                }
            }
        }

        // TODO: get the whole path as a seam

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
