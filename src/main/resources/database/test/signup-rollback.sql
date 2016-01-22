/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database rollback for signup feature
 */

DELETE FROM person WHERE email = 'prueba@prueba.com';

DELETE FROM city WHERE name = 'Valladolid';
DELETE FROM country WHERE code = 'ES';