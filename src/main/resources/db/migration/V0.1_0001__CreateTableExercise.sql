CREATE TABLE `exercise`.exercise
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    source           VARCHAR(30)        NOT NULL,
    code_list_code   VARCHAR(255)       NOT NULL,
    code             VARCHAR(30) UNIQUE NOT NULL,
    display_value    VARCHAR(100)       NOT NULL,
    long_description TEXT,
    from_date        DATE               NOT NULL,
    to_date          DATE,
    sorting_priority int
);