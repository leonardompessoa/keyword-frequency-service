package com.labfolder.controllers;


import com.labfolder.business.KeywordAnalyzer;
import com.labfolder.domain.KeywordFrequency;
import com.labfolder.exceptions.NoteAnalizerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/NoteKeyword")
@CrossOrigin("*")
public class NoteKeywordController {

    @Autowired
    private KeywordAnalyzer keywordAnalyzer;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody KeywordFrequency analyzeNoteEntry(@RequestParam("entry") MultipartFile entry, @RequestParam("keyword") String keyword) throws IOException {

        if(entry.isEmpty() || keyword.isEmpty()) {
            throw new NoteAnalizerException();
        }

        KeywordFrequency frequency = keywordAnalyzer.analyze(entry.getInputStream(), keyword);

        return frequency;
    }


    @ExceptionHandler({Exception.class})
    public void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }


}
