/* 
 * Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database tables' creation
 */

DROP TABLE IF EXISTS role;
DROP SEQUENCE IF EXISTS role_seq;
DROP TABLE IF EXISTS branch;
DROP SEQUENCE IF EXISTS branch_seq;
DROP TABLE IF EXISTS company;
DROP SEQUENCE IF EXISTS company_seq;
DROP TABLE IF EXISTS category;
DROP SEQUENCE IF EXISTS category_seq;
DROP TABLE IF EXISTS verification;
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
	id				smallint DEFAULT nextval('country_seq') NOT NULL PRIMARY KEY,
	name 			varchar(35)		NOT NULL UNIQUE,
	code 			varchar(2)		NOT NULL UNIQUE
);

CREATE SEQUENCE city_seq;

CREATE TABLE city (
	id				integer DEFAULT nextval('city_seq') NOT NULL PRIMARY KEY,
	name 			varchar(35)		NOT NULL UNIQUE,
	country_id		integer			NOT NULL REFERENCES country(id)
		ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE SEQUENCE address_seq;

CREATE TABLE address (
	id				integer DEFAULT nextval('address_seq') NOT NULL	PRIMARY KEY,
	address1 		varchar(35)		NULL,
	address2 		varchar(35)		NULL,
	zip_code		varchar(10)		NULL,
	city_id			integer			NOT NULL REFERENCES city(id)
		ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE SEQUENCE person_seq;

CREATE TABLE person (
    id				integer DEFAULT nextval('person_seq') NOT NULL PRIMARY KEY,
    first_name		varchar(35) 	NOT NULL,
    last_name		varchar(35)		NOT NULL,
	email			varchar(50)		NOT NULL UNIQUE,
	password		varchar(50)		NOT NULL,
	nif				varchar(9)		NULL UNIQUE,
	phone			varchar(12)		NULL,
	active			boolean			NOT NULL DEFAULT true,
	admin_role		boolean			NOT NULL DEFAULT false,
	address_id		integer			NULL REFERENCES address(id)
		ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE verification (
	person_id		integer			NOT NULL PRIMARY KEY REFERENCES person(id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	confirmation_key		text		NOT NULL UNIQUE
);

CREATE SEQUENCE category_seq;

CREATE TABLE category (
	id				integer DEFAULT nextval('category_seq') NOT NULL PRIMARY KEY,
	name			varchar(30) 	NOT NULL UNIQUE
);

CREATE SEQUENCE company_seq;

CREATE TABLE company (
    id				integer DEFAULT nextval('company_seq') NOT NULL PRIMARY KEY,
    trade_name		varchar(30)		NULL,
    business_name	varchar(50) 	NOT NULL UNIQUE,
	email			varchar(50)		NOT NULL UNIQUE,
	cif				varchar(9)		NOT NULL UNIQUE,
	active			boolean			NOT NULL DEFAULT false,
	category_id		integer			NULL REFERENCES category(id)
		ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE SEQUENCE branch_seq;

CREATE TABLE branch (
	id				integer DEFAULT nextval('branch_seq') NOT NULL PRIMARY KEY,
	company_id		integer			NOT NULL REFERENCES company(id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	address_id		integer			NOT NULL REFERENCES address(id)
		ON DELETE NO ACTION ON UPDATE CASCADE,
	main			boolean			NOT NULL DEFAULT false,
	phone			varchar(12)		NULL,
	UNIQUE (company_id, address_id),
	UNIQUE (company_id, main)
);

CREATE SEQUENCE role_seq;

CREATE TABLE role (
	id				integer DEFAULT nextval('role_seq') NOT NULL PRIMARY KEY,
    person_id		integer			NULL REFERENCES person(id)
		ON DELETE NO ACTION ON UPDATE CASCADE,
	branch_id		integer			NOT NULL REFERENCES branch(id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	is_manager		boolean			NOT NULL DEFAULT false,
	activity		varchar(30)		NULL,
	UNIQUE (person_id, branch_id)
);
