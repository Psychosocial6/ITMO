INSERT INTO planets (name, radius, weight, life_existence) VALUES ('Jupiter', 69911000, 18987E23, false); 
INSERT INTO planets (name, radius, weight, life_existence) VALUES ('Earth', 6378000, 5.9742E24, true);

INSERT INTO satellites (name, radius, weight, planet_id) VALUES ('Callisto', 2410.3E3, 1.08E23, 1);
INSERT INTO satellites (name, radius, weight, planet_id) VALUES ('Moon', 1737.4E3, 7.36E22, 2);
INSERT INTO satellites (name, radius, weight, planet_id) VALUES ('Sinope', 19E3, 7.5E16, 1);
INSERT INTO satellites (name, radius, weight, planet_id) VALUES ('Pasiphae', 58E3, 3E17, 1);
INSERT INTO satellites (name, radius, weight, planet_id) VALUES ('Leda', 20E3, 1.09E16, 1);

INSERT INTO destinations (planet_id, satellite_id) VALUES (null, 1);

INSERT INTO spaceships (name, crew_capacity, carrying_capacity, engine_power) VALUES ('Apollo', 3, 45578.12, 26895);

INSERT INTO missions (name, destination_id, spaceship_id, duration) VALUES ('Apollo-14', 1, 1, '3 years 2 months 24 days 6 hours 2 minutes 36 seconds');
