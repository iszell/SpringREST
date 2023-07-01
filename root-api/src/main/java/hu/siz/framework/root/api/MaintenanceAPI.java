package hu.siz.framework.root.api;

import hu.siz.framework.root.exception.UnsupportedOperationException;
import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.IdentifierWrapper;
import hu.siz.framework.root.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Generic maintenance API definition with default implementations returning 501: Not implemented
 *
 * @param <I> the identifier type of the maintained object (DTO)
 * @param <T> the actual DTO type of the maintained object
 * @author siz
 */
public interface MaintenanceAPI<T, I> {
    /**
     * Create object
     *
     * @param dto the source to create the object from
     * @return an {@link IdentifierWrapper} containing the id of the created object
     */
    @PutMapping(value = "", consumes = MediaTypes.HAL_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(description = "Create object")
    @ResponseStatus(HttpStatus.CREATED)
    default IdentifierWrapper<I> create(@Valid @RequestBody T dto) {
        throw new UnsupportedOperationException("create operation is not supported by this API");
    }

    /**
     * Retrieve an object by its identifier
     *
     * @param id the identifier of the object
     * @return the object
     */
    @GetMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(description = "Get an object by its identifier")
    default T get(@PathVariable I id) {
        throw new
                UnsupportedOperationException("get operation is not supported by this API");
    }

    /**
     * Delete the object with the specified identifier
     *
     * @param id the identifier of the object to be deleted
     */
    @DeleteMapping("{id}")
    @Operation(description = "Delete object")
    default void delete(@PathVariable I id) {
        throw new UnsupportedOperationException("delete operation is not supported by this API");
    }

    /**
     * Partial update of an object with an identifier
     *
     * @param id  the identifier of the object
     * @param dto the partially populated object
     */
    @PatchMapping(value = "{id}", consumes = MediaTypes.HAL_JSON_VALUE)
    @Operation(description = "Patch (partially update) object")
    default void patch(@PathVariable I id, @Valid @RequestBody T dto) {
        throw new UnsupportedOperationException("patch operation is not supported by this API");
    }

    /**
     * Paged search for objects by search criteria and ordering
     *
     * @param filter an array of filter lists.
     *               Relation between the list elements is logical AND.
     *               Relation between lists is logical OR
     * @param page   the page of data to be retrieved
     * @param size   the number of elements on a page
     * @param order  an array of order fields
     * @return a {@link PagedModel} of objects
     */
    @GetMapping(value = "search", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(description = "Search objects")
    default PagedModel<EntityModel<T>> search(@RequestParam(value = "filter", required = false)
                                              @Schema(description = "Query criteria specified in the form " +
                                                      "fieldname1=[!]matchstyle.value1;value2:fieldname2=... " +
                                                      "The fields in a criteria are in logical AND relation." +
                                                      "The optional exclamation mark [!] means case sensitive search. " +
                                                      "For match styles that supports multiple values they can be " +
                                                      "specified as a semicolon separated list. " +
                                                      "Default match style is EQUAL. " +
                                                      "Multiple criteria are separated by semicolons. " +
                                                      "You can specify this parameter more than once. The relation " +
                                                      "between those will be logical OR.",
                                                      example = "firstName=in.john;jane" +
                                                              ":lastName=!Doe" +
                                                              ":email=like.@gmail.com")
                                              List<Filter>[] filter,
                                              @RequestParam(value = "page", required = false, defaultValue = "0")
                                              @Schema(description = "Requested page", defaultValue = "0")
                                              int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "20")
                                              @Schema(description = "Page size", defaultValue = "20")
                                              int size,
                                              @RequestParam(value = "order", required = false)
                                              @Schema(description = "Order fields. Can have more than one in a query." +
                                                      " Field name prefixed with a minus sign (-) means " +
                                                      "descending order", example = "-level,lastName,firstName")
                                              Order[] order) {
        throw new UnsupportedOperationException("search operation is not supported by this API");
    }

    /**
     * Update (fully overwrite) an object
     *
     * @param id  the identifier of the object
     * @param dto the object
     * @return nothing
     */
    @PostMapping("{id}")
    @Operation
    default ResponseEntity<Void> update(@PathVariable I id, @Valid @RequestBody T dto) {
        throw new UnsupportedOperationException("update operation is not supported by this API");
    }
}
