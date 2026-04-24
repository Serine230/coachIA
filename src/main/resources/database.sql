-- Base de données : coach_ia

CREATE TABLE users (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    created_at  DATETIME DEFAULT NOW()
);

CREATE TABLE categories (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(100) NOT NULL,
    color    VARCHAR(7) DEFAULT '#7c6ffa',
    user_id  INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tasks (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(200) NOT NULL,
    description  TEXT,
    category_id  INT,
    priority     VARCHAR(20) DEFAULT 'MOYENNE',
    status       VARCHAR(20) DEFAULT 'À faire',
    due_date     DATE,
    user_id      INT NOT NULL,
    created_at   DATETIME DEFAULT NOW(),
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    FOREIGN KEY (user_id)     REFERENCES users(id)      ON DELETE CASCADE
);

CREATE TABLE productivity_log (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    task_id     INT NOT NULL,
    user_id     INT NOT NULL,
    time_spent  BIGINT NOT NULL,
    logged_at   DATETIME NOT NULL,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);