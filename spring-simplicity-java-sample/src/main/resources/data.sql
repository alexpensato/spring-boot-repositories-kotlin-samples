INSERT INTO college (id, name, name_of_city) VALUES (1, 'University of California', 'Berkeley') ON CONFLICT (id) DO NOTHING;
INSERT INTO college (id, name, name_of_city) VALUES (2, 'Harvard University', 'Cambridge') ON CONFLICT (id) DO NOTHING;

INSERT INTO student (id, name, address, college_id) VALUES (1, 'Mark', 'Telegraph Ave', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO student (id, name, address, college_id) VALUES (2, 'Susie', 'Shattuck Ave', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO student (id, name, address, college_id) VALUES (3, 'Valerie', 'Euclid Ave', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO student (id, name, address, college_id) VALUES (4, 'John', 'Oxford St', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO student (id, name, address, college_id) VALUES (5, 'Mary', 'Washington St', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO student (id, name, address, college_id) VALUES (6, 'Joseph', 'Everett St', 2) ON CONFLICT (id) DO NOTHING;
