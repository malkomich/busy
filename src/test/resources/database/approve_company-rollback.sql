/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database rollback for the feature of verifying a new company
 */

DELETE FROM notification;

DELETE FROM role;
DELETE FROM branch;
DELETE FROM company;
DELETE FROM category;

DELETE FROM address;
DELETE FROM city;
DELETE FROM country;

DELETE FROM person;