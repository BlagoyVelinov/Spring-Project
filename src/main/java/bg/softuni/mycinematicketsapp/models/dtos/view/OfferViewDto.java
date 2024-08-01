package bg.softuni.mycinematicketsapp.models.dtos.view;

import bg.softuni.mycinematicketsapp.models.enums.OfferType;

public class OfferViewDto {
    private long id;
    private String title;
    private String description;
    private String imageUrl;
    private OfferType offerCategory;

    public long getId() {
        return id;
    }

    public OfferViewDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OfferViewDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferViewDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferViewDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public OfferType getOfferCategory() {
        return offerCategory;
    }

    public OfferViewDto setOfferCategory(OfferType offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }
}
