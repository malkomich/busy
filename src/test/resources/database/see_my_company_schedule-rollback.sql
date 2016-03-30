/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database rollback for the feature of searching a company by name
 */

DELETE FROM booking;

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