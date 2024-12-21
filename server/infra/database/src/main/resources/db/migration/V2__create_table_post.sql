create table if not exists feed(
    record_id uuid primary key,
    text text not null,
    post_owner uuid not null,
    brand_id uuid not null,
    -- 投稿のカテゴリ
    -- とりあえず
    --  0: 通常投稿
    feed_category int not null ,
    -- データ作成日時
    created_at timestamp with time zone not null default current_timestamp,
    -- データ更新日時
    updated_at timestamp with time zone not null default current_timestamp,
    constraint fk_post_owner foreign key(post_owner) references system_uid(uid) on delete cascade on update cascade,
    constraint fk_brand_id foreign key (brand_id) references shochu_brand(brand_id) on delete cascade on update cascade
);
CREATE INDEX ON feed(post_owner);


create table if not exists feed_image_resource(
    resource_id uuid primary key,
    resource_url varchar(512) not null,
    resource_owner uuid null,
    related_feed uuid null ,
    -- データ作成日時
    created_at timestamp with time zone not null default current_timestamp,
    constraint fk__feed_image_resource__owner foreign key (resource_owner) references system_uid(uid) on delete set null on update cascade,
    constraint fk__feed_image_resource__related_feed foreign key (related_feed) references feed(record_id) on delete set null on update cascade
);
CREATE INDEX ON feed_image_resource(resource_owner);
CREATE INDEX ON feed_image_resource(related_feed);



-- フォロー状態を管理するテーブル
CREATE TABLE IF NOT EXISTS relationship(
    -- ID
    relation_id uuid primary key ,
    -- フォローしている人。FROM
    followee uuid not null ,
    -- フォローされている人。TO
    follower uuid not null ,
    -- フォロー時刻
    created_at timestamp with time zone not null,
    constraint fk_followee_id foreign key (followee) references system_uid(uid) on delete cascade on update cascade,
    constraint fk_follower_id foreign key (follower) references system_uid(uid) on delete cascade on update cascade ,
    unique (follower, followee),
    check ( follower != relationship.followee )
);
CREATE INDEX ON relationship(followee);
CREATE INDEX ON relationship(follower);