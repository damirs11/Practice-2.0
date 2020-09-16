package ru.blogic.controllers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.blogic.dto.request.LoginRequest;
import ru.blogic.dto.request.RegistrationRequest;
import ru.blogic.dto.response.MessageResponse;
import ru.blogic.service.security.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void authenticateUser() throws Exception {
        when(authService.authenticateUser(any(), any())).thenReturn(new MessageResponse("Вход прошел успешно"));

        String result = objectMapper.writeValueAsString(new LoginRequest("un", "pwd"));

        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Вход прошел успешно"));

    }

    @Test
    void registerUser() throws Exception {
        when(authService.registerUser(any())).thenReturn(new MessageResponse("Регистрация прошла успешно"));

        String result = objectMapper.writeValueAsString(new RegistrationRequest("un", "pwd"));

        mockMvc.perform(post("/api/auth/registration").contentType(MediaType.APPLICATION_JSON).content(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Регистрация прошла успешно"));

    }

    @Test
    void currentUser() throws Exception {
        when(authService.currentUser()).thenReturn(SecurityContextHolder.getContext().getAuthentication());

        mockMvc.perform(get("/api/auth/currentUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }
}