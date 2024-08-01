package bg.softuni.mycinematicketsapp.services.impl;

//import bg.softuni.mycinematicketsapp.models.entities.MovieClass;
//import bg.softuni.mycinematicketsapp.models.enums.MovieClassEnum;
//import bg.softuni.mycinematicketsapp.repository.MovieClassRepository;
//import bg.softuni.mycinematicketsapp.services.MovieClassService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;

//@Service
//public class MovieClassServiceImpl implements MovieClassService {
//    private final MovieClassRepository movieClassRepository;
//
//    @Autowired
//    public MovieClassServiceImpl(MovieClassRepository movieClassRepository) {
//        this.movieClassRepository = movieClassRepository;
//    }
//
//    @Override
//    public void initMovieClassesInDb() {
//        if (this.movieClassRepository.count() == 0) {
//            List<MovieClass> movieClassList = Arrays.stream(MovieClassEnum.values())
//                    .map(MovieClass::new)
//                    .toList();
//
//            this.movieClassRepository.saveAll(movieClassList);
//        }
//    }
//
//    @Override
//    public MovieClass getMovieClassByName(MovieClassEnum name) {
//        return this.movieClassRepository.findByName(name);
//    }
//}
