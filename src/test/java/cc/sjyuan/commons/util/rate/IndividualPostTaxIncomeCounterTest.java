package cc.sjyuan.commons.util.rate;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class IndividualPostTaxIncomeCounterTest {
    @Test
    public void testPostTaxIncome() throws Exception {

        double preTaxIncome = 10000; // 税前总收入
        double socialSecurityAndProvidentFound = 2143.984; // 扣除社保及公积金

        IndividualPostTaxIncomeCounter.Builder builder = IndividualPostTaxIncomeCounter.builder();

        IndividualPostTaxIncomeCounter counter = builder.preTaxIncome(preTaxIncome)
                .socialSecurityAndProvidentFound(socialSecurityAndProvidentFound)
                .build();

        String.join()
        assertEquals(3985.0, counter.postTaxIncome());
    }
}