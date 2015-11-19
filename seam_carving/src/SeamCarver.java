import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private final Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        checkNull(picture);
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
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
        return 0;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        checkNull(seam);
        checkLength(seam, width());
        checkValidSeam(seam);
        if (height() <= 1) {
            throw new IllegalArgumentException("Height is too small");
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        checkNull(seam);
        checkLength(seam, height());
        checkValidSeam(seam);
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

    private void checkValidSeam(int[] seam) {
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i-1]) > 1) {
                throw new IllegalArgumentException("Seam is invalid");
            }
        }
    }
}
