/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database prepare for signup feature
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Prueba', 'Prueba', 'prueba@prueba.com', 'prueba', false, false);
INSERT INTO registry(person_id, confirmation_key) VALUES( (SELECT id FROM person WHERE email='prueba@prueba.com'), 'IDPRUEBA');

INSERT INTO country(name, code) VALUES('España', 'ES');
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));