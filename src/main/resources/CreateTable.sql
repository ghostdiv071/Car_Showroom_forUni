CREATE TABLE City (
	ID SERIAL PRIMARY KEY, 
	Name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE Gender (
	ID SERIAL PRIMARY KEY,
	Name varchar(255) NOT NULL UNIQUE 
);

CREATE TABLE Positions (
	ID SERIAL PRIMARY KEY,
	Name varchar(255) NOT NULL UNIQUE 
);

CREATE TABLE Engine_Type (
	ID SERIAL PRIMARY KEY,
	Name varchar(255) NOT NULL UNIQUE 
);

CREATE TABLE Brand (
	ID SERIAL PRIMARY KEY,
	Name varchar(255) NOT NULL UNIQUE 
);

CREATE TABLE Color (
	ID SERIAL PRIMARY KEY,
	Name varchar(255) NOT NULL UNIQUE 
);

CREATE TABLE Carcase (
	ID SERIAL PRIMARY KEY,
	Name varchar(255) NOT NULL UNIQUE 
);

CREATE TABLE Model (
	ID SERIAL PRIMARY KEY,
	Name varchar(255) NOT NULL UNIQUE,
	Brand_ID int references Brand(ID)  
);

CREATE TABLE Engine (
	ID SERIAL PRIMARY KEY,
	Capacity smallint CHECK(Capacity < 1000 AND Capacity > 99),
	Volume float CHECK (Volume > 1.0 AND Volume < 10.0),
	Type_ID int references Engine_Type(ID) NOT NULL
);

CREATE TABLE Customer (
	ID SERIAL PRIMARY KEY,
	Name varchar(32) NOT NULL,
	Second_Name varchar(32),
	Last_Name varchar(32) NOT NULL,
	Email varchar(255) NOT NULL,
	Password varchar(255) NOT NULL
);
 
CREATE TABLE Vehicle_Passport (
	ID SERIAL PRIMARY KEY,
	Mass smallint CHECK (Mass > 999 AND Mass < 10000),
	Max_Mass smallint CHECK (Mass > 999 AND Mass < 10000),
	Tank_Volume smallint CHECK (Tank_Volume > 0 AND Tank_Volume < 256),
	Model_ID int references Model(ID) NOT NULL,
	Color_ID int references Color(ID) NOT NULL,
	Carcase_ID int references Carcase(ID) NOT NULL
);

ALTER TABLE Vehicle_Passport ADD Release_Year smallint CHECK (Release_Year > 2000 AND Release_Year < 2020);

CREATE TABLE Car_Showroom (
	ID SERIAL PRIMARY KEY,
	Street varchar(255) NOT NULL,
	House_Number smallint CHECK (House_Number > 0),
	City_ID int references City(ID) NOT NULL
);

CREATE TABLE EMPLOYEE (
	ID SERIAL PRIMARY KEY,
	Name varchar(32) NOT NULL,
	Second_Name varchar(32),
	Last_Name varchar(32) NOT NULL,
	Birthday date NOT NULL,
	Position_ID int references Positions(ID) NOT NULL,
	Gender_ID int references Gender(ID),
	Showroom_ID int references Car_Showroom(ID)
);

CREATE TABLE Car (
	ID SERIAL PRIMARY KEY,
	Price int CHECK (Price > 0),
	Showroom_ID int references Car_Showroom(ID) NOT NULL,
	Engine_ID int references Engine(ID) NOT NULL,
	VIN int references Vehicle_Passport(ID) NOT NULL
);



CREATE TABLE Purchase (
	ID SERIAL PRIMARY KEY,
	Car_ID int references Car(ID) NOT NULL,
	Customer_ID int references Customer(ID) NOT NULL
);

INSERT INTO City (Name) VALUES
	('Москва'), 
	('Воронеж'), 
	('Санкт-Петербург')
;

INSERT INTO Gender (Name) VALUES
	('Мужской'), 
	('Женский')
;

INSERT INTO Positions (Name) VALUES
	('Директор'), 
	('Финансовый директор'), 
	('Начальник отдела продаж'),
	('Начальник отдела кадров'),
	('Администратор торгового зала'),
	('Менеджер по продаже в зале'),
	('Бухгалтер'),
	('Офис-менеджер'),
	('Логист')
;

INSERT INTO Engine_Type (Name) VALUES
	('бензиновый'),
	('гибридный'),
	('дизельный'),
	('электрический')
;

INSERT INTO Brand (Name) VALUES
	('Audi'),
	('BMW'),
	('Jaguar'),
	('Lexus'),
	('Toyota'),
	('Volvo')
;

INSERT INTO Color (Name) VALUES
	('red'),
	('orange'),
	('yellow'),
	('green'),
	('cyan'),
	('blue'),
	('purlple'),
	('pink'),
	('black'),
	('white'),
	('grey')
;

INSERT INTO Carcase (Name) VALUES
	('sedan'),
	('coupe'),
	('hatchback'),
	('wagon'),
	('suv')
;

INSERT INTO Model (Name) VALUES
	('A6'),
	('Q7'),
	('8 series'),
	('X3'),
	('XF'),
	('ES'),
	('RX'),
	('Camry'),
	('V90'),
	('XC 90')
;

INSERT INTO ENGINE (Capacity, Volume, Type_ID) VALUES
	(190, 2.0, 1),
	(245, 3.0, 1),
	(249, 3.0, 3),
	(340, 4.5, 1),
	(200, 2.5, 1),
	(249, 3.5, 1),
	(313, 3.5, 2),
	(300, 3.5, 1),
	(407, 2.0, 2),
	(333, 5.0, 4)
;

INSERT INTO Car_Showroom (Street, House_Number, City_ID) VALUES
	('Волгоградский проспект', 41, 1),
	('Улица Шишкова', 75, 2),
	('Улица Руставели', 53, 3)
;

INSERT INTO Customer (Name, Second_Name, Last_Name, Email, Password) VALUES
	('Иван', 'Иванович', 'Иванов', 'ivanov@mail.com', 'ivanov1234'),
	('Пётр', 'Петрович', 'Петров', 'petrov@mail.com', 'petrov1234321'),
	('John', NULL, 'Smith', 'englshmn@icloud.com', 'qwertyui1928')
;

INSERT INTO EMPLOYEE (Name, Second_Name, Last_Name, Birthday, Position_ID, Gender_ID, Showroom_ID) VALUES
	('Екатерина', 'Александровна', 'Рудова', '1974-12-17', 1, 2, NULL),
	('Семён', 'Андреевич', 'Кузнецов', '1970-12-06', 2, 1, NULL),
	('Павел', 'Трофимович', 'Морозов', '1967-02-28', 3, 1, NULL),
	('Анна', 'Сергеевна', 'Новикова', '1981-03-27', 4, 2, NULL),
	('Святослав', 'Константинович', 'Мельников', '1973-04-02', 5, 1, 1),
	('Степан', 'Дмитриевич', 'Макаров', '1986-07-13', 5, 1, 2),
	('Фёдор', 'Ярославович', 'Пантелеев', '1982-10-18', 5, 1, 3)
;

INSERT INTO Vehicle_Passport (Release_Year, Mass, Max_Mass, Tank_Volume, Model_ID, Color_ID, Carcase_ID) VALUES
	(2018, 1680, 2000, 70, 1, 11, 4),
	(2019, 2105, 2500, 75, 2, 1, 5),
	(2018, 1965, 2500, 68, 3, 5, 2),
	(2017, 1725, 2250, 65, 4, 7, 5),
	(2015, 1635, 2150, 74, 5, 9, 1),
	(2016, 1700, 2200, 60, 6, 10, 1),
	(2019, 1885, 2500, 72, 7, 6, 5),
	(2017, 1580, 2000, 60, 8, 4, 1),
	(2016, 1920, 2475, 60, 9, 3, 3),
	(2015, 1966, 2525, 71, 10, 9, 5)
;

INSERT INTO Car (Price, Showroom_ID, Engine_ID, VIN) VALUES
	(3500000, 1, 2, 1),
	(4800000, 2, 3, 2),
	(7600000, 1, 4, 3),
	(4300000, 3, 6, 4),
	(4000000, 2, 8, 5),
	(2900000, 3, 5, 6),
	(5800000, 1, 7, 7),
	(2300000, 2, 1, 8),
	(4100000, 1, 6, 9),
	(6200000, 3, 9, 10)
;

INSERT INTO Purchase (Car_ID, Customer_ID) VALUES
	(14, 1),
	(19, 2),
	(12, 3)
;

INSERT INTO Purchase (Car_ID, Customer_ID) VALUES
	(11, 1),
	(15, 2),
	(16, 3)
;

commit;
