create schema `fashionstorewebsite`;
use fashionstorewebsite;


create table Login_Accounts(
	ID bigint auto_increment NOT NULL,
	User_Name varchar(50) NOT NULL UNIQUE,
	Password varchar(50) NOT NULL,
    Role varchar(10) NOT NULL,
    
	constraint PK_Login primary key(ID)
);


CREATE TABLE Customers(
	ID bigint auto_increment NOT NULL,
	Name varchar(50) NOT NULL,
	Email varchar(50),
	Phone_Number varchar(10) NOT NULL,
	Avatar text,
    Account_ID bigint NOT NULL,
    
    constraint FK_Customers_Accounts foreign key(Account_ID) references Login_Accounts(ID),

	CONSTRAINT PK_Customers PRIMARY KEY(ID)
);


CREATE TABLE Staffs(
	ID bigint auto_increment NOT NULL,
	Name varchar(50) NOT NULL,
	Birth_Date date NOT NULL,
	Hometown varchar(50) NOT NULL,
	Position varchar(50) NOT NULL,
	Email varchar(50) NOT NULL,
	Phone_Number varchar(50) NOT NULL,
	Avatar text NOT NULL,
    Account_ID bigint NOT NULL,
    
	constraint FK_Staffs_Accounts foreign key(Account_ID) references Login_Accounts(ID),

	CONSTRAINT PK_Staffs primary key(ID)
);


create table  Products(
	ID bigint auto_increment NOT NULL,
	Name varchar(50) NOT NULL,
	Price double NOT NULL,
    Original_Price double NOT NULL,
    Color varchar(50) NOT NULL,
    Size varchar(10) NOT NULL,
	Available_Quantity int NOT NULL,
	Sold_Quantity  bigint NOT NULL,
	One_star_quantity int,
	Two_star_quantity int,
	Three_star_quantity int,
	Four_star_quantity int,
	Five_star_quantity int,
    Brand varchar(50) NOT NULL,
    Discount double NOT NULL,
    
	constraint PK_Products primary key(ID, Size, Color)
);


create table Catalog(
	ID bigint auto_increment NOT NULL,
	Name varchar(50) NOT NULL,
    
	constraint PK_Catalog primary key(ID)
);


create table Cart(
	ID bigint auto_increment NOT NULL, 
	Customer_ID bigint NOT NULL,
	Product_ID bigint NOT NULL,
	Quantity int NOT NULL,
    
    constraint PK_Cart primary key(ID),

	constraint FK_Customer_Cart foreign key(Customer_ID) references Customers(ID),
	constraint FK_Product_Cart foreign key(Product_ID) references Products(ID)
);


create table Comments(
	ID bigint auto_increment NOT NULL,
	Product_ID bigint NOT NULL,
	Customer_ID bigint NOT NULL,
	Comment_Content text NOT NULL,
    
	constraint PK_Comments primary key(ID),

	constraint FK_Customer_Comments foreign key(Customer_ID) references Customers(ID),
	constraint FK_Product_Comments foreign key(Product_ID) references Products(ID)
);


create table Catalog_with_Products(
	Catalog_ID bigint NOT NULL,
	Product_ID bigint NOT NULL,
    
	constraint PK_CwP primary key(Catalog_ID,Product_ID),

	constraint FK_catalog foreign key(Catalog_ID) references Catalog(ID),
	constraint FK_products foreign key(Product_ID) references Products(ID)
);


create table Invoice(
	ID bigint NOT NULL,
	Customer_ID bigint NOT NULL,
	Invoice_Date date NOT NULL,
	Product_ID bigint NOT NULL,
	Quantity int NOT NULL,
	Payment_Status tinyint NOT NULL,
    
	constraint PK_Invoice primary key(ID),

	constraint FK_customer_invoice foreign key(Customer_ID) references Customers(ID),
	constraint FK_Product_invoice foreign key(Product_ID) references Products(ID)
);


create table Product_Different_Images(
	Product_ID bigint NOT NULL,
	Image_1 text,
	Image_2 text,
	Image_3 text,
	Image_4 text,
    
	constraint PK_Images primary key(Product_ID),

	constraint FK_Product_images foreign key(Product_ID) references Products(ID)
);


create table Delivery(
	Invoice_ID bigint NOT NULL,
	Shipper_ID bigint NOT NULL,
	Delivery_Date date NOT NULL,
	Current_Status bit NOT NULL ,
    
	constraint PK_delivery primary key(Invoice_ID),

	constraint FK_Staff_delivery foreign key(Shipper_ID) references Staffs(ID),
	constraint FK_Invoice_delivery foreign key(Invoice_ID) references Invoice(ID)
);


insert into staffs (Name, Birth_Date, Hometown, Position, Email, Phone_Number, Avatar, Account_ID) values ('sang', STR_TO_DATE('23-06-2001', '%d-%m-%Y'), 'hcm', 'staff', 'nguyenhoangsang236@gmail.com', '0123456987', 'https://upload.wikimedia.org/wikipedia/commons/f/fe/Son_Tung_M-TP_1_%282017%29.png', 1);

insert into customers (Name, Email, Phone_Number, Avatar, Account_ID) values ('phuc', 'phuclateo@gmail.com', '0123456987', 'https://photo-cms-plo.epicdn.me/w850/Uploaded/2022/ernccqrwq/2022_09_09/thai-vu-1249.jpg', 2);

INSERT INTO `fashionstorewebsite`.`products` (`Name`, `Price`, `Original_Price`, `Color`, `Size`, `Available_Quantity`, `Sold_Quantity`, `One_star_quantity`, `Two_star_quantity`, `Three_star_quantity`, `Four_star_quantity`, `Five_star_quantity`, `Brand`, `Discount`) VALUES ('Adidas Future Icons 3-Stripes Black Hoodie', 1550000, '1200000', 'black', 'xxl', '50', '10', '0', '0', '0', '0', '0', 'Addidas', '0');

insert into catalog_with_products(Catalog_ID, Product_ID) values (1, 1);

insert into product_different_images(Product_ID, Image_1, Image_2, Image_3, Image_4) values (1, 'https://cdn.vortexs.io/api/images/220db91a-368d-4e6c-bc2a-816850f4a870/1920/w/ao-hoodie-adidas-future-icons-3-stripes-black-hk4572.jpeg', 'https://cdn.vortexs.io/api/images/b32134de-59d8-4705-a92a-eb60d63e3c6f/1920/w/ao-hoodie-adidas-future-icons-3-stripes-black-hk4572.jpeg', 'https://cdn.vortexs.io/api/images/82569bda-8f98-41f5-9bca-326b436f56d7/1920/w/ao-hoodie-adidas-future-icons-3-stripes-black-hk4572.jpeg', 'https://cdn.vortexs.io/api/images/63b031bc-d058-4471-bc87-373692291699/1920/w/ao-hoodie-adidas-future-icons-3-stripes-black-hk4572.jpeg');

select * from product_different_images;

select * from staffs;