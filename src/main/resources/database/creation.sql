/* 
 * Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database tables' creation
 */

DROP TABLE IF EXISTS person;
DROP SEQUENCE IF EXISTS person_seq;
DROP TABLE IF EXISTS address;
DROP SEQUENCE IF EXISTS address_seq;
DROP TABLE IF EXISTS city;
DROP SEQUENCE IF EXISTS city_seq;
DROP TABLE IF EXISTS country;
DROP SEQUENCE IF EXISTS country_seq;

CREATE SEQUENCE country_seq;

CREATE TABLE country (
	id		smallint DEFAULT nextval('country_seq') NOT NULL	PRIMARY KEY,
	name 	varchar(35)		NOT NULL UNIQUE,
	code 	varchar(2)		NOT NULL UNIQUE
);

CREATE SEQUENCE city_seq;

CREATE TABLE city (
	id			integer DEFAULT nextval('city_seq') NOT NULL		PRIMARY KEY,
	name 		varchar(35)		NOT NULL UNIQUE,
	country_id	integer			NOT NULL REFERENCES country(id)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE SEQUENCE address_seq;

CREATE TABLE address (
	id			integer DEFAULT nextval('address_seq') NOT NULL		PRIMARY KEY,
	address1 	varchar(35)		NULL,
	address2 	varchar(35)		NULL,
	zip_code	varchar(10)		NULL,
	city_id		integer			NOT NULL REFERENCES city(id)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE SEQUENCE person_seq;

CREATE TABLE person (
    id					integer DEFAULT nextval('person_seq') NOT NULL		PRIMARY KEY,
    first_name			varchar(35) 	NOT NULL,
    last_name			varchar(35)		NOT NULL,
	email				varchar(50)		NOT NULL UNIQUE,
	password			varchar(50)		NOT NULL,
	nif					varchar(9)		NULL UNIQUE,
	phone				varchar(12)		NULL,
	active				boolean			NOT NULL DEFAULT true,
	admin_role			boolean			NOT NULL DEFAULT false,
	address_id			integer			NULL REFERENCES address(id)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE registry (
	person_id			integer			NOT NULL PRIMARY KEY REFERENCES person(id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	confirmation_id		text			NOT NULL
);
