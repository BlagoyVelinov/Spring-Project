use `cinema_tickets_27_july_2024`;

INSERT INTO `users`(`birthdate`,`created`, `email`, `image_url`, `is_active`, `modified`, `name`, `password`, `username`)
VALUES
    ('1990-06-06', '2024-05-19 12:09:30.367508', 'admin@gmail.com', null, 0, null, 'Admin Adminov', '$2a$10$9cgWfwwTx.fUSNnFc0tkbOERNJVAVloDgi/DIF6JwPSDD4YfY1PVy', 'admin'),
    ('1983-07-07', '2024-06-11 20:09:30.367508', 'user@gmail.com', null, 0, null, 'User userov', '$2a$10$9cgWfwwTx.fUSNnFc0tkbOERNJVAVloDgi/DIF6JwPSDD4YfY1PVy', 'useruser');

INSERT INTO `users_roles`(`user_id`, `role_id`)
VALUES
    (1, 1),
    (1, 2),
    (2, 1);

INSERT INTO `movies`(`audio`, `description`, `hall_number`, `image_url`, `movie_length`, `name`, `projection_format`, `subtitles`, `trailer_url`, `movie_class_id`)
    VALUES
        ('Engl.', 'Two years after her move to San Francisco, 13-year-old Riley Andersen is about to enter high school...', 'HALL_5', 'https://m.media-amazon.com/images/M/MV5BYTc1MDQ3NjAtOWEzMi00YzE1LWI2OWUtNjQ0OWJkMzI3MDhmXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_.jpg', 120, 'Inside Out 2', 'D_3D', 'Buld.', 'https://www.youtube.com/embed/LEjhY15eCx0&t=1s', 1),
        ('Engl.', 'Four years after the death of Isabel Aretas, MPD Detective Mike Lowrey marries his physical therapist, Christine...', 'HALL_6', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRi9kID45MZjKWQgYyE7mSXHRYc4nRC9cnSV09wM9g2i7SS3S0xP93ZQMnsbkSgca9sA_A&usqp=CAU', 100, 'Bad Boys: Ride Or Die', 'D_3D', 'Bulg.', 'https://www.youtube.com/embed/hRFY_Fesa9Q&t=7s', 4),
        ('Engl.', 'Alien-Romulus: takes the phenomenally successful Alien franchise back to its roots: While scavenging the deep ends of a derelict space station...', 'HALL_1', 'https://posterspy.com/wp-content/uploads/2024/04/AlienRomulus_Poster02-nightshade-intensity-LOW-V1.jpg', 140, 'Alien: Romulus', 'D_4DX', 'Bulg.', 'https://www.youtube.com/embed/ruCbkPugw34', 5),
        ('Bulg.Engl.', 'Всичко в живота на любимия на малки и големи суперзлодей Гру, превърнал се в агент на Антизлодейската лига, изглежда повече от перфектно... ', 'HALL_3', 'https://www.cinemacity.bg/xmedia-cw/repo/feats/posters/6361D3R.jpg', 95, 'Despicable Me 4', 'D_3D', 'Bulg.', 'https://www.youtube.com/embed/qQlr9-rF32A', 1),
        ('Engl.', 'dvddfsfsawefaczxc', 'HALL_2', 'https://m.media-amazon.com/images/M/MV5BZmYwY2EyZmItYWViZi00YTFhLWE3NGEtMDE5ODJmMTQ2ZWRhXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_.jpg', 120, 'Trap 2024', 'D_4DX', 'Bulg.', '', 5);

INSERT INTO `movies__booking_times`(`movie_id`, `start_time_id`)
VALUES
    (4, 4),
    (4, 5),
    (5, 9),
    (5, 10);
