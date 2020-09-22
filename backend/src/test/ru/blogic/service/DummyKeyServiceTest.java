package ru.blogic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.entity.KeyFile;
import ru.blogic.entity.KeyMeta;
import ru.blogic.repository.KeyFileRepository;
import ru.blogic.repository.KeyRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {})
class DummyKeyServiceTest {

    @Mock
    Environment env;

    @Mock
    KeyRepository keyRepository;

    @Mock
    KeyFileRepository keyFileRepository;

    @Mock
    FileStorageService fileStorageService;

    @InjectMocks
    DummyKeyService dummyKeyService;

    @Captor
    ArgumentCaptor<KeyFile> keyFileArgumentCaptor;

    @BeforeEach
    public void init() {
        when(env.getProperty("LICENSES_PATH")).thenReturn("F:\\workplace\\java\\dummy-generator\\target\\dummy-generator-1.0.jar");
    }

    @Test
    void findAll() {
        KeyMeta keyMeta1 = new KeyMeta();
        keyMeta1.setId(1L);
        keyMeta1.setOrganization("name");
        keyMeta1.setExpiration(new Date());
        keyMeta1.setCoresCount(4);
        keyMeta1.setUsersCount(4);
        keyMeta1.setModuleFlags(240);
        keyMeta1.setKeyFileName("keyFileName");
        keyMeta1.setComment("comment");
        KeyMeta keyMeta2 = new KeyMeta();
        keyMeta2.setId(2L);
        keyMeta2.setOrganization("name");
        keyMeta2.setExpiration(new Date());
        keyMeta2.setCoresCount(4);
        keyMeta2.setUsersCount(4);
        keyMeta2.setModuleFlags(240);
        keyMeta2.setKeyFileName("keyFileName");
        keyMeta2.setComment("comment");

        Iterable<KeyMetaDTO> expectList = Stream.of(keyMeta1, keyMeta2)
                .map(KeyMetaDTO::new)
                .collect(Collectors.toList());

        when(keyRepository.findAll()).thenReturn(Arrays.asList(keyMeta1, keyMeta2));
//        Iterable<KeyMetaDTO> resultList = dummyKeyService.findAllByType(new Unpaged());

        assertEquals(null, expectList); //TODO: переделать
    }

    @Test
    void getKeyFile_Success() throws FileNotFoundException {
        KeyMeta keyMeta = new KeyMeta();
        keyMeta.setId(1L);
        keyMeta.setOrganization("name");
        keyMeta.setExpiration(new Date());
        keyMeta.setCoresCount(4);
        keyMeta.setUsersCount(4);
        keyMeta.setModuleFlags(240);
        keyMeta.setKeyFileName("keyFileName");
        keyMeta.setComment("comment");

        KeyFile keyFile = new KeyFile(
                "fileName",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                keyMeta,
                new byte[100]
        );
        keyFile.setId(1L);

        when(keyFileRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(keyFile));

        KeyFileDTO result = dummyKeyService.getKeyFile(1L);
        assertEquals(result, new KeyFileDTO(keyFile));
    }

    @Test
    void getKeyFile_FileNotFoundException() {
        when(keyFileRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> dummyKeyService.getKeyFile(2L));
    }

    @Test
    void generate_WithoutKey() throws IOException {
        KeyMetaDTO keyMetaDTO = new KeyMetaDTO();
        keyMetaDTO.setOrganization("name");
        keyMetaDTO.setExpiration(new Date());
        keyMetaDTO.setCoresCount(4);
        keyMetaDTO.setUsersCount(4);
        keyMetaDTO.setModuleFlags(240);
        keyMetaDTO.setKeyFileName("keyFileName");
        keyMetaDTO.setComment("comment");


        dummyKeyService.generate(keyMetaDTO, null);

        verify(fileStorageService, times(0)).save(any());
        verify(fileStorageService, times(1)).deleteAll();

        assertTrue(Files.exists(Paths.get(System.getProperty("java.io.tmpdir") + keyMetaDTO.getKeyFileName())));
    }
}