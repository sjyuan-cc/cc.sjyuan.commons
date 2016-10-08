package cc.sjyuan.commons.util.rate;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class IndividualPostTaxIncomeCounterTest {

    @Test
    public void testPostTaxIncome() throws Exception {

        double preTaxIncome = 5000; // 税前总收入
        double socialSecurityAndProvidentFound = 1000; // 扣除社保及公积金

        IndividualPostTaxIncomeCounter.Builder builder = IndividualPostTaxIncomeCounter.builder();

        IndividualPostTaxIncomeCounter counter = builder.preTaxIncome(preTaxIncome)
                .socialSecurityAndProvidentFound(socialSecurityAndProvidentFound)
                .build();

        assertEquals(3985.0, counter.postTaxIncome());
    }
}