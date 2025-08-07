CREATE INDEX index_people_surname ON Н_ЛЮДИ USING HASH (ФАМИЛИЯ);
CREATE INDEX index_studying_nzk ON Н_ОБУЧЕНИЯ USING HASH (НЗК);
CREATE INDEX index_studying_humanid ON Н_ОБУЧЕНИЯ (ЧЛВК_ИД);
CREATE INDEX index_students_id ON Н_УЧЕНИКИ (ИД);