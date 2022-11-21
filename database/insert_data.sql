INSERT INTO Users (name, coins, password) VALUES('bea', 0, '$2y$10$WpYMMVI32Dgc.JkVDynGTucqJbindPY.LMWfDBl.12WfBv9HBUySO');
INSERT INTO Users (name, coins, password) VALUES('felix', 10, '$2y$10$WpYMMVI32Dgc.JkVDynGTucqJbindPY.LMWfDBl.12WfBv9HBUySO');

INSERT INTO Cards (damage, category, element_type) VALUES(32, 's', 'f');
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

Select * from Users;

SELECT * FROM Users WHERE user_id = 1





