/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database prepare for the feature of blocking a user
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Admin', 'Admin', 'admin@domain.x', 'pass', true, true);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Usuario1', 'Apellidos1', 'user1@domain.x', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Usuario2', 'Apellidos2', 'user2@domain.x', 'pass', true, false);