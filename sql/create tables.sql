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
status_is_current BOOLEAN
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
FOREIGN KEY (book_barcode) REFERENCES book(barcode),
lending_librarian INT,
FOREIGN KEY (lending_librarian) REFERENCES librarian(librarian_id),
subscriber_id INT,
FOREIGN KEY (subscriber_id) REFERENCES users(user_id),
borrow_date DATE,
return_date DATE,
actual_returned_date DATE

);

create table extensions
(
lending_librarian INT,
FOREIGN KEY (lending_librarian) REFERENCES librarian(librarian_id),

borrow_number INT,
FOREIGN KEY (borrow_number) REFERENCES borrow(borrow_number),

day_of_extension DATE,

new_return_date DATE
);





CREATE TABLE order_book (
book_name varchar(50),
nearest_book_return DATE,
subscriber_id INT,
FOREIGN KEY (subscriber_id) REFERENCES users(user_id),
order_status ENUM ('fulfilled','waiting','cancelled') default 'waiting'
);

