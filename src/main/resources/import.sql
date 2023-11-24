-- Inserção de dados na tabela 'director'
INSERT INTO director (name) VALUES ('Director 1');
INSERT INTO director (name) VALUES ('Director 2');

-- Inserção de dados na tabela 'studio'
INSERT INTO studio (foundation, location, name) VALUES ('Foundation 1', 'Location 1', 'Studio 1');
INSERT INTO studio (foundation, location, name) VALUES ('Foundation 2', 'Location 2', 'Studio 2');

-- Inserção de dados na tabela 'animes'
INSERT INTO animes (description, image_url, title) VALUES ('Anime 1 Description', 'Image URL 1', 'naruto');
INSERT INTO animes (description, image_url, title) VALUES ('Anime 2 Description', 'Image URL 2', 'Anime 2');

-- Inserção de dados na tabela 'anime_director'
INSERT INTO anime_director (anime_id, director_id) VALUES (1, 1); -- Associa Anime 1 ao Director 1
INSERT INTO anime_director (anime_id, director_id) VALUES (2, 2); -- Associa Anime 2 ao Director 2

-- Inserção de dados na tabela 'anime_studio'
INSERT INTO anime_studio (anime_id, studio_id) VALUES (1, 1); -- Associa Anime 1 ao Studio 1
INSERT INTO anime_studio (anime_id, studio_id) VALUES (2, 2); -- Associa Anime 2 ao Studio 2

-- Inserção de dados na tabela 'tb_user'
INSERT INTO tb_user (email, password, username) VALUES ('user1@email.com', 'password1', 'User1');
INSERT INTO tb_user (email, password, username) VALUES ('user2@email.com', 'password2', 'User2');

-- Inserção de dados na tabela 'tb_user_animes'
INSERT INTO tb_user_animes (animes_id, user_id) VALUES (1, 1); -- Associa Anime 1 ao User1
INSERT INTO tb_user_animes (animes_id, user_id) VALUES (2, 2); -- Associa Anime 2 ao User2
