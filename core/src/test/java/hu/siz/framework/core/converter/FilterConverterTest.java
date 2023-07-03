package hu.siz.framework.core.converter;

import hu.siz.framework.root.model.Filter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterConverterTest {

    private static final Filter CASE_INSENSITIVE_EQUAL =
            new Filter("test", Filter.MatchStyle.EQUAL, false, new String[]{"foo"});
    private static final Filter CASE_SENSITIVE_EQUAL =
            new Filter("test", Filter.MatchStyle.EQUAL, true, new String[]{"bAr"});
    private static final Filter IN =
            new Filter("test", Filter.MatchStyle.IN, false, new String[]{"foo", "bar"});
    private static final Filter[] TEST_FILTERS =
            new Filter[]{CASE_INSENSITIVE_EQUAL, CASE_SENSITIVE_EQUAL, IN};
    private static final String TEST_STRING = "test=foo:test=!bAr:test=in.foo;bar";

    @Test
    void testConvert() {
        var filters = new FilterConverter().convert(TEST_STRING);
        assertNotNull(filters);
        assertArrayEquals(TEST_FILTERS, filters.toArray());
    }

    @Test
    void testEmptyFilter() {
        var filters = new FilterConverter().convert("");
        assertNotNull(filters);
        assertEquals(0, filters.size());
    }

    @Test
    void testInvalidMatchStyle() {
        var filters = new FilterConverter().convert("test=bar.foo");
        assertNotNull(filters);
        assertArrayEquals(new Filter[]{CASE_INSENSITIVE_EQUAL}, filters.toArray());
    }
}