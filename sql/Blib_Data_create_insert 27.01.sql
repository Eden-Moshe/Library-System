create table users
(
user_id INT AUTO_INCREMENT PRIMARY KEY,
user_password varchar(255),
user_role ENUM('Librarian', 'Subscriber') DEFAULT 'Subscriber'

);

create table librarian
(
librarian_id INT,
FOREIGN KEY (librarian_id) REFERENCES users(user_id),
librarian_name varchar(255)
);



create table subscriber
(
subscriber_id INT,
FOREIGN KEY (subscriber_id) REFERENCES users(user_id),
subscriber_name varchar(255),
subscriber_phone_number varchar (15),
subscriber_email varchar(255),
subscriber_status ENUM('active', 'frozen') DEFAULT 'active'

);

create table user_status_registry
(
user_id INT,
FOREIGN KEY (user_id) REFERENCES users(user_id),
status_set_date DATE,
status_end_date DATE,
status_is_current BOOLEAN,
subscriber_status ENUM('active', 'frozen')

);


CREATE TABLE book (
  barcode varchar(50) NOT NULL,
  book_name varchar(255) DEFAULT NULL,
  book_genre varchar(255) DEFAULT NULL,
  book_description varchar(255) DEFAULT NULL,
  shelf_location varchar(50) DEFAULT NULL,
  book_available tinyint(1) DEFAULT NULL,
  return_date DATE,
  PRIMARY KEY (barcode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


create table borrow
(
borrow_number INT AUTO_INCREMENT PRIMARY KEY,
book_barcode varchar(50),
FOREIGN KEY (book_barcode) REFERENCES book(barcode) ON DELETE CASCADE,
lending_librarian INT,
FOREIGN KEY (lending_librarian) REFERENCES librarian(librarian_id) ON DELETE CASCADE,
subscriber_id INT,
FOREIGN KEY (subscriber_id) REFERENCES users(user_id) ON DELETE CASCADE,
borrow_date DATE,
return_date DATE,
actual_returned_date DATE
);

create table extensions
(
lending_librarian INT,
FOREIGN KEY (lending_librarian) REFERENCES librarian(librarian_id) ON DELETE CASCADE,
borrow_number INT,
FOREIGN KEY (borrow_number) REFERENCES borrow(borrow_number) ON DELETE CASCADE,
day_of_extension DATE,
new_return_date DATE
);


create table librarian_messages
(
message_id INT AUTO_INCREMENT PRIMARY KEY,
librarian_id INT,
FOREIGN KEY (librarian_id) REFERENCES librarian(librarian_id) ON DELETE CASCADE,
sender VARCHAR(255),
message VARCHAR(1023),
is_new BOOLEAN default TRUE
);



CREATE TABLE order_book (
book_name varchar(50),
nearest_book_return DATE,
subscriber_id INT,
FOREIGN KEY (subscriber_id) REFERENCES users(user_id) ON DELETE CASCADE,
order_status ENUM ('fulfilled','waiting','cancelled') DEFAULT'waiting');


CREATE TABLE destroyed_books (
    book_barcode VARCHAR(50),
    borrower_id INT,
    PRIMARY KEY (book_barcode),
    FOREIGN KEY (borrower_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert data into users table
INSERT INTO users (user_id, user_password, user_role) VALUES
(1, '123', 'Librarian'),
(2, '123', 'Subscriber'),
(3, '123', 'Librarian'),
(4, '123', 'Subscriber'),
(5, '123', 'Subscriber'),
(6, 'noypass', 'Subscriber'),
(7, 'newpass1', 'Subscriber'),
(8, 'newpass2', 'Subscriber'),
(9, 'newpass3', 'Subscriber'),
(10, 'newpass4', 'Subscriber'),
(11, 'newpass5', 'Subscriber'),
(12, 'tompass', 'Subscriber'),
(13, 'emmapass', 'Subscriber'),
(14, 'chrispass', 'Subscriber'),
(15, 'natpass', 'Subscriber'),
(16, 'scarpass', 'Subscriber'),
(17, 'robpass', 'Subscriber'),
(18, 'galpass', 'Subscriber'),
(19, 'hughpass', 'Subscriber'),
(20, 'zenpass', 'Subscriber'),
(21, 'ryanpass', 'Subscriber');

-- Insert data into librarian table
INSERT INTO librarian (librarian_id, librarian_name) VALUES
(1, 'John Smith'),
(3, 'Jane Doe');

-- Insert data into subscriber table
INSERT INTO subscriber (subscriber_id, subscriber_name, subscriber_phone_number, subscriber_email, subscriber_status) VALUES
(2, 'Alice Brown', '1234567890', 'alice@example.com', 'active'),
(4, 'Bob White', '9876543210', 'bob@example.com', 'active'),
(5, 'Eden Moshe', '0541213564', 'eden@example.com', 'active'),
(6, 'Noy Shemesh', '0541588974', 'noy@example.com', 'frozen'),
(7, 'David Cohen', '0523344556', 'david@example.com', 'active'),
(8, 'Sarah Levi', '0507788996', 'sarah@example.com', 'active'),
(9, 'Daniel Green', '0549988774', 'daniel@example.com', 'frozen'),
(10, 'Michael Ron', '0524455667', 'michael@example.com', 'active'),
(11, 'Leah Gold', '0508877665', 'leah@example.com', 'frozen'),
(12, 'Tom Hardy', '0531234567', 'tom@example.com', 'active'),
(13, 'Emma Watson', '0549876543', 'emma@example.com', 'active'),
(14, 'Chris Evans', '0521112233', 'chris@example.com', 'frozen'),
(15, 'Natalie Portman', '0545556677', 'natalie@example.com', 'active'),
(16, 'Scarlett Johansson', '0502233445', 'scarlett@example.com', 'active'),
(17, 'Robert Downey', '0529988776', 'robert@example.com', 'frozen'),
(18, 'Gal Gadot', '0546677889', 'gal@example.com', 'active'),
(19, 'Hugh Jackman', '0537766554', 'hugh@example.com', 'frozen'),
(20, 'Zendaya Coleman', '0543344556', 'zendaya@example.com', 'active'),
(21, 'Ryan Gosling', '0524433221', 'ryan@example.com', 'active');

-- Insert data into user_status_registry table
INSERT INTO user_status_registry (user_id, status_set_date, status_end_date, status_is_current, subscriber_status) VALUES
(2, '2025-01-01', NULL, TRUE, 'active'),
(4, '2025-01-10', '2025-01-20', FALSE, 'frozen');

-- Insert data into book table
INSERT INTO book (barcode, book_name, book_genre, book_description, shelf_location, book_available, return_date) VALUES
('B001', 'The Great Gatsby', 'Fiction', 'A classic novel by F. Scott Fitzgerald', 'A1', 0, '2025-01-17'),
('B002', '1984', 'Dystopian', 'A novel by George Orwell', 'B2', 0, '2025-01-19'),
('B003', 'To Kill a Mockingbird', 'Fiction', 'A novel by Harper Lee', 'C3', 0, '2025-01-05'),
('B456', 'Wicked', 'Fantasy', 'A novel by L. Frank Baum', 'A1', 0, '2025-01-15'),
('D501', 'Wizard Of Oz', 'Fantasy', 'A Story about the land of Oz', 'C4', 1, NULL),
('B789', 'Harry Potter', 'Fantasy', 'A novel by J.K. Rowling', 'B5', 0, '2025-01-23'),
('B999', 'The Hobbit', 'Fantasy', 'A novel by J.R.R. Tolkien', 'C6', 0, '2025-01-25'),
('B100', 'Pride and Prejudice', 'Romance', 'A novel by Jane Austen', 'D1', 1, NULL),
('B200', 'Moby Dick', 'Adventure', 'A novel by Herman Melville', 'D2', 1, NULL),
('B300', 'Dracula', 'Horror', 'A novel by Bram Stoker', 'A7', 0, '2025-01-30'),
('B400', 'The Catcher in the Rye', 'Fiction', 'A novel by J.D. Salinger', 'B8', 1, NULL),
('B500', 'The Kite Runner', 'Drama', 'A novel by Khaled Hosseini', 'C9', 0, '2025-01-30'),
('B600', 'Life of Pi', 'Adventure', 'A novel by Yann Martel', 'D3', 1, NULL),
('B700', 'The Road', 'Dystopian', 'A novel by Cormac McCarthy', 'A4', 0, '2025-01-31'),
('B800', 'Gone Girl', 'Thriller', 'A novel by Gillian Flynn', 'B6', 1, NULL),
('B900', 'Dune', 'Sci-Fi', 'A novel by Frank Herbert', 'C8', 0, '2025-01-31'),
('B101', 'The Shining', 'Horror', 'A novel by Stephen King', 'A9', 0, '2025-01-31'),
('B201', 'Frankenstein', 'Horror', 'A novel by Mary Shelley', 'B10', 0, '2025-01-31'),
('B301', 'The Alchemist', 'Fiction', 'A novel by Paulo Coelho', 'C10', 1, NULL),
('B202', 'The Midnight Library', 'Fiction', 'A novel by Matt Haig', 'B3', 1, NULL),
('B203', 'The Night Circus', 'Fantasy', 'A novel by Erin Morgenstern', 'C1', 1, NULL),
('B204', 'Educated', 'Memoir', 'A memoir by Tara Westover', 'D5', 1, NULL),
('B205', 'The Silent Patient', 'Thriller', 'A novel by Alex Michaelides', 'A6', 1, NULL),
('B206', 'Circe', 'Fantasy', 'A novel by Madeline Miller', 'B4', 1, NULL);

-- Insert data into borrow table
INSERT INTO borrow (book_barcode, lending_librarian, subscriber_id, borrow_date, return_date, actual_returned_date) VALUES
('B001', 1, 2, '2025-01-05', '2025-01-17', NULL),
('B002', 3, 4, '2025-01-07', '2025-01-19', NULL),
('B003', 1, 5, '2025-01-01', '2025-01-05', '2025-02-10'),
('B456', 3, 6, '2025-01-03', '2025-01-15', '2025-01-30'),
('D501', 1, 7, '2025-01-14', '2025-01-26', NULL),
('B789', 3, 8, '2025-01-09', '2025-01-23', '2025-01-15'),
('B999', 1, 9, '2025-01-11', '2025-01-25', '2025-01-20'),
('B100', 3, 10, '2025-01-13', '2025-01-27', NULL),
('B200', 1, 11, '2025-01-13', '2025-01-25', '2025-01-29'),
('B300', 1, 12, '2025-01-17', '2025-01-30', '2025-01-30'),
('B400', 3, 13, '2025-01-18', '2025-01-28', NULL),
('B500', 1, 14, '2025-01-20', '2025-01-30', '2025-02-05'),
('B600', 3, 15, '2025-01-21', '2025-02-09', NULL),
('B700', 1, 16, '2025-01-22', '2025-01-31', NULL),
('B800', 3, 17, '2025-02-01', '2025-02-15', NULL),
('B900', 1, 18, '2025-01-24', '2025-01-31', NULL),
('B101', 3, 19, '2025-01-25', '2025-01-31', '2025-01-31'),
('B201', 1, 20, '2025-01-20', '2025-01-31', '2025-01-31'),
('B301', 3, 21, '2025-01-24', '2025-01-31', NULL);

-- Insert data into extensions table
INSERT INTO extensions (lending_librarian, borrow_number, day_of_extension, new_return_date) VALUES
(1, 1, '2025-01-15', '2025-01-25'),
(3, 1, '2025-01-18', '2025-01-28');

-- Insert data into order_book table
INSERT INTO order_book (book_name, nearest_book_return, subscriber_id, order_status) VALUES
('The Great Gatsby', '2025-01-25', 4, 'waiting'),
('1984', '2025-01-28', 2, 'fulfilled');

