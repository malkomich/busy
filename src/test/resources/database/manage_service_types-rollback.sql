/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database rollback for the feature about management of service types
 */

DELETE FROM booking;

DELETE FROM service;
DELETE FROM service_type;

DELETE FROM hour_schedule;
DELETE FROM day_schedule;
DELETE FROM week_schedule;
DELETE FROM year_schedule;

DELETE FROM role;
DELETE FROM branch;
DELETE FROM company;
DELETE FROM category;

DELETE FROM address;
DELETE FROM city;
DELETE FROM country;

DELETE FROM person;