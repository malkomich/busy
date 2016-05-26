/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database script to add some roles
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Juan', 'X', 'juan@domain.x', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Carlos', 'X', 'carlos@domain.x', 'pass', true, false);

INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='juan@domain.x'), (SELECT id FROM branch WHERE phone='902202122'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='carlos@domain.x'), (SELECT id FROM branch WHERE phone='902202122'), true);