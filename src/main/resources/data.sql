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