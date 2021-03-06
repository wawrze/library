package com.wawrzelibrary.library.controllers;

import com.wawrzelibrary.library.domains.titles.TitleDto;
import com.wawrzelibrary.library.exeptions.TitleNotFoundException;
import com.wawrzelibrary.library.mappers.TitleMapper;
import com.wawrzelibrary.library.services.TitleDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/title")
@CrossOrigin(origins = "*")
public class TitleController {

    @Autowired
    private TitleDbService service;

    @Autowired
    private TitleMapper titleMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getTitles")
    public List<TitleDto> getTitles() {
        return titleMapper.mapToTitleDtoList(service.getAllTitles());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTitle")
    public TitleDto getTitle(@RequestParam Integer titleId) throws TitleNotFoundException {
        return titleMapper.mapToTitleDto(service.getTitle(titleId).orElseThrow(TitleNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTitle")
    public TitleDto updateTitle(@RequestBody TitleDto titleDto) {
        return titleMapper.mapToTitleDto(service.saveTitle(titleMapper.mapToTitle(titleDto)));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createTitle",
            consumes = APPLICATION_JSON_VALUE
    )
    public void createTitle(@RequestBody TitleDto titleDto) {
        service.saveTitle(titleMapper.mapToTitle(titleDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTitle")
    public void deleteTitle(@RequestParam Integer titleId) {
        service.deleteTitle(titleId);
    }

}