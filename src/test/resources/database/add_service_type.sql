/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database script to add a new service type
 */

INSERT INTO service_type(name, description, bookings_per_role, duration, company_id) VALUES('Tipo 1', 'Un tipo de servicio', 2, 60, (SELECT id FROM company WHERE cif='B12345678'));