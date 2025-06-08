ALTER TABLE tasks
ADD COLUMN userid INT,
ADD FOREIGN KEY (userid) REFERENCES todo_users(id);