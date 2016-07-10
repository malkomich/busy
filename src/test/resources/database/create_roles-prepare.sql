/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database prepare for the feature about management of service types
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Jessy', 'Pinkman', 'busy.validation@gmail.com', 'pass', true, false);

INSERT INTO country(name, code) VALUES('España', 'ES');
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Mayor', null, '47005', (SELECT id FROM city WHERE name='Valladolid'));

INSERT INTO category(name) VALUES('Software');
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Busy', 'Busy S.A.', 'busy.validation@gmail.com', 'B12345678', true, (SELECT id FROM category WHERE name='Software'));
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE cif='B12345678'), (SELECT id FROM address WHERE address1='Calle Mayor'), true, '902202122');
