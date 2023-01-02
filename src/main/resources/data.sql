INSERT INTO usermgmtsystem.role (role_name , role_description) 
  SELECT 'Admin','This is Admin Role' FROM DUAL
WHERE NOT EXISTS 
  (SELECT role_name FROM usermgmtsystem.role WHERE role_name='Admin');



INSERT INTO usermgmtsystem.user (user_first_name, user_last_name, user_password, user_name) 
  SELECT 'admin','admin','$2a$10$Uvo/2eQVKW4xrhfbyUJ9ZuxR7l6esA0B/ujISqhqaJ/yrIU/5si/u','admin' FROM DUAL
WHERE NOT EXISTS 
  (SELECT user_name FROM usermgmtsystem.user WHERE user_name='admin');




  INSERT INTO usermgmtsystem.user_role (user_id , role_id) 
  SELECT 'admin','Admin' FROM DUAL
WHERE NOT EXISTS 
  (SELECT user_id FROM usermgmtsystem.user_role WHERE user_id='admin');