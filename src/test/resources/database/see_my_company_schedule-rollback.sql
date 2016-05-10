/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database rollback for the feature of show the company schedule
 */

DELETE FROM booking;

DELETE FROM service;
DELETE FROM service_type;

DELETE FROM role;
DELETE FROM branch;
DELETE FROM company;
DELETE FROM category;

DELETE FROM address;
DELETE FROM city;
DELETE FROM country;

DELETE FROM person;