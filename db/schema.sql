drop database if exists mmdf;
create database mmdf;
use mmdf;

drop table if exists bid;
drop table if exists bid_order;
drop table if exists bid_product_image;
drop table if exists bid_product;
drop table if exists bid_product_category;
drop table if exists bid_product_condition;
drop table if exists news;
drop table if exists member;


create table if not exists member
(
    member_id               int auto_increment primary key,
    address                 varchar(100) not null,
    birth                   date         not null,
    cid                     varchar(200) not null,
    cid_reset_sending_time  datetime(6)  null,
    cid_reset_verify_uuid   varchar(100) null,
    city                    varchar(20)  not null,
    email                   varchar(50)  not null,
    image                   mediumblob   null,
    is_verified             tinyint      not null,
    join_time               datetime(6)  null,
    member_account          varchar(20)  not null,
    name                    varchar(20)  not null,
    phone                   varchar(20)  not null,
    score_number            int          null,
    score_sum               int          null,
    seller_status           tinyint      not null,
    tw_person_id            varchar(10)  not null,
    verified_time           datetime(6)  null,
    verify_sending_time     datetime(6)  null,
    wallet_amount           int          null,
    wallet_answer           varchar(100) not null,
    wallet_available_amount int          null,
    wallet_cid              varchar(200) not null,
    wallet_question         varchar(100) not null,
    constraint email_index unique (email),
    constraint member_account_index unique (member_account)
);

create index is_verified_index on member (is_verified);
create index seller_status_index on member (seller_status);

CREATE TABLE bid_product_category
(
    category_id   INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(20) NOT NULL
);

CREATE TABLE bid_product_condition
(
    condition_id   INT AUTO_INCREMENT PRIMARY KEY,
    condition_name VARCHAR(20) NOT NULL
);

CREATE TABLE bid_product
(
    product_id    INT AUTO_INCREMENT PRIMARY KEY,
    seller_id     INT          NOT NULL,
    category_id   INT          NOT NULL,
    condition_id  INT          NOT NULL,
    name          VARCHAR(40)  NOT NULL,
    description   VARCHAR(400) NOT NULL,
    start_price   INT          NOT NULL,
    duration      INT          NOT NULL,
    start_time    DATETIME,
    end_time      DATETIME,
    status        INT          NOT NULL DEFAULT 0,
    last_modified DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES member (member_id),
    FOREIGN KEY (category_id) REFERENCES bid_product_category (category_id),
    FOREIGN KEY (condition_id) REFERENCES bid_product_condition (condition_id)
);

CREATE TABLE bid_product_image
(
    image_id   INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT        NOT NULL,
    image      MEDIUMBLOB NOT NULL,
    position   INT        NOT NULL,
    FOREIGN KEY (product_id) REFERENCES bid_product (product_id)
);

CREATE TABLE bid
(
    bid_id     INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    member_id  INT NOT NULL,
    amount     INT NOT NULL,
    bid_time   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES bid_product (product_id),
    FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE bid_order
(
    order_id    INT AUTO_INCREMENT PRIMARY KEY,
    product_id  INT         NOT NULL,
    buyer_id    INT         NOT NULL,
    seller_id   INT         NOT NULL,
    subtotal    INT         NOT NULL,
    discount    INT         DEFAULT 0,
    total       INT         ,
    create_time DATETIME             DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status      INT         NOT NULL DEFAULT 0,
    name        VARCHAR(20) ,
    phone       VARCHAR(20) ,
    address     VARCHAR(40) ,
    fee         INT         ,
    FOREIGN KEY (product_id) REFERENCES bid_product (product_id),
    FOREIGN KEY (buyer_id) REFERENCES member (member_id),
    FOREIGN KEY (seller_id) REFERENCES member (member_id)
);

CREATE TABLE news
(
    news_id       INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(40)  NOT NULL,
    content       VARCHAR(800) NOT NULL,
    image         MEDIUMBLOB   NOT NULL,
    last_modified DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status        INT          NOT NULL DEFAULT 1
);

