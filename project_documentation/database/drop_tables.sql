/* 
 * Busy 
 * Author: Juan Carlos González Cabrero
 *
 * Database tables' dropping
 */

DROP TABLE IF EXISTS booking;

DROP TABLE IF EXISTS schedule;
DROP SEQUENCE IF EXISTS schedule_seq;

DROP TABLE IF EXISTS time_slot;
DROP SEQUENCE IF EXISTS time_slot_seq;

DROP TABLE IF EXISTS service;
DROP SEQUENCE IF EXISTS service_seq;

DROP TABLE IF EXISTS service_type;
DROP SEQUENCE IF EXISTS service_type_seq;

DROP TABLE IF EXISTS notification;
DROP SEQUENCE IF EXISTS notification_seq;

DROP TABLE IF EXISTS role;
DROP SEQUENCE IF EXISTS role_seq;

DROP TABLE IF EXISTS branch;
DROP SEQUENCE IF EXISTS branch_seq;

DROP TABLE IF EXISTS company;
DROP SEQUENCE IF EXISTS company_seq;

DROP TABLE IF EXISTS category;
DROP SEQUENCE IF EXISTS category_seq;

DROP TABLE IF EXISTS verification;

DROP TABLE IF EXISTS person;
DROP SEQUENCE IF EXISTS person_seq;

DROP TABLE IF EXISTS address;
DROP SEQUENCE IF EXISTS address_seq;

DROP TABLE IF EXISTS city;
DROP SEQUENCE IF EXISTS city_seq;

DROP TABLE IF EXISTS country;
DROP SEQUENCE IF EXISTS country_seq;
