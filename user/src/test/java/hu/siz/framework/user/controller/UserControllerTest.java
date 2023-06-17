package hu.siz.framework.user.controller;

import hu.siz.framework.root.model.IdentifierWrapper;
import hu.siz.framework.user.model.UserDTO;
import hu.siz.framework.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final long PAGE = 0L;
    private static final long SIZE = 20L;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void create() {
        when(userService.create(any()))
                .thenReturn(new IdentifierWrapper<>(UUID.randomUUID()));
        var response = userController.create(new UserDTO());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void search() {
        when(userService.search(any(), eq(PAGE), eq(SIZE), any()))
                .thenReturn(Page.empty());
        var response = userController.search(null, PAGE, SIZE, null);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}