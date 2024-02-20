package models;

public class Statistics {
    final double average;
    final double median;
    final double highest;
    final double lowest;

    public Statistics(double average, double median, double highest, double lowest) {
        this.average = average;
        this.median = median;
        this.highest = highest;
        this.lowest = lowest;
    }

    public double getAverage() {
        return average;
    }

    public double getMedian() {
        return median;
    }

    public double getHighest() {
        return highest;
    }

    public double getLowest() {
        return lowest;
    }

    @Override
    public String toString() {
        return ("Average: " + average + "\n")
        + ("Median: " + median + "\n")
        + ("Highest: " + highest + "\n")
        + ("Lowest: " + lowest + "\n");
    }
}
