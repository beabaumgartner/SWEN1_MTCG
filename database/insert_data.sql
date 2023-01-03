INSERT INTO Users (username, password) VALUES('bea08', '$2y$10$WpYMMVI32Dgc.JkVDynGTucqJbindPY.LMWfDBl.12WfBv9HBUySO');
INSERT INTO Users (username, password) VALUES('flex', '$2y$10$WpYMMVI32Dgc.JkVDynGTucqJbindPY.LMWfDBl.12WfBv9HBUySO');

INSERT INTO Cards (card_id, damage, category, element_type) VALUES(32, 's', 'f');
INSERT INTO Cards (damage, category, element_type) VALUES(50, 'm', 'w');
INSERT INTO Cards (damage, category, element_type) VALUES(22, 'm', 'f');
INSERT INTO Cards (damage, category, element_type) VALUES(30, 's', 'w');

INSERT INTO Stack (user_id) VALUES(1);
INSERT INTO Stack (user_id) VALUES(2);

INSERT INTO Stack_Cards (stack_id, card_id, quantity) VALUES(1, 2, 1);
INSERT INTO Stack_Cards (stack_id, card_id, quantity) VALUES(1, 3, 1);
INSERT INTO Stack_Cards (stack_id, card_id, quantity) VALUES(1, 4, 1);
-----------
INSERT INTO Stack_Cards (stack_id, card_id, quantity) VALUES(2, 4, 1);
INSERT INTO Stack_Cards (stack_id, card_id, quantity) VALUES(2, 2, 1);
INSERT INTO Stack_Cards (stack_id, card_id, quantity) VALUES(2, 3, 2);


INSERT INTO Deck (user_id) VALUES(1);
INSERT INTO Deck (user_id) VALUES(2);

INSERT INTO Deck_Cards (deck_id, card_id, quantity) VALUES(1, 1, 1);
INSERT INTO Deck_Cards (deck_id, card_id, quantity) VALUES(1, 4, 1);
INSERT INTO Deck_Cards (deck_id, card_id, quantity) VALUES(1, 3, 1);
-----------
INSERT INTO Deck_Cards (deck_id, card_id, quantity) VALUES(2, 1, 1);
INSERT INTO Deck_Cards (deck_id, card_id, quantity) VALUES(2, 4, 1);
INSERT INTO Deck_Cards (deck_id, card_id, quantity) VALUES(2, 2, 2);

UPDATE Users
SET name = 'Felix Schuster',
    bio = 'Ich mag Eislaufen',
    image = ':)'
WHERE username = 'flex';

SELECT name, bio, image from Users Where username = 'flex';

Alter USER postgres with PASSWORD 'postgres';

SELECT * FROM Tokens WHERE user_id = 1;
SELECT * FROM Tokens JOIN Users ON Tokens.user_id = Users.user_id WHERE users.username = 'bea08' AND Tokens.token = 'bea08-mtcgToken'