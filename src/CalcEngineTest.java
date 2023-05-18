import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalcEngineTest {

    private CalcEngine calcEngine;

    @BeforeEach
    public void setUp() {
        calcEngine = new CalcEngine();
    }

    @Test
    public void testGetDisplayValue() {
        assertEquals(0, calcEngine.getDisplayValue());
    }

    @Test
    public void testNumberPressedDecimalMode() {
        calcEngine.numberPressed(1);
        calcEngine.numberPressed(2);
        calcEngine.numberPressed(3);
        assertEquals(123, calcEngine.getDisplayValue());
    }

    @Test
    public void testNumberPressedHexadecimalMode() {
        calcEngine.switchMode(); // Enable hexadecimal mode
        calcEngine.numberPressed(1);
        calcEngine.numberPressed(10); // 'A' in hexadecimal
        calcEngine.numberPressed(11); // 'B' in hexadecimal
        assertEquals(0x1AB, calcEngine.getDisplayValue());
    }

    @Test
    public void testEqualsValidCalculation() {
        calcEngine.numberPressed(5); // Set left operand
        calcEngine.applyOperator('+'); // Set operator
        calcEngine.numberPressed(3); // Set right operand
        calcEngine.equals(); // Calculate the result
        assertEquals(8, calcEngine.getDisplayValue());
    }

    @Test
    public void testEqualsIncompleteCalculation() {
        calcEngine.equals(); // Calculate without setting operands and operator
        assertEquals(0, calcEngine.getDisplayValue());
    }

    @Test
    public void testApplyOperatorWithoutNewOperand() {
        calcEngine.numberPressed(5); // Set operand
        calcEngine.applyOperator('+'); // Apply operator without a new operand
        assertEquals(0, calcEngine.getDisplayValue());
    }

    @Test
    public void testClear() {
        calcEngine.numberPressed(5); // Set a non-zero display value
        calcEngine.clear();
        assertEquals(0, calcEngine.getDisplayValue());
    }
}
