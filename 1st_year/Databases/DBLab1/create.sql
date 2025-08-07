CREATE TABLE satellites (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	radius DECIMAL NOT NULL,
     CHECK(radius > 0),
	weight DECIMAL NOT NULL,
     CHECK(weight > 0),
	planet_id INTEGER NOT NULL
);

CREATE TABLE planets (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	radius DECIMAL NOT NULL, 
     CHECK(radius > 0),
	weight DECIMAL NOT NULL,
     CHECK(weight > 0),
	life_existence BOOLEAN NOT NULL
);

CREATE TABLE destinations (
	id SERIAL PRIMARY KEY,
	planet_id INTEGER,
	satellite_id INTEGER
);

CREATE TABLE missions (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	destination_id INTEGER NOT NULL,
	spaceship_id INTEGER NOT NULL,
	duration INTERVAL NOT NULL
);

CREATE TABLE spaceships (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	crew_capacity INTEGER NOT NULL,
     CHECK(crew_capacity >= 0),
	carrying_capacity DECIMAL NOT NULL,
     CHECK(carrying_capacity >= 0),
	engine_power DECIMAL NOT NULL,
     CHECK(engine_power > 0)
);

ALTER TABLE satellites
ADD FOREIGN KEY(planet_id) REFERENCES planets(id)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE destinations
ADD FOREIGN KEY(planet_id) REFERENCES planets(id)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE destinations
ADD FOREIGN KEY(satellite_id) REFERENCES satellites(id)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE missions
ADD FOREIGN KEY(destination_id) REFERENCES destinations(id)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE missions
ADD FOREIGN KEY(spaceship_id) REFERENCES spaceships(id)
ON UPDATE CASCADE ON DELETE CASCADE;
