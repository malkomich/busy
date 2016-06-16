/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database rollback for the feature about management of service types
 */

DELETE FROM booking;

DELETE FROM schedule;
DELETE FROM service;
DELETE FROM service_type;

DELETE FROM role;
DELETE FROM branch;
DELETE FROM company;
DELETE FROM category;

DELETE FROM notification;
DELETE FROM verification;
DELETE FROM person;

DELETE FROM address;
DELETE FROM city;
DELETE FROM country;