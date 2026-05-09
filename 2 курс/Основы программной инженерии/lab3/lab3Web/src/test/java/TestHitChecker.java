import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utility.HitChecker;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestHitChecker {

    private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal r;
    private final boolean expected;

    public TestHitChecker(BigDecimal x, BigDecimal y, BigDecimal r, boolean expected) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: check({0}, {1}, {2}) = {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { new BigDecimal(0),  new BigDecimal(1),   new BigDecimal(3),  true  },
                { new BigDecimal(1),  new BigDecimal(-1),  new BigDecimal(1),  false }
        });
    }

    @Test
    public void testHitChecker() {
        boolean actual = HitChecker.check(x, y, r);
        assertEquals(expected, actual);
    }
}