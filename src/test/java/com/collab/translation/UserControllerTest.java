package com.collab.translation;

import com.collab.domain.UserService;
import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.models.NewUserInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService service;

    @Value("${name.max.length}")
    private Integer maxNameLength;

    @Test
    void shouldNotAllowANameLongerThanAllowed() throws Exception {
        String tooLongOfAName = String.join("", Collections.nCopies(maxNameLength + 1, "x"));
        NewUserInput newUser = NewUserInput.builder().name(tooLongOfAName).build();
        CurrentUser currentUser = new CurrentUser("some id", tooLongOfAName);
        when(service.save(any(NewUser.class))).thenReturn(currentUser);

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsBytes(newUser))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(service, never()).save(any(NewUser.class));
    }
}
