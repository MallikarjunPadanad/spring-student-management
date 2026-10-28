package in.strikes.model;

public class StudentStats {

    private final long total;
    private final double averageAge;

    public StudentStats(long total, double averageAge) {
        this.total = total;
        this.averageAge = averageAge;
    }

    public long getTotal() { return total; }
    public double getAverageAge() { return averageAge; }
}
