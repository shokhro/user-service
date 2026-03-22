package simulation;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CanculatorTest {

    @Test
    public void testAddition() {
        //---------------Arrange-----------------
        Canculator canculator = new Canculator();
        int a = 5;
        int b = 4;

        //---------------Act---------------------
        int result = canculator.add(a, b);

        //--------------Assert-------------------
        Assertions.assertEquals(9, result, "summa 5+4 bo'lsihi kerak 9");
    }
}
