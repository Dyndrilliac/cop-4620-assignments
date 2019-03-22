package Project2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CMinusParserDemoAcceptanceTests
{
    @Test
    void testRun1()
    {
        String result = CMinusParserDemo.run("/home/matthew/Documents/Git/GitHub/cop-4620-assignments/Test Files/parse_a_t1.c", true);

        assertEquals(result,"ACCEPT");
    }

    @Test
    void testRun2()
    {
        String result = CMinusParserDemo.run("/home/matthew/Documents/Git/GitHub/cop-4620-assignments/Test Files/parse_a_t2.c", true);

        assertEquals(result,"ACCEPT");
    }

    @Test
    void testRun3()
    {
        String result = CMinusParserDemo.run("/home/matthew/Documents/Git/GitHub/cop-4620-assignments/Test Files/parse_a_t3.c", true);

        assertEquals(result,"ACCEPT");
    }

    @Test
    void testRun4()
    {
        String result = CMinusParserDemo.run("/home/matthew/Documents/Git/GitHub/cop-4620-assignments/Test Files/parse_a_t4.c", true);

        assertEquals(result,"ACCEPT");
    }

    @Test
    void testRun5()
    {
        String result = CMinusParserDemo.run("/home/matthew/Documents/Git/GitHub/cop-4620-assignments/Test Files/parse_a_t5.c", true);

        assertEquals(result,"ACCEPT");
    }

    @Test
    void testRun6()
    {
        String result = CMinusParserDemo.run("/home/matthew/Documents/Git/GitHub/cop-4620-assignments/Test Files/parse_a_t6.c", true);

        assertEquals(result,"ACCEPT");
    }
}
