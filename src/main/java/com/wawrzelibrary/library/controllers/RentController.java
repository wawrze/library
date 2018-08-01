package com.wawrzelibrary.library.controllers;

import com.wawrzelibrary.library.domains.rents.RentDto;
import com.wawrzelibrary.library.exeptions.RentNotFoundException;
import com.wawrzelibrary.library.mappers.RentMapper;
import com.wawrzelibrary.library.services.RentDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/rents")
@CrossOrigin(origins = "*")
public class RentController {

    @Autowired
    private RentDbService service;

    @Autowired
    private RentMapper rentMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getRents")
    public List<RentDto> getRents() {
        return rentMapper.mapToRentDtoList(service.getAllRents());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRent")
    public RentDto getRent(@RequestParam Integer rentId) throws RentNotFoundException {
        return rentMapper.mapToRentDto(service.getRent(rentId).orElseThrow(RentNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateRent")
    public RentDto updateRent(@RequestBody RentDto rentDto) {
        return rentMapper.mapToRentDto(service.saveRent(rentMapper.mapToRent(rentDto)));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createRent",
            consumes = APPLICATION_JSON_VALUE
    )
    public void createRent(@RequestBody RentDto rentDto) {
        service.saveRent(rentMapper.mapToRent(rentDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteRent")
    public void deleteRent(@RequestParam Integer rentId) {
        service.deleteRent(rentId);
    }

}