create table if not exists system_uid(
    uid uuid primary key,
    username varchar(64) unique not null,
    created_at timestamp with time zone not null,        -- UIDの作成日時
    is_deleted bool not null default false, -- UIDの削除フラグ
    is_anonymous_user boolean default false,
    delete_reason int null,              -- 削除理由（ENUMや数値で定義）
    deleted_at timestamp with time zone null             -- 削除された日時
);

create table if not exists profile_image_resource(
    resource_id uuid primary key,
    resource_url varchar(512) not null,
    resource_owner uuid null,
    -- データ作成日時
    created_at timestamp with time zone not null default current_timestamp,
    constraint fk__image_resource__owner foreign key (resource_owner) references system_uid(uid) on delete set null on update cascade
);

create table if not exists anonymous_user (
    id uuid primary key ,
    -- ユーザID
    system_uid uuid unique ,
    nickname varchar(64) not null ,
    comment varchar(512) not null ,
    delete_reason int null,              -- 昇格や削除の理由
    -- データ作成日時
    created_at timestamp with time zone not null default current_timestamp,
    -- データ更新日時
    updated_at timestamp with time zone not null default current_timestamp,
    deleted_at timestamp with time zone null ,             -- 削除された日時
    constraint fk_anonymous_user_uid foreign key (system_uid) references system_uid(uid) on delete cascade on update cascade
);

create table if not exists shochu_club_user(
    id uuid primary key ,
    -- ユーザID
    system_uid uuid unique ,
    -- メアド
    email varchar(250) unique not null,
    is_email_verified bool not null,
    -- ハッシュ化済パスワード（Argon2）
    password_hash TEXT,
    -- パスワード変更日時
    password_changed_at timestamp with time zone not null default current_timestamp,
    -- ログイン失敗回数
    failed_login_attempts int not null default 0,
    -- 認証プロバイダ
    auth_provider int not null,

    is_enabled bool not null default true,
    is_deleted bool not null default false,
    is_locked bool not null default false,

    multi_factor_auth_enabled bool not null,
    multi_factor_auth_secret TEXT null,

    -- 誕生日
    birthday timestamp with time zone null,
    -- ニックネーム
    nickname varchar(64) not null,
    -- コメント
    comment varchar(512) not null,
    -- アイコン画像URL
    icon_url uuid null ,
    -- データ作成日時
    created_at timestamp with time zone not null default current_timestamp,
    -- データ更新日時
    updated_at timestamp with time zone not null default current_timestamp,
    -- 最終ログイン時刻
    last_login_at timestamp with time zone null,
    -- アカウントをDISABLEにした時刻
    account_disabled_at timestamp with time zone null,
    -- アカウント削除時刻
    account_deleted_at timestamp with time zone null,
    constraint fk__shochu_club_user__uid foreign key (system_uid ) references system_uid(uid) on delete cascade on update cascade,
    constraint fc_icon_url foreign key(icon_url) references profile_image_resource(resource_id) on delete set null
);


CREATE TABLE provisional_registration (
    id uuid PRIMARY KEY,     -- 仮登録の一意のID
    system_uid uuid null ,                                         -- 関連するユーザーID（既存ユーザーの場合）
    email VARCHAR(255) NOT NULL UNIQUE ,                                         -- 仮登録中のメールアドレス（新規登録/変更後のアドレス）
    password_hash TEXT,                                                  -- パスワードのハッシュ（新規登録時のみ, ）
    registration_type int NOT NULL,                                      -- 0: new, 1: change email
    created_at timestamp with time zone DEFAULT cast(current_timestamp as timestamp with time zone ),    -- 仮登録の作成日時
    constraint fk__1herAFbsgf_user_id foreign key(system_uid) references system_uid(uid) on delete cascade on update cascade ,
    CHECK ( registration_type in (0, 1))
);
CREATE INDEX ON provisional_registration(system_uid);







create table if not exists shochu_maker(
    -- uuid v4(or v7)
    maker_id uuid primary key,
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
    main_maker_image_resource uuid null,
    -- データ作成日時
    created_at timestamp with time zone not null default current_timestamp,
    -- データ更新日時
    updated_at timestamp with time zone not null default current_timestamp,

    constraint fk_main_maker_image_url foreign key (main_maker_image_resource) references profile_image_resource(resource_id) on delete set null,
    -- 1:北海道 ...
    -- 47: 沖縄県
    check ( maker_area between 1 and 47)

);

create table if not exists shochu_brand(
    -- ブランドID
    brand_id uuid primary key,
    -- ブランド名
    brand_name varchar(64) not null,
    -- ブランド詳細
    brand_description varchar(512) not null,
    -- ブランド画像(メイン)
    brand_main_image_url uuid null,
    -- メーカー
    maker_id uuid not null,
    -- データ作成日時
    created_at timestamp with time zone not null default current_timestamp,
    -- データ更新日時
    updated_at timestamp with time zone not null default current_timestamp,
    constraint fk_maker_id foreign key (maker_id) references shochu_maker(maker_id) on delete cascade ,
    constraint fk_main_image_url foreign key (brand_main_image_url) references profile_image_resource(resource_id) on delete set null
);

create table if not exists user_session(
    session_id uuid primary key , -- セッションID
    system_uid uuid not null , -- セッションを持つユーザ
    refresh_token_hash bit(32) not null, -- HMAC-SHA256
    created_at timestamp with time zone not null, -- セッション発行時刻
    expires_at timestamp with time zone not null,
    constraint fk__user_session_user_id foreign key(system_uid) references system_uid(uid) on delete cascade on update cascade
);

CREATE INDEX ON user_session(system_uid);
-- CREATE INDEX ON user_session(created_at);
-- CREATE INDEX ON user_session(expires_at);