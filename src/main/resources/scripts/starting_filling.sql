INSERT INTO roles (role_name) VALUES ('administrator'), ('moderator'), ('speaker'), ('user');
INSERT INTO users (role_id, login, password) VALUES (1,'root','root');
INSERT INTO confs (conf_name, conf_place,confDate) VALUES('Kiev Java Epam Open', 'Kiev, Kudryashova', 180518180000);
INSERT INTO reports (conf_id, report_name, report_desk) VALUES (1,'Java Fx', 'Report about Java FX posiibilities');
INSERT INTO reports (conf_id, report_name, report_desk) VALUES (1,'Reactive Java', 'Report about Java FX posiibilities');
INSERT INTO reports (conf_id, report_name, report_desk) VALUES (1,'JIT compilation', 'Report about JIT');
INSERT INTO reports (conf_id, report_name, report_desk) VALUES (1,'Jmm jdk 9', 'Report jmm at JDK 9');