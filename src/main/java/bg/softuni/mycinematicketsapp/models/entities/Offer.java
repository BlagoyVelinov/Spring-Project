package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.OfferType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false)
    @Size(min = 5, max = 2000)
    private String description;
    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_category")
    private OfferType offerCategory;

    public String getTitle() {
        return title;
    }

    public Offer setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Offer setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Offer setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public OfferType getOfferCategory() {
        return offerCategory;
    }

    public Offer setOfferCategory(OfferType offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }
}
