package hu.siz.framework.core.converter;

import hu.siz.framework.root.model.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert URL parameter to Order object
 *
 * @author siz
 */
@Component
public class OrderConverter implements Converter<String, Order> {
    @Override
    public Order convert(String source) {
        return source.charAt(0) == '-' ?
                new Order(source.substring(1), true) :
                new Order(source, false);
    }
}
