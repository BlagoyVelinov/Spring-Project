package bg.softuni.mycinematicketsapp.services.impl;

//import bg.softuni.mycinematicketsapp.models.entities.Category;
//import bg.softuni.mycinematicketsapp.models.entities.UserRole;
//import bg.softuni.mycinematicketsapp.models.enums.Genre;
//import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
//import bg.softuni.mycinematicketsapp.repository.CategoryRepository;
//import bg.softuni.mycinematicketsapp.services.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;

//import java.util.Arrays;
//import java.util.List;

//@Service
//public class CategoryServiceImpl implements CategoryService {
//    private final CategoryRepository categoryRepository;
//    @Autowired
//    public CategoryServiceImpl(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }
//
//    @Override
//    public void initCategoryInDb() {
//        if (this.categoryRepository.count() == 0) {
//            List<Category> roles = Arrays.stream(Genre.values())
//                    .map(Category::new)
//                    .toList();
//
//            this.categoryRepository.saveAll(roles);
//        }
//    }
//
//    @Override
//    public List<Category> getCategoryByGenre(List<Genre> genreList) {
//        return this.categoryRepository.findAllByNameIn(genreList);
//    }
//}
