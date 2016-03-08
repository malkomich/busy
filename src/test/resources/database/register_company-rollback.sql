/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database rollback for the feature of registering a new company
 */

DELETE FROM notification;

DELETE FROM role;
DELETE FROM branch;
DELETE FROM address;
DELETE FROM city;
DELETE FROM country;
DELETE FROM company;
DELETE FROM category;

DELETE FROM person;