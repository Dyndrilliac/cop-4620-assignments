package Project2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CMinusParserDemoRejectionTests
{
    @Test
    void testRun1()
    {
        String result = CMinusParserDemo.run("/home/matthew/Documents/Git/GitHub/cop-4620-assignments/Test Files/parse_r_t1.c", true);

        assertEquals(result,"REJECT");
    }

    @Test
    void testRun2()
    {
        String result = CMinusParserDemo.run("/home/matthew/Documents/Git/GitHub/cop-4620-assignments/Test Files/parse_r_t2.c", true);

        assertEquals(result,"REJECT");
    }
}
