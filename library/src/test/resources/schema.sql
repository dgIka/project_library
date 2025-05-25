CREATE TABLE person (
                        person_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        name VARCHAR(100) UNIQUE NOT NULL,
                        birth_date DATE NOT NULL
);

CREATE TABLE book (
                      book_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      title VARCHAR(100) UNIQUE NOT NULL,
                      author VARCHAR(100) NOT NULL,
                      "year" INT NOT NULL,
                      person_id INT,
                      FOREIGN KEY (person_id) REFERENCES person(person_id) ON DELETE SET NULL
);