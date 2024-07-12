package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.ProgramViewDto;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.Program;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.repository.ProgramRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.ProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final CityService cityService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository,
                              CityService cityService, ModelMapper modelMapper) {
        this.programRepository = programRepository;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
    }
    @Override
    public void initProgramsToDb() {
        if (this.programRepository.count() == 0) {
            Set<City> allCities = this.cityService.getAllCities();
            Program program = new Program().setCities(allCities);
            this.programRepository.save(program);
        }
    }
}
