DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id INT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  category VARCHAR(250) NOT NULL,
  retail_price double NOT NULL,
  discounted_price double NOT NULL,
  availability BOOLEAN NOT NULL
);