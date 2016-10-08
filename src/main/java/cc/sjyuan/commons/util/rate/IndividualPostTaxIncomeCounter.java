package cc.sjyuan.commons.util.rate;

public class IndividualPostTaxIncomeCounter {
    private static final double RATE_BELOW_1500 = .03;
    private static final double RATE_1500_4500 = .1;
    private static final double RATE_4500_9000 = .2;
    private static final double RATE_9000_35000 = .25;
    private static final double RATE_35000_55000 = .30;
    private static final double RATE_55000_80000 = .35;
    private static final double RATE_ABOVE_80000 = .40;

    private static final double THRESHOULD = 3500.0;

    private final double preTaxIncome;
    private final double socialSecurityAndProvidentFound;

    private IndividualPostTaxIncomeCounter(double preTaxIncome, double socialSecurityAndProvidentFound) {
        this.preTaxIncome = preTaxIncome;
        this.socialSecurityAndProvidentFound = socialSecurityAndProvidentFound;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private double preTaxIncome;
        private double socialSecurityAndProvidentFound;

        public Builder preTaxIncome(double preTaxIncome) {
            this.preTaxIncome = preTaxIncome;
            return this;
        }

        public Builder socialSecurityAndProvidentFound(double socialSecurityAndProvidentFound) {
            this.socialSecurityAndProvidentFound = socialSecurityAndProvidentFound;
            return this;
        }

        public IndividualPostTaxIncomeCounter build() {
            return new IndividualPostTaxIncomeCounter(preTaxIncome, socialSecurityAndProvidentFound);
        }
    }

    public double postTaxIncome() {
        double taxIncome = preTaxIncome - socialSecurityAndProvidentFound - THRESHOULD;
        double tax;
        if (taxIncome <= 0) {
            tax = 0;
        } else if (taxIncome <= 1500.0) {
            tax = taxIncome * RATE_BELOW_1500;
        } else if (taxIncome <= 4500.0) {
            tax = 1500.0 * RATE_BELOW_1500 + (taxIncome - 1500) * RATE_1500_4500;
        } else if (taxIncome <= 9000.0) {
            tax = 1500.0 * RATE_BELOW_1500 + 3000.0 * RATE_1500_4500 + (taxIncome - 4500.0) * RATE_4500_9000;
        } else if (taxIncome <= 35000.0) {
            tax = 1500.0 * RATE_BELOW_1500 + 3000.0 * RATE_1500_4500 + 4500.0 * RATE_4500_9000 + (taxIncome - 9000.0)
                    * RATE_9000_35000;
        } else if (taxIncome <= 55000.0) {
            tax = 1500.0 * RATE_BELOW_1500 + 3000.0 * RATE_1500_4500 + 4500.0 * RATE_4500_9000 + 26000.0 *
                    RATE_9000_35000 + (taxIncome - 35000.0) * RATE_35000_55000;
        } else if (taxIncome <= 80000.0) {
            tax = 1500.0 * RATE_BELOW_1500 + 3000.0 * RATE_1500_4500 + 4500.0 * RATE_4500_9000 + 26000.0 *
                    RATE_9000_35000 + 20000.0 * RATE_35000_55000 + (taxIncome - 55000.0) * RATE_55000_80000;
        } else {
            tax = 1500.0 * RATE_BELOW_1500 + 3000.0 * RATE_1500_4500 + 4500.0 * RATE_4500_9000 + 26000.0 *
                    RATE_9000_35000 + 20000.0 * RATE_35000_55000 + 25000 * RATE_55000_80000 + (taxIncome - 80000.0) *
                    RATE_ABOVE_80000;
        }
        return preTaxIncome - socialSecurityAndProvidentFound - tax;
    }
}
