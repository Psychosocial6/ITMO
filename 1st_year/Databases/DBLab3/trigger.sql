CREATE OR REPLACE FUNCTION check_satellite_radius() 
RETURNS TRIGGER AS $$ 
BEGIN 
IF (SELECT radius FROM satellites WHERE id = NEW.satellite_id) < 1e6 
THEN 
RAISE EXCEPTION 'Satellite is too small for landing'; 
END IF; 
RETURN NEW; 
END; 
$$ LANGUAGE plpgsql; 
CREATE TRIGGER before_destination_insert_or_update 
BEFORE INSERT OR UPDATE ON destinations 
FOR EACH ROW 
EXECUTE FUNCTION check_satellite_radius();