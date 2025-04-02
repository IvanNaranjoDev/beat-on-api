-- 'avatar'
CREATE TABLE IF NOT EXISTS avatars (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    path_url VARCHAR(255) NOT NULL
);

-- 'users'
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    passcode VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL,
    avatar_id BIGINT NOT NULL,
    FOREIGN KEY (avatar_id) REFERENCES avatars(id) ON DELETE CASCADE 
);

-- 'roles'
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

-- 'user_roles'
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- 'categories'
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL,
    color VARCHAR(7) NOT NULL,
    icon_url VARCHAR(255) NOT NULL
);

-- 'sounds'
CREATE TABLE IF NOT EXISTS sounds (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sound_name VARCHAR(50) NOT NULL,
    duration VARCHAR(10) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    sound_path VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- 'instrumentals'
CREATE TABLE IF NOT EXISTS instrumentals (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inst_name VARCHAR(50) UNIQUE NOT NULL,
    bpm VARCHAR(3) NOT NULL,
    public BOOLEAN NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    cover_url VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 'sounds'
CREATE TABLE IF NOT EXISTS inst_sounds (
    sound_id BIGINT NOT NULL,
    instrumental_id BIGINT NOT NULL,
    PRIMARY KEY (sound_id, instrumental_id),
    FOREIGN KEY (sound_id) REFERENCES sounds(id) ON DELETE CASCADE,
    FOREIGN KEY (instrumental_id) REFERENCES instrumentals(id) ON DELETE CASCADE
);

-- 'likes'
CREATE TABLE IF NOT EXISTS likes (
    user_id BIGINT NOT NULL,
    instrumental_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, instrumental_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (instrumental_id) REFERENCES instrumentals(id) ON DELETE CASCADE
);

-- 'playlist'
CREATE TABLE IF NOT EXISTS sounds (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    cover_url VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    public BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    instrumental_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (instrumental_id) REFERENCES instrumentals(id) ON DELETE CASCADE

);
