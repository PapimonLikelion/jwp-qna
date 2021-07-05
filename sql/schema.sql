DROP table user;
DROP table question;
DROP table answer;
DROP table delete_history;

create table if not exists user (
    id              bigint auto_increment not null,
    created_at      timestamp not null,
    email           varchar(50),
    name            varchar(20) not null,
    password        varchar(20) not null,
    updated_at      timestamp,
    user_id         varchar(20) not null unique,
    primary key     (id)
);

create table if not exists question (
    id              bigint auto_increment not null,
    contents        clob,
    created_at      timestamp not null,
    deleted         boolean not null,
    title           varchar(100) not null,
    updated_at      timestamp,
    writer_id       bigint,
    primary key     (id),
    foreign key     (writer_id) references user(id)
);

create table if not exists answer (
    id              bigint auto_increment not null,
    contents        clob,
    created_at      timestamp not null,
    deleted         boolean not null,
    question_id     bigint,
    updated_at      timestamp,
    writer_id       bigint,
    primary key     (id),
    foreign key     (question_id) references question(id),
    foreign key     (writer_id) references user(id)
);

create table if not exists delete_history (
    id              bigint auto_increment not null,
    content_id      bigint,
    content_type    varchar(255),
    create_date     timestamp,
    delete_by_id    bigint,
    primary key     (id),
    foreign key     (content_id) references question(id),
    foreign key     (delete_by_id) references user(id)
);
