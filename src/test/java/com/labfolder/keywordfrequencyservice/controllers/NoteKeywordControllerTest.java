package com.labfolder.keywordfrequencyservice.controllers;

import com.labfolder.business.KeywordAnalyzer;
import com.labfolder.controllers.NoteKeywordController;
import com.labfolder.domain.KeywordFrequency;
import com.labfolder.exceptions.NoteAnalizerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NoteKeywordController.class, secure = false)
public class NoteKeywordControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeywordAnalyzer keywordAnalyzer;

    @Test
    public void receiveLongEntry() throws Exception {

        Mockito.when(keywordAnalyzer.analyze(Mockito.any(), Mockito.anyString())).thenReturn(new KeywordFrequency(5, new String[]{}));

        Resource resource = new ClassPathResource("longInput.txt");
        MockMultipartFile entry = new MockMultipartFile("entry", "blob", "text/plain", resource.getInputStream());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload("/NoteKeyword")
                .file(entry).param("keyword", "lorem");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String content = response.getContentAsString();
        assertEquals("{\"frequency\":5,\"similarWords\":[]}", content);
    }

    @Test
    public void receiveEmptyEntry() throws Exception {

        Mockito.when(keywordAnalyzer.analyze(Mockito.any(), Mockito.anyString())).thenReturn(new KeywordFrequency(5, new String[]{}));

        MockMultipartFile keyword = new MockMultipartFile("keyword", "keyword", "text/plain", "lorem".getBytes());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload("/NoteKeyword")
                .param("keyword", "lorem");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void receiveEmptyKeyword() throws Exception {

        Mockito.when(keywordAnalyzer.analyze(Mockito.any(), Mockito.anyString())).thenReturn(new KeywordFrequency(5, new String[]{}));

        Resource resource = new ClassPathResource("longInput.txt");
        MockMultipartFile entry = new MockMultipartFile("entry", "blob", "text/plain", resource.getInputStream());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload("/NoteKeyword").file(entry)
                .param("keyword", "");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

}
