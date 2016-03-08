/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database prepare for the feature of registering a new company
 */
	
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Nombre', 'Apellidos', 'user@domain.com', 'pass', true, false);

INSERT INTO country(name, code) VALUES('España', 'ES');
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));
INSERT INTO category(name) VALUES('Mobiliario');