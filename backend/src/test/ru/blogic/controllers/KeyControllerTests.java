package ru.blogic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.interfaces.KeyGenerator;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
public class KeyControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private KeyGenerator<KeyMetaDTO, KeyFileDTO> dummyKeyService;

    @Test
    public void getAllKeys() throws Exception {
        KeyMetaDTO keyMetaDTO1 = new KeyMetaDTO();
        keyMetaDTO1.setOrganization("name");
        keyMetaDTO1.setExpiration(new Date());
        keyMetaDTO1.setCoresCount(4);
        keyMetaDTO1.setUsersCount(4);
        keyMetaDTO1.setModuleFlags(240);
        keyMetaDTO1.setKeyFileName("keyFileName");
        keyMetaDTO1.setComment("comment");

//        when(dummyKeyService.findAllByType(any())).thenReturn(Collections.singletonList(keyMetaDTO1)); //TODO: переделать

        mockMvc.perform(get("/api/key"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(keyMetaDTO1))));
    }

    @Test
    public void downloadKeyFile_Success() throws Exception {
        KeyMetaDTO keyMetaDTO = new KeyMetaDTO();
        keyMetaDTO.setOrganization("name");
        keyMetaDTO.setExpiration(new Date());
        keyMetaDTO.setCoresCount(4);
        keyMetaDTO.setUsersCount(4);
        keyMetaDTO.setModuleFlags(240);
        keyMetaDTO.setKeyFileName("keyFileName");
        keyMetaDTO.setComment("comment");

        KeyFileDTO keyFileDTO = new KeyFileDTO(
                "fileName",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                keyMetaDTO,
                new byte[100]
        );
        keyFileDTO.setId(1L);

        when(dummyKeyService.getKeyFile(any(Long.class))).thenReturn(keyFileDTO);

        mockMvc.perform(get("/api/key/download/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
                .andExpect(header().exists(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS))
                .andExpect(content().bytes(keyFileDTO.getData()));
    }

    @Test
    public void downloadKeyFile_NotFound() throws Exception {
        when(dummyKeyService.getKeyFile(any(Long.class))).thenThrow(FileNotFoundException.class);

        mockMvc.perform(get("/api/key/download/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Такого ключа нет"));
    }

    @Test
    public void createNewKey_Success() throws Exception {
        mockMvc.perform(post("/api/key/create", new KeyMetaDTO()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Новый ключ создан"));
    }

    @Test
    public void createNewKey_IOException() throws Exception {
//        doThrow(IOException.class).when(dummyKeyService).generate(any());

        mockMvc.perform(post("/api/key/create", new KeyMetaDTO()))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ошибка при генерации ключа"));
    }
}
