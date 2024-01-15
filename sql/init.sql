
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
    constraint FK9h46qrqk2kn21mga8ha7g593l
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
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email)
);

create table admin
(
    position varchar(20) null,
    id       bigint      not null
        primary key,
    constraint FK1ja8rua032fgnk9jmq7du3b3a
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
    constraint UK_lh7am6sc9pv0nhyg7qkj7w5d3
        unique (student_id),
    constraint FKqytew32213tbnj8u0er377k3q
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
    constraint FKejhetonisg4txtfewssbuwuta
        foreign key (part_time_group_id) references part_time_group (id),
    constraint FKlr6ioq2q4ow9lkewf15u2ddpq
        foreign key (student_id) references student (id)
);

create table wage
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime null,
    last_modified_date datetime null,
    fifth_week_wage    int      not null,
    first_week_wage    int      not null,
    fourth_week_wage   int      not null,
    month              int      null,
    second_week_wage   int      not null,
    sixth_week_wage    int      not null,
    third_week_wage    int      not null,
    year               int      null,
    part_time_group_id bigint   null,
    student_id         bigint   null,
    constraint FKlmqdipng7nbbrxqt0ob3s7ll
        foreign key (student_id) references student (id),
    constraint FKq6d8tl5kv74dwnd3a2ovdrr9h
        foreign key (part_time_group_id) references part_time_group (id)
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
    constraint FK3x7nfcg2v6itg7juqm487qi55
        foreign key (student_id) references student (id),
    constraint FKirk6v0yilqoylhfbsiw5cypad
        foreign key (part_time_id) references part_time (id),
    constraint FKl30r9qfspi2omos0qk6yl735d
        foreign key (part_time_group_id) references part_time_group (id),
    constraint FKoe42moq4r1mgdkwb7iqaqa52r
        foreign key (confirmer_id) references admin (id),
    constraint FKr80vdgxdre49iqwvryk9opkwo
        foreign key (student_part_time_group_id) references student_part_time_group (id)
);
