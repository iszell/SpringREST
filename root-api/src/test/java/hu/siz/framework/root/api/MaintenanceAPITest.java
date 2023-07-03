package hu.siz.framework.root.api;


import hu.siz.framework.root.exception.UnsupportedOperationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MaintenanceAPITest {

    private static final MaintenanceAPI<Object, String> API = new MaintenanceAPI<>() {
    };
    private static final String ID = "id";
    private static final Object TEST_OBJECT = new Object();

    @Test
    void create() {
        assertThrows(UnsupportedOperationException.class,
                () -> API.create(TEST_OBJECT));
    }

    @Test
    void get() {
        assertThrows(UnsupportedOperationException.class,
                () -> API.get(ID));
    }

    @Test
    void delete() {
        assertThrows(UnsupportedOperationException.class,
                () -> API.delete(ID));
    }

    @Test
    void patch() {
        assertThrows(UnsupportedOperationException.class,
                () -> API.patch(ID, TEST_OBJECT));
    }

    @Test
    void search() {
        assertThrows(UnsupportedOperationException.class,
                () -> API.search(null, 0, 20, null));
    }

    @Test
    void update() {
        assertThrows(UnsupportedOperationException.class,
                () -> API.update(ID, TEST_OBJECT));
    }
}