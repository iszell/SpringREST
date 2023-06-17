package hu.siz.framework.core.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderConverterTest {

    private static final String FIELD_NAME = "field";
    private final OrderConverter converter = new OrderConverter();

    @Test
    void testAscending() {
        var order = converter.convert(FIELD_NAME);
        assertNotNull(order);
        assertEquals(FIELD_NAME, order.field());
        assertFalse(order.descending());
    }

    @Test
    void testDescending() {
        var order = converter.convert('-' + FIELD_NAME);
        assertNotNull(order);
        assertEquals(FIELD_NAME, order.field());
        assertTrue(order.descending());
    }
}