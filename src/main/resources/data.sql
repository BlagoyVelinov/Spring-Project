use `cinema_tickets_11_august_2024`;

INSERT INTO `users`(`birthdate`,`created`, `email`, `image_url`, `is_active`, `modified`, `name`, `password`, `username`)
VALUES
    ('1990-06-06', '2024-05-19 12:09:30.367508', 'admin@gmail.com', null, 0, null, 'Admin Adminov', '$2a$10$9cgWfwwTx.fUSNnFc0tkbOERNJVAVloDgi/DIF6JwPSDD4YfY1PVy', 'admin'),
    ('1983-07-07', '2024-06-11 20:09:30.367508', 'user@gmail.com', null, 0, null, 'User userov', '$2a$10$9cgWfwwTx.fUSNnFc0tkbOERNJVAVloDgi/DIF6JwPSDD4YfY1PVy', 'useruser');

INSERT INTO `users_roles`(`user_id`, `role_id`)
VALUES
    (1, 1),
    (1, 2),
    (2, 1);

INSERT INTO `offers`(`description`, `image_url`, `offer_category`, `title`)
VALUES ('CINEMA TICKETS VOUCHERS: A PERFECT GIFT IDEA Vouchers can be used to purchase cinema tickets for a date and film of your choice for all our locations across the country. THE PERFECT SOLUTION IF YOU ARE LOOKING FOR: A gift for your customers / Reward to encourage your employees!',
        '/images/offer-img/vouchers-company-hero_BG.jpg', 'FOR_THE_BUSINESS', 'VOUCHERS FOR COMPANIES'),
        ('Group tickets are intended for organized groups of students and teenagers. The group offer covers a wide range of films that can fit into the school''s curriculum. The group can both select new films from our current repertoire and inquire about films that are no longer showing in the cinema.',
         '/images/offer-img/groups-hero.png', 'FOR_THE_SCHOOLS', 'TICKETS FOR SCHOOL GROUPS'),
        ('CINEMA LOVES CHILDREN AND CHILDREN LOVE CINEMA! There is nothing better than a birthday spent with friends from kindergarten and school among the stars of the big screen. Give your child an unforgettable birthday at the cinema!',
         '/images/offer-img/birthdays_at_the_cinema_tickets.jpg', 'CINEMA_OFFERS', 'BIRTHDAY OF CINEMA');


# The password of test Users is -> 123456789