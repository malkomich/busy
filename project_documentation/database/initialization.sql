/* 
 * Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database initialization with the basic data which has to be stored by an admin
 */

INSERT INTO country(name, code) VALUES('España', 'ES');
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));