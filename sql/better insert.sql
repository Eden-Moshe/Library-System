-- 1) Insert Users
--    Note: Every user is either a librarian or a subscriber.
INSERT INTO users (user_password, user_role)
VALUES
  ('123', 'Librarian'),   -- user_id = 1
  ('123', 'Librarian'),  -- user_id = 2
  ('123', 'Subscriber'),    -- user_id = 3
  ('123', 'Subscriber'),    -- user_id = 4
  ('123', 'Subscriber');    -- user_id = 5

-- 2) Insert Librarians
--    Matches user_id 1 and 2.
INSERT INTO librarian (librarian_id, librarian_name)
VALUES
  (1, 'Alice Smith'),
  (2, 'Bob Johnson');

-- 3) Insert Subscribers
--    Matches user_id 3, 4, and 5.
INSERT INTO subscriber (subscriber_id, subscriber_name, subscriber_phone_number, subscriber_email, subscriber_status)
VALUES
  (3, 'Charlie Brown', '123-456-7890', 'charlie@example.com', 'active'),
  (4, 'Diana Prince', '987-654-3210', 'diana@example.com', 'active'),
  (5, 'Ethan Hawk',   '555-444-3333', 'ethan@example.com', 'frozen');

-- 4) Insert User Status Registry entries
--    Each entry references valid users in the 'users' table.
INSERT INTO user_status_registry (user_id, status_set_date, status_end_date, status_is_current)
VALUES
  (3, '2024-01-01', '2024-01-15', 0),
  (3, '2024-01-16', NULL,        1), 
  (4, '2024-01-10', NULL,        1),
  (5, '2024-01-05', NULL,        1);

-- 5) Insert Books
INSERT INTO book (barcode, book_name, book_genre, book_description, shelf_location, book_available, return_date)
VALUES
  ('B0001', 'The Great Gatsby',       'Fiction',   'A classic novel by F. Scott Fitzgerald', 'Shelf A1', 1, NULL),
  ('B0002', 'To Kill a Mockingbird',  'Fiction',   'A novel by Harper Lee',                  'Shelf A2', 1, NULL),
  ('B0003', '1984',                   'Dystopian', 'A novel by George Orwell',               'Shelf B1', 0, '2025-02-01'),
  ('B0004', 'Moby-Dick',              'Adventure', 'A novel by Herman Melville',             'Shelf B2', 1, NULL);

-- 6) Insert Borrows
--    References valid book barcodes, librarian IDs, and subscriber (user) IDs.
INSERT INTO borrow (book_barcode, lending_librarian, subscriber_id, borrow_date, return_date, actual_returned_date)
VALUES
  -- Borrow #1
  ('B0003', 1, 3, '2025-01-01', '2025-02-01', NULL),
  -- Borrow #2
  ('B0001', 2, 4, '2025-01-05', '2025-02-05', NULL);

-- 7) Insert Extensions
--    References the borrow_number from the 'borrow' table and a valid librarian.
--    (Assumes the first insert in 'borrow' produced borrow_number = 1, the second is 2, etc.)
INSERT INTO extensions (lending_librarian, borrow_number, day_of_extension, new_return_date)
VALUES
  (1, 1, '2025-01-20', '2025-02-15');

-- 8) Insert Orders
--    References a valid subscriber_id.
INSERT INTO order_book (book_name, nearest_book_return, subscriber_id, order_status)
VALUES
  ('1984',                   '2025-02-01', 3, 'waiting'),
  ('To Kill a Mockingbird',  '2025-03-01', 5, 'waiting'),
  ('The Great Gatsby',       '2025-01-25', 4, 'fulfilled');
