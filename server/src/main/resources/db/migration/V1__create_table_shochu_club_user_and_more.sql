create table if not exists image_resources(
    resource_id binary(16) primary key,
    resource_url varchar(512) not null,
    -- データ作成日時
    created_at datetime not null default cast(current_timestamp as DATETIME),
    -- データ更新日時
    updated_at datetime not null default cast(current_timestamp as DATETIME)
);


create table if not exists maker(
    -- uuid v4(or v7)
    maker_id binary(16) primary key,
    -- メーカ名
    maker_name varchar(64) not null,
    -- 詳細
    maker_description varchar(512) not null,
    -- URL
    maker_url varchar(255) not null,
    -- 住所
    maker_address varchar(512) not null,
    -- 都道府県コード
    maker_area int not null,
    -- メーカーの画像(メイン)
    main_maker_image_resource binary(16) null,
    -- データ作成日時
    created_at datetime not null default cast(current_timestamp as DATETIME),
    -- データ更新日時
    updated_at datetime not null default cast(current_timestamp as DATETIME),

    constraint foreign key fk_main_maker_image_url(main_maker_image_resource) references image_resources(resource_id) on delete set null
);

create table if not exists brand(
    -- ブランドID
    brand_id binary(16) primary key,
    -- ブランド名
    brand_name varchar(64) not null,
    -- ブランド詳細
    brand_description varchar(512) not null,
    -- ブランド画像(メイン)
    brand_main_image_url binary(16) null,
    -- メーカー
    maker_id binary(16) not null,
    -- データ作成日時
    created_at datetime not null default cast(current_timestamp as DATETIME),
    -- データ更新日時
    updated_at datetime not null default cast(current_timestamp as DATETIME),
    constraint foreign key fk_maker_id (maker_id) references maker(maker_id) on delete cascade ,
    constraint foreign key fk_main_image_url(brand_main_image_url) references image_resources(resource_id) on delete set null
);

create table if not exists shochu_club_user(
    -- ユーザID
    user_id binary(16) primary key,
    -- 誕生日
    birthday datetime null,
    -- ニックネーム
    nickname varchar(64) not null,
    -- firebase authenticationのID
    firebase_uid varchar(255) unique not null,
    -- コメント
    comment varchar(512) not null,
    -- アイコン画像URL
    icon_url binary(16) null ,
    -- データ作成日時
    created_at datetime not null default cast(current_timestamp as DATETIME),
    -- データ更新日時
    updated_at datetime not null default cast(current_timestamp as DATETIME),
    constraint foreign key fc_icon_url(icon_url) references image_resources(resource_id) on delete set null,
    index idx_firebase_uid(firebase_uid)
);