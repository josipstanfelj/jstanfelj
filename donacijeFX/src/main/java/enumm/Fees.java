package enumm;

public enum Fees {

    ADMINISTRATIVE_COST(0.05),
    GOVERNMENT_TAX(0.07),
    TRANSACTION_COST(0.02);

    private final double percentage;
    Fees(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }
}
