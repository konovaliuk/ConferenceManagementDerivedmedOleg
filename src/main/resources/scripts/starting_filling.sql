INSERT INTO roles (role_name) VALUES ('administrator'), ('moderator'), ('speaker'), ('user');
INSERT INTO users (roleId, email, login, password) VALUES (1, 'root@root.com', 'root', 'root');
INSERT INTO users (roleId, email, login, password) VALUES (2, 'moder@m.com', 'moderator one', '1');
INSERT INTO users (roleId, email, login, password) VALUES (2, 'moder2@m.com', 'moderator two', '1');
INSERT INTO users (roleId, email, login, password) VALUES (3, 'speaker@s.com', 'speaker one', '1');
INSERT INTO users (roleId, email, login, password) VALUES (3, 'speaker2@s.com', 'speaker two', '1');
INSERT INTO users (roleId, email, login, password) VALUES (3, 'speaker3@s.com', 'speaker three', '1');
INSERT INTO users (roleId, email, login, password) VALUES (3, 'speaker4@s.com', 'speaker four', '1');
insert into users (roleId, email, login, password) values (4, 'user@u.com', 'user one', '1');
INSERT INTO confs (conf_name, conf_place, confDate) VALUES ('Kiev Java Epam Open', 'Kiev, Kudryashova', 180518180000);
INSERT INTO confs (conf_name, conf_place, confDate) values ('Upcoming conference 1', 'Kiev', 180618180000);
INSERT INTO reports (conf_id, reportName, report_desk) VALUES (1, 'Java Fx', 'Report about Java FX posiibilities');
INSERT INTO reports (conf_id, reportName, report_desk)
VALUES (1, 'Reactive Java', 'Report about Java FX posiibilities');
INSERT INTO reports (conf_id, reportName, report_desk) VALUES (1, 'JIT compilation', 'Report about JIT');
INSERT INTO reports (conf_id, reportName, report_desk) VALUES (1, 'Jmm jdk 9', 'Report jmm at JDK 9');
insert into reports (conf_id, reportName, report_desk) values (2, 'Upcoming report 2', 'report description');
insert into reports (conf_id, reportName, report_desk) values (2, 'Upcoming report 3', 'report description');
insert into reports (conf_id, reportName, report_desk) values (2, 'Upcoming report 4', 'report description');
insert into reports (conf_id, reportName, report_desk) values (2, 'Upcoming report 5', 'report description');
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (4, 6, TRUE, FALSE, FALSE, TRUE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (6, 5, TRUE, FALSE, FALSE, TRUE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (7, 7, TRUE, FALSE, FALSE, TRUE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (7, 1, TRUE, FALSE, FALSE, TRUE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (4, 2, TRUE, FALSE, FALSE, TRUE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (5, 3, TRUE, FALSE, FALSE, TRUE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (6, 4, TRUE, FALSE, FALSE, TRUE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (8, 2, FALSE , FALSE, FALSE, FALSE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (8, 3, FALSE , FALSE, FALSE, FALSE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (8, 4, FALSE , FALSE, FALSE, FALSE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (2, 2, FALSE , FALSE, FALSE, FALSE);
insert INTO users_reports (user_id, report_id, active_speaker, by_speaker, by_moder, confirmed)
VALUES (3, 4, FALSE , FALSE, FALSE, FALSE);