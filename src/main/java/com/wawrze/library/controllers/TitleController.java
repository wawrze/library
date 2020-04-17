package com.wawrze.library.controllers;

import com.wawrze.library.domains.titles.TitleDto;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.TitleNotFoundException;
import com.wawrze.library.mappers.TitleMapper;
import com.wawrze.library.services.TitleDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.wawrze.library.filters.AuthFilter.USER_ROLE_KEY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/title")
@CrossOrigin(origins = "*")
public class TitleController {

    private final TitleDbService service;
    private final TitleMapper titleMapper;

    @Autowired
    public TitleController(TitleDbService service, TitleMapper titleMapper) {
        this.service = service;
        this.titleMapper = titleMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTitles")
    public List<TitleDto> getTitles() {
        return titleMapper.mapToTitleDtoList(service.getAllTitles());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTitle")
    public TitleDto getTitle(@RequestParam Integer titleId) throws TitleNotFoundException {
        return service.getTitle(titleId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "updateTitle")
    public TitleDto updateTitle(@RequestBody TitleDto titleDto, HttpServletRequest request) {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        return titleMapper.mapToTitleDto(service.saveTitle(titleMapper.mapToTitle(titleDto), userRole));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createTitle",
            consumes = APPLICATION_JSON_VALUE
    )
    public void createTitle(@RequestBody TitleDto titleDto, HttpServletRequest request) {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        service.saveTitle(titleMapper.mapToTitle(titleDto), userRole);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTitle")
    public void deleteTitle(@RequestParam Integer titleId, HttpServletRequest request) {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        service.deleteTitle(titleId, userRole);
    }

}