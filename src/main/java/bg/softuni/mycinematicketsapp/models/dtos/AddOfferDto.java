package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.enums.OfferType;
import bg.softuni.mycinematicketsapp.validation.annotation.UniqueOfferName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddOfferDto {

    @NotEmpty
    @UniqueOfferName(message = "Offer with this name already exist!")
    @Size(min = 5, max = 100, message = "Offer title length must be between 5 and 20 characters!")
    private String title;
    @NotEmpty
    @Size(min = 5, max = 2000, message = "Description length must be between 5 and 2000 characters!")
    private String description;
    @Size(min = 5, max = 200, message = "The URL length should be between 5 and 200 symbols!")
    private String imageUrl;
    @NotNull(message = "Please select an offer category!")
    private OfferType offerCategory;

    public String getTitle() {
        return title;
    }

    public AddOfferDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddOfferDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AddOfferDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public OfferType getOfferCategory() {
        return offerCategory;
    }

    public AddOfferDto setOfferCategory(OfferType offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }
}
