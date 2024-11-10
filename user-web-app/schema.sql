CREATE DATABASE Userappdb;


CREATE TABLE Products (
  ProductID INT PRIMARY KEY,
  Name VARCHAR(255),
  Description VARCHAR(255),
  Quantity INT,
  Price DECIMAL(10, 2)
);

CREATE TABLE Orders (
  OrderID INT PRIMARY KEY,
  ProductID INT,
  Quantity INT,
  OrderDate DATE,
  FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);