INSERT INTO peliculas (nombre, genero, caratula) VALUES('Man of Steel', 'Ciencia-Ficción', '4fb39d8f-7a03-45e5-a809-0deaa50c6aab_1369029660_111993_1532610193_sumario_normal.jpg');
INSERT INTO peliculas (nombre, genero, caratula) VALUES('SpiderMan', 'Ciencia-Ficción', '7bf11bab-1926-487e-9a71-efcd45afbf0d_spiderman.jpg');
INSERT INTO peliculas (nombre, genero, caratula) VALUES('Annabelle', 'Terror', '1032a6ea-793c-4274-9988-0686625a7735_annabelle-vuelve-a-casa-poster-1561372176.jpg');
INSERT INTO peliculas (nombre, genero, caratula) VALUES('Wonder Woman 84', 'Aventuras', '7fc2eda5-05ea-47dc-8f78-3a898ad329fc_wonder_woman_nineteen_eighty_four_ver7_xxlg.webp');
INSERT INTO peliculas (nombre, genero, caratula) VALUES('The Batman', 'Acción', 'e1fefbe9-64a3-4042-aef2-aa39c7de3135_The-Batman-poster.jpg');
INSERT INTO peliculas (nombre, genero, caratula) VALUES('Shazam', 'Aventuras', '90874eb7-882c-4a36-bcfe-11830a344044_s-l1600.jpg');
INSERT INTO peliculas (nombre, genero, caratula) VALUES('Halo', 'Ciencia-Ficción', '25b80838-2d36-480f-a731-4f93a637ac83_4629767.jpg-r_1280_720-f_jpg-q_x-xxyxx.jpg');

/* Creamos algunos usuarios con sus roles */
INSERT INTO `users` (username, password, enabled) VALUES ('colin','$2a$10$O9wxmH/AeyZZzIS09Wp8YOEMvFnbRVJ8B4dmAMVSGloR62lj.yqXG',1);
INSERT INTO `users` (username, password, enabled) VALUES ('ceysor','$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS',1);

INSERT INTO `authorities` (user_id, authority) VALUES (1,'ROLE_USER');
INSERT INTO `authorities` (user_id, authority) VALUES (2,'ROLE_ADMIN');
INSERT INTO `authorities` (user_id, authority) VALUES (2,'ROLE_USER');