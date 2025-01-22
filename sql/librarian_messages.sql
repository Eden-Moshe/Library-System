create table librarian_messages
(
message_id INT AUTO_INCREMENT PRIMARY KEY,
librarian_id INT,
FOREIGN KEY (librarian_id) REFERENCES librarian(librarian_id),
sender VARCHAR(255),
message VARCHAR(1023),
is_new BOOLEAN default TRUE
);