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
        ('Angl.', 'Plot. Two years after moving to San Francisco, 13-year-old Riley is about to enter high school. Her personified emotions — Joy, Sadness, Fear, Anger, and Disgust — now oversee a newly-formed element of Rileys mind called her Sense of Self, which houses memories and feelings that shape her beliefs.', 'HALL_5', 'https://m.media-amazon.com/images/M/MV5BYTc1MDQ3NjAtOWEzMi00YzE1LWI2OWUtNjQ0OWJkMzI3MDhmXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_.jpg', 120, 'Inside Out 2', 'D_3D', 'Buld.', 'https://www.youtube.com/embed/LEjhY15eCx0&t=1s', 1),
        ('Bulg.', 'bj,bhjv,hbnb,jb', 'HALL_3', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNSiI6kh83RIFJGFNjUIDHBudEIDCIyCqCkQ&s', 110, 'test1', 'D_3D', 'Engl.', 'https://www.youtube.com/embed/iH5MDG3LqeA', 3),
        ('Angl', 'dvddfsfsawefaczxc', 'HALL_2', 'https://m.media-amazon.com/images/M/MV5BZmYwY2EyZmItYWViZi00YTFhLWE3NGEtMDE5ODJmMTQ2ZWRhXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_.jpg', 120, 'Trap 2024', 'D_4DX', 'Buld.', 'https://www.youtube.com/embed/ruCbkPugw34', 5);

INSERT INTO `movies__booking_times`(`movie_id`, `start_time_id`)
VALUES
    (1, 4),
    (1, 5),
    (3, 9),
    (3, 10);
