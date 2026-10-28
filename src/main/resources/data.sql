-- Sample rows, inserted only when the table is empty.
INSERT INTO students (name, email, age)
SELECT * FROM (VALUES
    ('Ananya Rao',    'ananya.rao@example.com',    20),
    ('Rohan Mehta',   'rohan.mehta@example.com',   22),
    ('Priya Nair',    'priya.nair@example.com',    21),
    ('Karthik Iyer',  'karthik.iyer@example.com',  24),
    ('Sneha Kulkarni','sneha.kulkarni@example.com',19)
) AS seed(name, email, age)
WHERE NOT EXISTS (SELECT 1 FROM students);
