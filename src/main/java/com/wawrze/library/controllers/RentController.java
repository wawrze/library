package com.wawrze.library.controllers;

import com.wawrze.library.domains.rents.RentDto;
import com.wawrze.library.domains.rents.SimplifiedRentDto;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.BookNotFoundException;
import com.wawrze.library.exeptions.RentNotFoundException;
import com.wawrze.library.exeptions.UserNotFoundException;
import com.wawrze.library.mappers.RentMapper;
import com.wawrze.library.services.RentDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.wawrze.library.filters.AuthFilter.USER_ID_KEY;
import static com.wawrze.library.filters.AuthFilter.USER_ROLE_KEY;

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
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        return rentMapper.mapToRentDtoList(service.getAllRents(userRole));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRents/{userId}")
    public List<SimplifiedRentDto> getRents(HttpServletRequest request, @PathVariable Integer userId) {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        int id = (int) request.getSession().getAttribute(USER_ID_KEY);
        return rentMapper.mapToSimplifiedRentDtoList(service.getUserRents(userId, id, userRole));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRent")
    public RentDto getRent(@RequestParam Integer rentId, HttpServletRequest request) throws RentNotFoundException {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        return rentMapper.mapToRentDto(service.getRent(rentId, userRole).orElseThrow(RentNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRentByBookId")
    public RentDto getRentByBookId(@RequestParam Integer bookId, HttpServletRequest request) throws RentNotFoundException {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        return rentMapper.mapToRentDto(service.getRentByBookId(bookId, userRole).orElseThrow(RentNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateRent")
    public RentDto updateRent(@RequestBody RentDto rentDto, HttpServletRequest request) {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        return rentMapper.mapToRentDto(service.updateRent(rentMapper.mapToRent(rentDto), userRole));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createRent")
    public void createRent(@RequestParam Integer userId, @RequestParam Integer bookId, HttpServletRequest request) throws BookNotFoundException, UserNotFoundException {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        service.createRent(userId, bookId, userRole);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteRent")
    public void deleteRent(@RequestParam Integer rentId, HttpServletRequest request) {
        UserRole userRole = (UserRole) request.getSession().getAttribute(USER_ROLE_KEY);
        service.deleteRent(rentId, userRole);
    }

}