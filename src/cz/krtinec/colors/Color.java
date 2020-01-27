package cz.krtinec.colors;

class Color implements Comparable<Color> {
    private Integer rgb;
    private Integer count;

    public Color(Integer rgb, Integer count) {
        this.rgb = rgb;
        this.count = count;
    }

    @Override
    public int compareTo(Color o) {
        return - this.count.compareTo(o.count);
    }

    @Override
    public String toString() {
        return String.format("#%06X", (0xFFFFFF & rgb));
    }
}
