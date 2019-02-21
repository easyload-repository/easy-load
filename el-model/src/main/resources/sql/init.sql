# This sql file using for init testing data for this project

# insert el_activity
INSERT INTO easy_load.el_activity (activity_id, activity_description, activity_name, create_time, end_time, owner, start_time, update_time)
VALUES ('2c9c473167cac2450167cb73acc30000', NULL, 'kate', NULL, NULL, NULL, NULL, NULL);
INSERT INTO easy_load.el_activity (activity_id, activity_description, activity_name, create_time, end_time, owner, start_time, update_time)
VALUES ('2c9c473167cac2450167cb75cf170001', NULL, 'kate', NULL, NULL, NULL, NULL, NULL);
INSERT INTO easy_load.el_activity (activity_id, activity_description, activity_name, create_time, end_time, owner, start_time, update_time)
VALUES ('2c9c473167cac2450167cb7767530002', NULL, 'kate', NULL, NULL, NULL, NULL, NULL);
INSERT INTO easy_load.el_activity (activity_id, activity_description, activity_name, create_time, end_time, owner, start_time, update_time)
VALUES
  ('2c9c473167cb7d510167cb7d8b9c0000', NULL, 'kate', '2018-12-20 12:01:02', NULL, NULL, NULL, '2018-12-20 12:01:02');

# insert el_round
INSERT INTO easy_load.el_round (round_id, create_time, description, expected_end_time, owner, round_name, expected_start_time, status, times, update_time, version, activity_id, column_13, actually_end_time, actually_start_time, end_time, start_time)
VALUES ('4cecd1619aa04081b5ad93f24c8eea94', '2018-12-20 12:07:36', 'test', '2018-12-20 12:08:00', NULL, 'round-test-1',
                                            '2018-12-20 12:10:01', 'NEW', '1', '2018-12-20 12:14:10', 1,
        '2c9c473167cac2450167cb75cf170001', NULL, NULL, NULL, NULL, NULL);
INSERT INTO easy_load.el_round (round_id, create_time, description, expected_end_time, owner, round_name, expected_start_time, status, times, update_time, version, activity_id, actually_end_time, actually_start_time, end_time, start_time)
VALUES ('4cecd1619aa04081b5ad93f24c8eea95', '2018-12-20 12:07:36', 'test', '2018-12-20 12:08:00', NULL, 'round-test-2',
                                            '2018-12-20 12:10:01', 'READY', '1', '2018-12-20 12:14:10', 1,
        '2c9c473167cac2450167cb75cf170001', NULL, NULL, NULL, NULL);
INSERT INTO easy_load.el_round (round_id, create_time, description, expected_end_time, owner, round_name, expected_start_time, status, times, update_time, version, activity_id, actually_end_time, actually_start_time, end_time, start_time)
VALUES ('4cecd1619aa04081b5ad93f24c8eea96', '2018-12-20 12:07:36', 'test', '2018-12-20 12:08:00', NULL, 'round-test-3',
                                            '2018-12-20 12:10:01', 'IN_PROCESS', '1', '2018-12-20 12:14:10', 1,
        '2c9c473167cac2450167cb75cf170001', NULL, NULL, NULL, NULL);
INSERT INTO easy_load.el_round (round_id, create_time, description, expected_end_time, owner, round_name, expected_start_time, status, times, update_time, version, activity_id, actually_end_time, actually_start_time, end_time, start_time)
VALUES ('4cecd1619aa04081b5ad93f24c8eea97', '2018-12-20 12:07:36', 'test', '2018-12-20 12:08:00', NULL, 'round-test-4',
                                            '2018-12-20 12:10:01', 'COMPLETE', '1', '2018-12-20 12:14:10', 1,
        '2c9c473167cac2450167cb75cf170001', NULL, NULL, NULL, NULL);
INSERT INTO easy_load.el_round (round_id, create_time, description, expected_end_time, owner, round_name, expected_start_time, status, times, update_time, version, activity_id, actually_end_time, actually_start_time, end_time, start_time)
VALUES ('4cecd1619aa04081b5ad93f24c8eea98', '2018-12-20 12:07:36', 'test', '2018-12-20 12:08:00', NULL, 'round-test-5',
                                            '2018-12-20 12:10:01', 'ERROR', '1', '2018-12-20 12:14:10', 1,
        '2c9c473167cb7d510167cb7d8b9c0000', NULL, NULL, NULL, NULL);
# insert el_attender
INSERT INTO easy_load.el_attender (attender_id, create_time, domain, owner, update_time)
VALUES ('f2cc8a446e5c416baafcb8e42f2ac701', '2018-12-20 13:00:59', 'DOC', NULL, '2018-12-20 13:01:13');
INSERT INTO easy_load.el_attender (attender_id, create_time, domain, owner, update_time)
VALUES ('f2cc8a446e5c416baafcb8e42f2ac702', '2018-12-20 13:00:59', 'HRC', NULL, '2018-12-20 13:01:13');
INSERT INTO easy_load.el_attender (attender_id, create_time, domain, owner, update_time)
VALUES ('f2cc8a446e5c416baafcb8e42f2ac703', '2018-12-20 13:00:59', 'SHP', NULL, '2018-12-20 13:01:13');

# insert el_round_attender
INSERT INTO easy_load.el_round_attender (round_attender_id, create_time, health_check_status, running_status, update_time, attender_id, el_round_id)
VALUES ('0d56609fd2654dcf814412342f86c7c3', '2018-12-21 01:11:54', 'NEW', 'NEW', '2018-12-21 01:12:38',
        'f2cc8a446e5c416baafcb8e42f2ac701', '4cecd1619aa04081b5ad93f24c8eea94');


INSERT INTO easy_load.el_round_attender (round_attender_id, create_time, health_check_status, running_status, update_time, attender_id, el_round_id)
VALUES ('0d56609fd2654dcf814412342f86c7c4', '2018-12-21 01:11:54', 'NEW', 'NEW', '2018-12-21 01:12:38',
        'f2cc8a446e5c416baafcb8e42f2ac702', '4cecd1619aa04081b5ad93f24c8eea94');

INSERT INTO easy_load.el_script (script_id, create_time, execute_cmd, pacing, path, stop_cmd, update_time, v_user, server_id, type_id)
VALUES ('571542f10e3d4646a80b34fed3d3b6c5', '2018-12-21 09:18:30', 'node hello.jd', 1, '\\\\test-04-w10\\DOC\\nodejs\\',
        'kill all node', '2018-12-21 09:27:43', 1, NULL, NULL);

INSERT INTO easy_load.el_script (script_id, create_time, execute_cmd, pacing, path, stop_cmd, update_time, v_user, server_id, type_id)
VALUES ('571542f10e3d4646a80b34fed3d3b6c5', '2018-12-21 09:18:30', 'node hello.jd', 1, '\\\\test-04-w10\\DOC\\nodejs\\',
        'kill all node', '2018-12-21 09:27:43', 1, NULL, NULL);
# insert el_server
INSERT INTO easy_load.el_server (server_id, create_time, host, name, port, update_time) VALUES
  ('99d0bf2847f64b6d97bbcc00712f3ddd', '2018-12-22 09:14:17', 'wuja12-w10', 'wuja-pc', 7788, '2018-12-22 09:15:21');

# insert el_server_folder
INSERT INTO easy_load.el_server_folder (folder_id, create_time, domain, script_path, update_time, init_folder, el_attender_id, server_id)
VALUES ('e02e241bd498403d9768b4a35c8a7e4d', '2018-12-22 09:26:50', NULL, NULL, '2018-12-22 09:27:04', TRUE,
        'f2cc8a446e5c416baafcb8e42f2ac701', '99d0bf2847f64b6d97bbcc00712f3ddd');

#inser el_script
INSERT INTO easy_load.el_script (script_id, create_time, execute_cmd, pacing, path, stop_cmd, update_time, v_user, server_id, type_id, last_execute, server_folder_id, name)
VALUES ('571542f10e3d4646a80b34fed3d3b6c5', '2018-12-21 09:18:30', 'node hello.jd', 1, '\\\\test-04-w10\\DOC\\nodejs\\',
                                            'kill all node', '2018-12-21 09:27:43', 1, NULL, NULL, NULL,
        'e02e241bd498403d9768b4a35c8a7e4d', 'xxx.jar');
COMMIT;


