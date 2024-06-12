package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.entities.Category;
import bg.softuni.mycinematicketsapp.models.enums.Genre;

public interface CategoryService {

    void initCategoryInDb();

    Category getCategoryByGenre(Genre name);
}
