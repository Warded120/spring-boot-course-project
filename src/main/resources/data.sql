-- Step 1: Check if the user with the specified email exists
DO
$$
    DECLARE
        user_id INT;
        key_id INT;
        super_user_data_id INT;
        role_id_operator INT;
        role_id_admin INT;
    BEGIN
        -- Check if user with email 'admin@gmail.com' exists
        SELECT id INTO user_id FROM users WHERE username = 'admin@gmail.com';

        -- If user does not exist, proceed with the creation process
        IF user_id IS NULL THEN
            -- Step 2: Insert into `keys` table to store the password
            INSERT INTO keys (password) VALUES ('$2a$10$DbuPbmj.VnxzfoO5YfEbJez.xYRRV9wJivpK6J9VnSH4XGi/wI5sy')
            RETURNING id INTO key_id;

            -- Step 3: Insert into `users` table with the key ID
            INSERT INTO users (username, key_id, enabled)
            VALUES ('admin@gmail.com', key_id, true)
            RETURNING id INTO user_id;

            -- Step 4: Insert into `super_user_data` table for additional user info
            INSERT INTO super_user_data (first_name, last_name, birth_date)
            VALUES ('admin', 'admin', '2005-05-18')
            RETURNING id INTO super_user_data_id;

            -- Step 5: Insert into `super_user` table to extend user details
            INSERT INTO super_user (id, super_user_data_id)
            VALUES (user_id, super_user_data_id);

            -- Step 6: Ensure roles "ROLE_OPERATOR" and "ROLE_ADMIN" exist
            -- Insert "ROLE_OPERATOR" if it does not exist
            INSERT INTO role (name)
            SELECT 'ROLE_OPERATOR'
            WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_OPERATOR')
            RETURNING id INTO role_id_operator;

            -- Insert "ROLE_ADMIN" if it does not exist
            INSERT INTO role (name)
            SELECT 'ROLE_ADMIN'
            WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_ADMIN')
            RETURNING id INTO role_id_admin;

            -- Get the IDs of "ROLE_OPERATOR" and "ROLE_ADMIN" (for cases where they already existed)
            SELECT id INTO role_id_operator FROM role WHERE name = 'ROLE_OPERATOR';
            SELECT id INTO role_id_admin FROM role WHERE name = 'ROLE_ADMIN';

            -- Step 7: Insert user roles in `users_roles` table
            INSERT INTO users_roles (user_id, role_id) VALUES
                                                           (user_id, role_id_operator),
                                                           (user_id, role_id_admin);

            RAISE NOTICE 'User admin@gmail.com has been created successfully.';
        ELSE
            RAISE NOTICE 'User admin@gmail.com already exists. No new user created.';
        END IF;
    END;
$$ LANGUAGE plpgsql;
