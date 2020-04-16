package com.wawrze.library.controllers;

import com.wawrze.library.domains.rents.RentDto;
import com.wawrze.library.exeptions.RentNotFoundException;
import com.wawrze.library.mappers.RentMapper;
import com.wawrze.library.services.RentDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.wawrze.library.filters.AuthFilter.IS_ADMIN_KEY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/rents")
@CrossOrigin(origins = "*")
public class RentController {

    private final RentDbService service;
    private final RentMapper rentMapper;

    @Autowired
    public RentController(RentDbService service, RentMapper rentMapper) {
        this.service = service;
        this.rentMapper = rentMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRents")
    public List<RentDto> getRents(HttpServletRequest request) {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        return rentMapper.mapToRentDtoList(service.getAllRents(isAdmin));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRent")
    public RentDto getRent(@RequestParam Integer rentId, HttpServletRequest request) throws RentNotFoundException {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        return rentMapper.mapToRentDto(service.getRent(rentId, isAdmin).orElseThrow(RentNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateRent")
    public RentDto updateRent(@RequestBody RentDto rentDto, HttpServletRequest request) {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        return rentMapper.mapToRentDto(service.saveRent(rentMapper.mapToRent(rentDto), isAdmin));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createRent",
            consumes = APPLICATION_JSON_VALUE
    )
    public void createRent(@RequestBody RentDto rentDto, HttpServletRequest request) {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        service.saveRent(rentMapper.mapToRent(rentDto), isAdmin);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteRent")
    public void deleteRent(@RequestParam Integer rentId, HttpServletRequest request) {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        service.deleteRent(rentId, isAdmin);
    }

}