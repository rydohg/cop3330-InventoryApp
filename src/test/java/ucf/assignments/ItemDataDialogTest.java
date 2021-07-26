package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemDataDialogTest {

    @Test
    void checkValidSerial() {
        ItemDataDialog testClass = new ItemDataDialog();
        assertTrue(testClass.checkValidSerial("XXXXXXXXXX"));
        assertTrue(testClass.checkValidSerial("X1XXX12XXX"));
        assertFalse(testClass.checkValidSerial("X1XXX12XX"));
        assertFalse(testClass.checkValidSerial("X1XXX12XX/"));
        assertFalse(testClass.checkValidSerial("asdf"));
    }
}