create table part_time_group
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    name               varchar(100) null,
    semester           int          null,
    year               int          null
);

create table part_time
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    hour_price         int          null,
    name               varchar(100) null,
    night_allowance    int          null,
    overtime_allowance int          null,
    semester           int          null,
    year               int          null,
    part_time_group_id bigint       null,
    constraint fk_part_time_group_id
        foreign key (part_time_group_id) references part_time_group (id)
);

create table user
(
    dtype              varchar(31)  not null,
    id                 bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    email              varchar(255) not null,
    name               varchar(10)  null,
    password           varchar(255) not null,
    role               varchar(255) null,
    constraint uk_email
        unique (email)
);

create table admin
(
    position varchar(20) null,
    id       bigint      not null
        primary key,
    constraint fk_user
        foreign key (id) references user (id)
);

create table student
(
    account      varchar(100) null,
    bank         varchar(30)  null,
    department   varchar(30)  null,
    grade        int          null,
    phone_number varchar(30)  null,
    semester     int          null,
    status       varchar(255) null,
    student_id   varchar(20)  null,
    id           bigint       not null
        primary key,
    constraint uk_student_id
        unique (student_id),
    constraint fk_user
        foreign key (id) references user (id)
);

create table student_part_time_group
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime null,
    last_modified_date datetime null,
    part_time_group_id bigint   null,
    student_id         bigint   null,
    constraint fk_part_time_group
        foreign key (part_time_group_id) references part_time_group (id),
    constraint fk_student
        foreign key (student_id) references student (id),
    index ix_student_id__ptg_id(student_id, part_time_group_id)
);

create table wage
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime null,
    last_modified_date datetime null,
    hour_price         int      not null,
    month              int      null,
    work_time          double   not null,
    year               int      null,
    part_time_id       bigint   null,
    part_time_group_id bigint   null,
    student_id         bigint   null,
    constraint fk_part_time
        foreign key (part_time_id) references part_time (id),
    constraint fk_student
        foreign key (student_id) references student (id),
    constraint fk_part_time_group
        foreign key (part_time_group_id) references part_time_group (id),
    index ix_part_time_group_id__student_id(part_time_group_id, student_id)
);

create table work
(
    id                         bigint auto_increment
        primary key,
    created_date               datetime     null,
    last_modified_date         datetime     null,
    confirmer_name             varchar(255) null,
    end_time                   datetime     null,
    night_work_time            float        null,
    start_time                 datetime     null,
    status                     varchar(255) null,
    week                       int          null,
    work_time                  float        null,
    confirmer_id               bigint       null,
    part_time_id               bigint       null,
    part_time_group_id         bigint       null,
    student_id                 bigint       null,
    student_part_time_group_id bigint       null,
    constraint fk_student
        foreign key (student_id) references student (id),
    constraint fk_part_time
        foreign key (part_time_id) references part_time (id),
    constraint fk_part_time_group
        foreign key (part_time_group_id) references part_time_group (id),
    constraint fk_admin
        foreign key (confirmer_id) references admin (id),
    constraint fk_student_part_time_group
        foreign key (student_part_time_group_id) references student_part_time_group (id),
    index ix_part_time_group_id__student_id__status(part_time_group_id, student_id, status)
);
