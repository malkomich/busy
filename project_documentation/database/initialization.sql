/* 
 * Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database initialization with the basic data to check the finished user stories
 */

SET CLIENT_ENCODING TO 'UTF-8';

INSERT INTO country(name, code) VALUES('España', 'ES');

/* table city */
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('León', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Burgos', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Ávila', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Segovia', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Palencia', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Soria', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Zamora', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Salamanca', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Sevilla', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Málaga', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Cádiz', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Madrid', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Barcelona', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Valencia', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Albacete', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Gijón', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Oviedo', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Logroño', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Vigo', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Santander', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('San Sebastián', (SELECT id FROM country WHERE code='ES'));
INSERT INTO city(name, country_id) VALUES('Ciudad Real', (SELECT id FROM country WHERE code='ES'));

/* table address */
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Ancha', '2, 1ºC', '45023', (SELECT id FROM city WHERE name='León'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Toreros', '3, s/n', '47007', (SELECT id FROM city WHERE name='Valladolid'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Lirios', '25, 3ºA', '48004', (SELECT id FROM city WHERE name='Burgos'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Pelícano', '9, 5ºD', '46030', (SELECT id FROM city WHERE name='Ávila'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle de la Brisa', '17, Bajo-A', '46903', (SELECT id FROM city WHERE name='Ávila'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Berdugo', '43, 6ºC', '43114', (SELECT id FROM city WHERE name='Segovia'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Peral', '2, 1ºD', '43018', (SELECT id FROM city WHERE name='Segovia'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Plaza de los Álamos', '90, Bajo-B', '43513', (SELECT id FROM city WHERE name='Segovia'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Plaza del Cantero', '54, 1ºA', '42670', (SELECT id FROM city WHERE name='Palencia'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle de la Ruta', '30, Bajo-B', '42910', (SELECT id FROM city WHERE name='Palencia'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Mayor', '6, 2ºD', '40231', (SELECT id FROM city WHERE name='Soria'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Membrillo', '75, Bajo-A', '40210', (SELECT id FROM city WHERE name='Soria'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Paseo de los Barrenderos', '2, 1ºC', '40207', (SELECT id FROM city WHERE name='Soria'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Grande', '78, 3ºE', '49014', (SELECT id FROM city WHERE name='Zamora'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Manzano', '5, 1ºC', '49510', (SELECT id FROM city WHERE name='Zamora'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Cero', '8, 3ºA', '49511', (SELECT id FROM city WHERE name='Zamora'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Alegre', '7, Bajo-A', '49201', (SELECT id FROM city WHERE name='Zamora'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Amadeus', '24, 5ºB', '41320', (SELECT id FROM city WHERE name='Salamanca'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Alta', '52, 1ºA', '41321', (SELECT id FROM city WHERE name='Salamanca'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle del Vértigo', '4, 6ºB', '41322', (SELECT id FROM city WHERE name='Salamanca'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Mar', '18, 2ºA', '41323', (SELECT id FROM city WHERE name='Salamanca'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Plaza de los Castaños', '3, Bajo-B', '41510', (SELECT id FROM city WHERE name='Salamanca'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Paseo de Miraflores', '35, 6ºA', '21408', (SELECT id FROM city WHERE name='Sevilla'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Guadalquivir', '45, 4ºB', '21405', (SELECT id FROM city WHERE name='Sevilla'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Paseo de Vista Alegre', '130, 1ºA', '21406', (SELECT id FROM city WHERE name='Sevilla'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle de la Concha', '5, 2ºB', '21407', (SELECT id FROM city WHERE name='Sevilla'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle de la Oliva', '43, 1ºC', '24015', (SELECT id FROM city WHERE name='Málaga'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Barbero', '8, Bajo.B', '24030', (SELECT id FROM city WHERE name='Málaga'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Pequeña', '12, 4ªE', '23508', (SELECT id FROM city WHERE name='Cádiz'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle del Jazmín', '42, 1ªF', '23620', (SELECT id FROM city WHERE name='Cádiz'));

INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Plaza de los Pilares', '72, Bajo-A', '23421', (SELECT id FROM city WHERE name='Cádiz'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Paseo de la Castellana', '101, 8ºE', '30129', (SELECT id FROM city WHERE name='Madrid'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Carretera de la Esperanza', '140, 9ºD', '30127', (SELECT id FROM city WHERE name='Madrid'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Palacio de Miramar', '110, 7ºC', '30128', (SELECT id FROM city WHERE name='Madrid'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle de las Nueces', '41, 5ºD', '30130', (SELECT id FROM city WHERE name='Madrid'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Pingüino', '8, Bajo-C', '30847', (SELECT id FROM city WHERE name='Madrid'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Antoni Gaudí', '18, 2ºA', '13067', (SELECT id FROM city WHERE name='Barcelona'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Aurora', '158, 5ºB', '13068', (SELECT id FROM city WHERE name='Barcelona'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Filipinas', '4, 3ºB', '13069', (SELECT id FROM city WHERE name='Barcelona'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Becquer', '29, 6ºB', '13070', (SELECT id FROM city WHERE name='Barcelona'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Plaza de las Torres', '32, 4ºB', '13071', (SELECT id FROM city WHERE name='Barcelona'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Miguel de Cervantes', '89, Bajo-A', '13143', (SELECT id FROM city WHERE name='Barcelona'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Camino del Botafumeiro', '143, 4ºB', '58763', (SELECT id FROM city WHERE name='Valencia'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle del Riego', '47, 1ºD', '58764', (SELECT id FROM city WHERE name='Valencia'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Paseo de las Fallas', '165, Bajo-B', '58618', (SELECT id FROM city WHERE name='Valencia'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Plaza de la Brasa', '3, 1ºA', '75087', (SELECT id FROM city WHERE name='Albacete'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Luna', '9, Bajo-A', '75503', (SELECT id FROM city WHERE name='Albacete'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Tomillo', '52, 5ºB', '09803', (SELECT id FROM city WHERE name='Gijón'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Antonio Machado', '28, Bajo-B', '09912', (SELECT id FROM city WHERE name='Gijón'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Gascona', '28, 6ºB', '08572', (SELECT id FROM city WHERE name='Oviedo'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Sin Rastro', '5, 5ºC', '12068', (SELECT id FROM city WHERE name='Logroño'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Paloma', '68, Bajo-A', '12141', (SELECT id FROM city WHERE name='Logroño'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Chica', '3, 2ºD', '32658', (SELECT id FROM city WHERE name='Vigo'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Ávila', '51, Bajo-C', '32691', (SELECT id FROM city WHERE name='Vigo'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Paseo de los almendros', '71, 1ºD', '31766', (SELECT id FROM city WHERE name='Santander'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle de la Culebra', '13, Bajo-D', '31792', (SELECT id FROM city WHERE name='Santander'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Lavanda', '6, 3ºA', '37683', (SELECT id FROM city WHERE name='San Sebastián'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Plaza del Duque', '49, 6ºD', '37521', (SELECT id FROM city WHERE name='San Sebastián'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle de los Bígaros', '67, Bajo-D', '37258', (SELECT id FROM city WHERE name='San Sebastián'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Alegre', '23, 2ºB', '72458', (SELECT id FROM city WHERE name='Ciudad Real'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Paseo de la Libertad', '35, Bajo-B', '72530', (SELECT id FROM city WHERE name='Ciudad Real'));

/* table person */
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Jose', 'García', 'admin@domain.x', 'pass', '71122343C', '654321987', true, true, (SELECT id FROM address WHERE zip_code='45023'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Sara', 'Fuentes', 'user_inactive_1@domain.x', 'pass', '01976578X', '616587498', false, false, (SELECT id FROM address WHERE zip_code='47007'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Cristian', 'Pérez', 'user_inactive_2@domain.x', 'pass', '71458632V', '695886325', false, false, (SELECT id FROM address WHERE zip_code='48004'));

INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Juan', 'González', 'jefe_pelu@domain.x', 'pass', '85214635X', '611402307', true, false, (SELECT id FROM address WHERE zip_code='40231'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Blanca', 'Salgado', 'empleado_pelu_1@domain.x', 'pass', '01352645G', '630402153', true, false, (SELECT id FROM address WHERE zip_code='42670'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('María', 'Gonzalo', 'empleado_pelu_2@domain.x', 'pass', '04253191X', '628931654', true, false, (SELECT id FROM address WHERE zip_code='40207'));

INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Carlos', 'Cabrero', 'jefe_academia@domain.x', 'pass', '01235490M', '684572105', true, false, (SELECT id FROM address WHERE zip_code='42670'));

INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Mario', 'Gómez', 'jefe_auto_1@domain.x', 'pass', '75894127A', '645870106', true, false, (SELECT id FROM address WHERE zip_code='46030'));

INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Raúl', 'Castro', 'jefe_auto_2@domain.x', 'pass', '74485652C', '697889546', true, false, (SELECT id FROM address WHERE zip_code='43114'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Rodrigo', 'Díaz', 'empleado_auto2_1@domain.x', 'pass', '73156932W', '651287063', true, false, (SELECT id FROM address WHERE zip_code='43018'));



INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Jaime', 'Gil', 'jefe_coches_1@domain.x', 'pass', '78435079H', '643279786', true, false, (SELECT id FROM address WHERE zip_code='49014'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Adrián', 'Valera', 'empleado_coches_1_1@domain.x', 'pass', '02519630H', '630259874', true, false, (SELECT id FROM address WHERE zip_code='49510'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Oscar', 'Martín', 'empleado_coches_1_2@domain.x', 'pass', '72369584F', '612362014', true, false, (SELECT id FROM address WHERE zip_code='49511'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Miguel', 'Cuellar', 'jefe_coches_2@domain.x', 'pass', '71509133J', '914520364', true, false, (SELECT id FROM address WHERE zip_code='41320'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Jesús', 'Álvarez', 'empleado_coches_2_1@domain.x', 'pass', '11103624C', '631015965', true, false, (SELECT id FROM address WHERE zip_code='41321'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Roberto', 'Gómez', 'empleado_coches_2_2@domain.x', 'pass', '74216530F', '631569875', true, false, (SELECT id FROM address WHERE zip_code='41322'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Alberto', 'Fernández', 'empleado_coches_2_3@domain.x', 'pass', '75239561G', '635984102', true, false, (SELECT id FROM address WHERE zip_code='41323'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Mateo', 'Gil', 'jefe_coches_3@domain.x', 'pass', '79605142G', '630298553', true, false, (SELECT id FROM address WHERE zip_code='21408'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Ramón', 'Rodriguez', 'empleado_coches_3_1@domain.x', 'pass', '41236205I', '695310478', true, false, (SELECT id FROM address WHERE zip_code='21405'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('César', 'López', 'empleado_coches_3_2@domain.x', 'pass', '72230196B', '613045871', true, false, (SELECT id FROM address WHERE zip_code='21406'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Enrique', 'Cubero', 'empleado_coches_3_3@domain.x', 'pass', '44325665F', '633562112', true, false, (SELECT id FROM address WHERE zip_code='21407'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Roberto', 'Marcos', 'jefe_coches_4@domain.x', 'pass', '00148563F', '602310485', true, false, (SELECT id FROM address WHERE zip_code='24015'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Julio', 'Cabrero', 'empleado_coches_4_1@domain.x', 'pass', '45213695R', '629196240', true, false, (SELECT id FROM address WHERE zip_code='23508'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Gonzalo', 'Mena', 'empleado_coches_4_2@domain.x', 'pass', '02358411I', '663058491', true, false, (SELECT id FROM address WHERE zip_code='23620'));

INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Cristina', 'Barajas', 'jefe_fisio_1@domain.x', 'pass', '08959632Q', '630898631', true, false, (SELECT id FROM address WHERE zip_code='30129'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Sonia', 'Puerro', 'empleado_fisio_1_1@domain.x', 'pass', '78964124A', '644206593', true, false, (SELECT id FROM address WHERE zip_code='30127'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Elvira', 'Lindo', 'empleado_fisio_1_2@domain.x', 'pass', '42309452I', '633639521', true, false, (SELECT id FROM address WHERE zip_code='30128'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Jairo', 'Esteban', 'empleado_fisio_1_3@domain.x', 'pass', '70965984Ñ', '688206413', true, false, (SELECT id FROM address WHERE zip_code='30130'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Sara', 'Laso', 'jefe_fisio_2@domain.x', 'pass', '11659350P', '986301482', true, false, (SELECT id FROM address WHERE zip_code='13067'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Silvia', 'Flaco', 'empleado_fisio_2_1@domain.x', 'pass', '15302984L', '688521461', true, false, (SELECT id FROM address WHERE zip_code='13068'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Alba', 'Crespo', 'empleado_fisio_2_2@domain.x', 'pass', '51120487J', '666501764', true, false, (SELECT id FROM address WHERE zip_code='13069'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Silvia', 'Flaco', 'empleado_fisio_2_3@domain.x', 'pass', '60145120K', '657998851', true, false, (SELECT id FROM address WHERE zip_code='13070'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Silvia', 'Flaco', 'empleado_fisio_2_4@domain.x', 'pass', '11145127H', '636559857', true, false, (SELECT id FROM address WHERE zip_code='13071'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Francisco', 'López', 'jefe_fisio_3@domain.x', 'pass', '79965419T', '633559621', true, false, (SELECT id FROM address WHERE zip_code='58763'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Laura', 'de Lázaro', 'empleado_fisio_3_1@domain.x', 'pass', '10659632K', '651857921', true, false, (SELECT id FROM address WHERE zip_code='58764'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Inés', 'Arbeloa', 'jefe_fisio_4@domain.x', 'pass', '75210567E', '924629878', true, false, (SELECT id FROM address WHERE zip_code='75087'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('', '', 'empleado_fisio_4_1@domain.x', 'pass', '', '', true, false, (SELECT id FROM address WHERE zip_code=''));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Sergio', 'Muñoz', 'jefe_fisio_5@domain.x', 'pass', '44639502S', '964830578', true, false, (SELECT id FROM address WHERE zip_code='09803'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Álvaro', 'Ortega', 'empleado_fisio_5_1@domain.x', 'pass', '41306982V', '962301452', true, false, (SELECT id FROM address WHERE zip_code='08572'));

INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Beatriz', 'González', 'jefe_dientes_1@domain.x', 'pass', '19605327Y', '627978625', true, false, (SELECT id FROM address WHERE zip_code='12068'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('', '', 'empleado_dientes_1_1@domain.x', 'pass', '32645288T', '633533433', true, false, (SELECT id FROM address WHERE zip_code=''));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Alicia', 'Diez', 'jefe_dientes_2@domain.x', 'pass', '23679051K', '987639441', true, false, (SELECT id FROM address WHERE zip_code='32658'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('', '', 'empleado_dientes_2_1@domain.x', 'pass', '16372823D', '667199121', true, false, (SELECT id FROM address WHERE zip_code=''));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Soraya', 'De la Fuente', 'jefe_dientes_3@domain.x', 'pass', '1635978H', '600598732', true, false, (SELECT id FROM address WHERE zip_code='31766'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('', '', 'empleado_dientes_3_1@domain.x', 'pass', '11992738C', '634443343', true, false, (SELECT id FROM address WHERE zip_code=''));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Juan', 'Fernández', 'jefe_dientes_4@domain.x', 'pass', '75596127A', '951639780', true, false, (SELECT id FROM address WHERE zip_code='37683'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Enrique', 'Bravo', 'empleado_dientes_4_1@domain.x', 'pass', '43145162P', '951852968', true, false, (SELECT id FROM address WHERE zip_code='37521'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('Paula', 'Alonso', 'jefe_dientes_5@domain.x', 'pass', '01152164B', '630930685',true, false, (SELECT id FROM address WHERE zip_code='72458'));
INSERT INTO person(first_name, last_name, email, password, nif, phone, active, admin_role, address_id) VALUES('', '', 'empleado_dientes_5_1@domain.x', 'pass', '22322113A', '677873647', true, false, (SELECT id FROM address WHERE zip_code=''));

/* table category */
INSERT INTO category(name) VALUES('Peluquería');
INSERT INTO category(name) VALUES('Academia');
INSERT INTO category(name) VALUES('Autoescuela');
INSERT INTO category(name) VALUES('Taller de vehículos');
INSERT INTO category(name) VALUES('Centro de fisioterapia');
INSERT INTO category(name) VALUES('Clínica dental');

/* table company */
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('El Corta Pelos', 'El Corta Pelos S.A.', 'peluqueria@domain.x', 'B12345678', true, (SELECT id FROM category WHERE name='Peluquería'));
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('AprendeMás', 'AprendeMás S.A.', 'academia@domain.x', 'B12341379', true, (SELECT id FROM category WHERE name='Academia'));
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Autoescuela Guillen', 'Autoescuela Guillen S.A.', 'autoescuela@domain.x', 'B68342958', true, (SELECT id FROM category WHERE name='Autoescuela'));
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Autoescuela Villa del Campo', 'Autoescuela Villa del Campo S.A.', 'autoescuela1@domain.x', 'B68345983', true, (SELECT id FROM category WHERE name='Autoescuela'));
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Talleres Jose', 'Talleres Jose S.A.', 'taller@domain.x', 'B45345891', true, (SELECT id FROM category WHERE name='Taller de vehículos'));
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Fisioterapia Cristina Barajas', 'Centro de fisioterapia Cristina Barajas S.A.', 'fisioterapia1@domain.x', 'B56867314', true, (SELECT id FROM category WHERE name='Centro de fisioterapia'));
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Clínica Dental Dientecines', 'Clínica dental Dientecines S.A.', 'dental@domain.x', 'B15683215', true, (SELECT id FROM category WHERE name='Clínica dental'));

/* table branch */
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='peluqueria@domain.x'), (SELECT id FROM address WHERE zip_code='40210'), true, '902202122');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='academia@domain.x'), (SELECT id FROM address WHERE zip_code='42910'), true, '902963123');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='autoescuela@domain.x'), (SELECT id FROM address WHERE zip_code='46903'), true, '902294124');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='autoescuela1@domain.x'), (SELECT id FROM address WHERE zip_code='43513'), false, '902209825');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='taller@domain.x'), (SELECT id FROM address WHERE zip_code='49201'), true, '902296126');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='taller@domain.x'), (SELECT id FROM address WHERE zip_code='41510'), false, '902296129');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='taller@domain.x'), (SELECT id FROM address WHERE zip_code='24030'), false, '902296130');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='taller@domain.x'), (SELECT id FROM address WHERE zip_code='23421'), false, '902296132');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='fisioterapia1@domain.x'), (SELECT id FROM address WHERE zip_code='30847'), true, '902874127');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='fisioterapia1@domain.x'), (SELECT id FROM address WHERE zip_code='13143'), false, '902874136');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='fisioterapia1@domain.x'), (SELECT id FROM address WHERE zip_code='58618'), false, '902874142');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='fisioterapia1@domain.x'), (SELECT id FROM address WHERE zip_code='75503'), false, '902874125');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='fisioterapia1@domain.x'), (SELECT id FROM address WHERE zip_code='09912'), false, '902874128');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='dental@domain.x'), (SELECT id FROM address WHERE zip_code='12141'), true, '902287128');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='dental@domain.x'), (SELECT id FROM address WHERE zip_code='32691'), false, '902287115');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='dental@domain.x'), (SELECT id FROM address WHERE zip_code='31792'), false, '902287117');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='dental@domain.x'), (SELECT id FROM address WHERE zip_code='37258'), false, '902287136');
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='dental@domain.x'), (SELECT id FROM address WHERE zip_code='72530'), false, '902287161');

/* table role */
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_pelu@domain.x'), (SELECT id FROM branch WHERE phone='902202122'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_pelu_1@domain.x'), (SELECT id FROM branch WHERE phone='902202122'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_pelu_2@domain.x'), (SELECT id FROM branch WHERE phone='902202122'), false);

INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_academia@domain.x'), (SELECT id FROM branch WHERE phone='902963123'), true);

INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_auto_1@domain.x'), (SELECT id FROM branch WHERE phone='902294124'), true);

INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_auto_2@domain.x'), (SELECT id FROM branch WHERE phone='902209825'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_auto2_1@domain.x'), (SELECT id FROM branch WHERE phone='902209825'), false);

INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_coches_1@domain.x'), (SELECT id FROM branch WHERE phone='902296126'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_1_1@domain.x'), (SELECT id FROM branch WHERE phone='902296126'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_1_2@domain.x'), (SELECT id FROM branch WHERE phone='902296126'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_coches_2@domain.x'), (SELECT id FROM branch WHERE phone='902296129'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_2_1@domain.x'), (SELECT id FROM branch WHERE phone='902296129'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_2_2@domain.x'), (SELECT id FROM branch WHERE phone='902296129'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_2_3@domain.x'), (SELECT id FROM branch WHERE phone='902296129'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_coches_3@domain.x'), (SELECT id FROM branch WHERE phone='902296130'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_3_1@domain.x'), (SELECT id FROM branch WHERE phone='902296130'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_3_2@domain.x'), (SELECT id FROM branch WHERE phone='902296130'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_3_3@domain.x'), (SELECT id FROM branch WHERE phone='902296130'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_coches_4@domain.x'), (SELECT id FROM branch WHERE phone='902296132'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_4_1@domain.x'), (SELECT id FROM branch WHERE phone='902296132'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_coches_4_2@domain.x'), (SELECT id FROM branch WHERE phone='902296132'), false);

INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_fisio_1@domain.x'), (SELECT id FROM branch WHERE phone='902874127'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_1_1@domain.x'), (SELECT id FROM branch WHERE phone='902874127'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_1_2@domain.x'), (SELECT id FROM branch WHERE phone='902874127'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_1_3@domain.x'), (SELECT id FROM branch WHERE phone='902874127'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_fisio_2@domain.x'), (SELECT id FROM branch WHERE phone='902874136'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_2_1@domain.x'), (SELECT id FROM branch WHERE phone='902874136'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_2_2@domain.x'), (SELECT id FROM branch WHERE phone='902874136'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_2_3@domain.x'), (SELECT id FROM branch WHERE phone='902874136'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_2_4@domain.x'), (SELECT id FROM branch WHERE phone='902874136'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_fisio_3@domain.x'), (SELECT id FROM branch WHERE phone='902874142'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_3_1@domain.x'), (SELECT id FROM branch WHERE phone='902874142'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_fisio_4@domain.x'), (SELECT id FROM branch WHERE phone='902874125'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_4_1@domain.x'), (SELECT id FROM branch WHERE phone='902874125'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_fisio_5@domain.x'), (SELECT id FROM branch WHERE phone='902874128'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_fisio_5_1@domain.x'), (SELECT id FROM branch WHERE phone='902874128'), false);

INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_dientes_1@domain.x'), (SELECT id FROM branch WHERE phone='902287128'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_dientes_1_1@domain.x'), (SELECT id FROM branch WHERE phone='902287128'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_dientes_2@domain.x'), (SELECT id FROM branch WHERE phone='902287115'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_dientes_2_1@domain.x'), (SELECT id FROM branch WHERE phone='902287115'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_dientes_3@domain.x'), (SELECT id FROM branch WHERE phone='902287117'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_dientes_3_1@domain.x'), (SELECT id FROM branch WHERE phone='902287117'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_dientes_4@domain.x'), (SELECT id FROM branch WHERE phone='902287136'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_dientes_4_1@domain.x'), (SELECT id FROM branch WHERE phone='902287136'), false);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='jefe_dientes_5@domain.x'), (SELECT id FROM branch WHERE phone='902287161'), true);
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='empleado_dientes_5_1@domain.x'), (SELECT id FROM branch WHERE phone='902287161'), false);


/* table service type  */
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Corte Caballero', 'Creación corte para caballero', 1, (SELECT id FROM company WHERE trade_name='El Corta Pelos'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Afeita barba', 'Afeitado de barba con navaja', 1, (SELECT id FROM company WHERE trade_name='El Corta Pelos'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Peinado Señora', 'Lavado y peinado para señora ', 1, (SELECT id FROM company WHERE trade_name='El Corta Pelos')); 
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Corte señora', 'Lavado, corte y peinado para señora', 1, (SELECT id FROM company WHERE trade_name='El Corta Pelos'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Mechas señora', 'Mechas de plata', 1, (SELECT id FROM company WHERE trade_name='El Corta Pelos'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Tinte', 'Tinte completo', 1, (SELECT id FROM company WHERE trade_name='El Corta Pelos'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Manicura', 'Masaje de manos, arreglo de uñas y manicura', 1, (SELECT id FROM company WHERE trade_name='El Corta Pelos'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Pedicura', 'Masaje de pies, arreglo de uñas y pedicura', 1, (SELECT id FROM company WHERE trade_name='El Corta Pelos'));

INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Matemáticas', 'Clase teórica de Matemáticas', 2, (SELECT id FROM company WHERE trade_name='AprendeMás'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Inglés', 'Clase teórica de Inglés', 3, (SELECT id FROM company WHERE trade_name='AprendeMás'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Física', 'Clase teórica de Física', 2, (SELECT id FROM company WHERE trade_name='AprendeMás'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Lengua', 'Clase teórica de Lengua', 3, (SELECT id FROM company WHERE trade_name='AprendeMás'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Química', 'Clase teórica de Química', 3, (SELECT id FROM company WHERE trade_name='AprendeMás'));

INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Teoría general', 'Clase teórica de circulación vial', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Guillen'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Teoría moto', 'Clase teórica temario específico de motocicletas', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Guillen'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Test coche', 'Test de preparación examen teórico de circulación vial', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Guillen'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Test moto', 'Test de preparación examen teórico específico de motocicletas', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Guillen'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Práctica coche', 'Formación práctica de circulación permiso clase B', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Guillen'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Práctica destreza', 'Formación práctica de destreza permiso clase A2', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Guillen'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Práctica moto', 'Formación práctica de circulación permiso clase A2', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Guillen'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Práctica reciclaje', 'prácticas de reciclaje permiso clase B', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Guillen'));

INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Teoría general', 'Clase teórica de circulación vial', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Villa del Campo'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Teoría moto', 'Clase teórica temario específico de motocicletas', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Villa del Campo'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Test coche', 'Test de preparación examen teórico de circulación vial', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Villa del Campo'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Test moto', 'Test de preparación examen teórico específico de motocicletas', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Villa del Campo'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Práctica coche', 'Formación práctica de circulación permiso clase B', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Villa del Campo'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Práctica destreza', 'Formación práctica de destreza permiso clase A2', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Villa del Campo'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Práctica moto', 'Formación práctica de circulación permiso clase A2', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Villa del Campo'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Práctica reciclaje', 'prácticas de reciclaje permiso clase B', 1, (SELECT id FROM company WHERE trade_name='Autoescuela Villa del Campo'));

INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Cambio de aceite', 'Clase teórica de circulación vial', 1, (SELECT id FROM company WHERE trade_name='Talleres Jose'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Cambio de ruedas', 'Clase teórica de circulación vial', 1, (SELECT id FROM company WHERE trade_name='Talleres Jose'));
INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Revisión oficial', 'Revisión oficial y mantenimiento', 1, (SELECT id FROM company WHERE trade_name='Talleres Jose'));

/* table service */
INSERT INTO service(service_type_id, repetition_type) VALUES((SELECT id FROM service_type WHERE name='Cambio de aceite'), 0);
INSERT INTO service(service_type_id, repetition_type) VALUES((SELECT id FROM service_type WHERE name='Revisión oficial'), 1);

INSERT INTO time_slot(start_time, service_id) VALUES('2016-07-22 16:00:00', (SELECT id FROM service WHERE service_type_id=(SELECT id FROM service_type WHERE name='Cambio de aceite')));
INSERT INTO time_slot(start_time, service_id) VALUES('2016-07-26 13:00:00', (SELECT id FROM service WHERE service_type_id=(SELECT id FROM service_type WHERE name='Revisión oficial')));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-22 16:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296126') ORDER BY id ASC LIMIT 1));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-26 13:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296126') ORDER BY id ASC LIMIT 1));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-22 16:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296129') ORDER BY id ASC LIMIT 1));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-26 13:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296129') ORDER BY id ASC LIMIT 1));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-22 16:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296130') ORDER BY id ASC LIMIT 1));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-26 13:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296130') ORDER BY id ASC LIMIT 1));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-22 16:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296132') ORDER BY id ASC LIMIT 1));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-26 13:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296132') ORDER BY id ASC LIMIT 1));

/* */

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-22 16:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296126') ORDER BY id DESC LIMIT 1));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-26 13:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296126') ORDER BY id DESC LIMIT 1));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-22 16:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296129') ORDER BY id DESC LIMIT 1));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-26 13:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296129') ORDER BY id DESC LIMIT 1));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-22 16:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296130') ORDER BY id DESC LIMIT 1));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-26 13:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296130') ORDER BY id DESC LIMIT 1));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-22 16:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296132') ORDER BY id DESC LIMIT 1));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-07-26 13:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902296132') ORDER BY id DESC LIMIT 1));
