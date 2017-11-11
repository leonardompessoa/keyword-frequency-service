package com.labfolder.controllers;


import com.labfolder.business.KeywordAnalyzer;
import com.labfolder.domain.KeywordFrequency;
import com.labfolder.exceptions.NoteAnalizerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/NoteKeyword")
@CrossOrigin("*")
public class NoteKeywordController {

    @Autowired
    private KeywordAnalyzer keywordAnalyzer;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody KeywordFrequency analyzeNoteEntry(@RequestParam("entry") MultipartFile entry, @RequestParam("keyword") String keyword) {

        if(entry.isEmpty() || keyword.isEmpty()) {
            throw new NoteAnalizerException();
        }

        KeywordFrequency frequency = null;
        try {
            frequency = keywordAnalyzer.analyze(entry.getInputStream(), keyword);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return frequency;
    }

}
