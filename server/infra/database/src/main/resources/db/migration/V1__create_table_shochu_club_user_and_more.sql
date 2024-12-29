-- UIDを一括で管理するテーブル
CREATE TABLE IF NOT EXISTS system_uid(
    -- システム全体でユーザを一意に識別するためのID
    uid UUID PRIMARY KEY,
    -- UIDに紐づくユーザ名。
    -- uidはuuidなので、それをヒューマンフレンドリーな文字列に対応させたもの
    username VARCHAR(64) UNIQUE NOT NULL,
    -- レコード作成日時
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    -- レコード更新日時
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL ,
    -- UIDに紐づくアカウントが削除されているかどうか
    -- つまり、本登録ユーザが削除されているかどうか
    -- 匿名ユーザの削除は、本登録ユーザへの昇格の可能性もあるので、削除とみなされない
    is_deleted BOOL NOT NULL DEFAULT FALSE,
    -- 紐付いているアカウントが匿名ユーザかどうか
    is_anonymous_user BOOL DEFAULT FALSE,
    -- 削除理由
    delete_reason INT NULL,
    -- 削除時刻
    -- つまり、本登録ユーザの退会時刻
    deleted_at TIMESTAMP WITH TIME ZONE NULL
);

-- 匿名ユーザテーブル
CREATE TABLE IF NOT EXISTS anonymous_user (
    -- システム上のID
    id UUID PRIMARY KEY ,
    -- ユーザID
    uid UUID UNIQUE REFERENCES system_uid(uid) ON DELETE CASCADE ON UPDATE CASCADE,
    -- ニックネーム
    nickname VARCHAR(64) NOT NULL ,
    -- コメント
    comment VARCHAR(512) NOT NULL ,
    -- 削除理由
    -- 0: 本登録のため
    -- 1: ユーザ申し出のため
    delete_reason INT NULL,
    -- データ作成日時
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- データ更新日時
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- アカウント削除時刻
    -- 例えば成り上がったときとか、申し出のとき
    deleted_at TIMESTAMP WITH TIME ZONE NULL
);
CREATE INDEX ON anonymous_user(uid);


CREATE TABLE IF NOT EXISTS shochu_club_user(
    id UUID PRIMARY KEY ,
    -- ユーザID
    uid UUID UNIQUE REFERENCES system_uid(uid) ON DELETE CASCADE ON UPDATE CASCADE,
    -- メアド
    email VARCHAR(250) UNIQUE NOT NULL,
    is_email_verified BOOL NOT NULL,
    -- ハッシュ化済パスワード（Argon2）
    password_hash TEXT,
    -- パスワード変更日時
    password_changed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- ログイン失敗回数
    failed_login_attempts INT NOT NULL DEFAULT 0,
    -- 認証プロバイダ
    auth_provider INT NOT NULL,

    is_enabled BOOL NOT NULL DEFAULT TRUE,
    is_deleted BOOL NOT NULL DEFAULT FALSE,
    is_locked BOOL NOT NULL DEFAULT FALSE,

    multi_factor_auth_enabled BOOL NOT NULL,
    multi_factor_auth_secret TEXT NULL,

    -- 誕生日
    birthday TIMESTAMP WITH TIME ZONE NULL,
    -- ニックネーム
    nickname VARCHAR(64) NOT NULL,
    -- コメント
    comment VARCHAR(512) NOT NULL,
    -- アイコン画像URL
    profile_icon_url VARCHAR(384),
    -- データ作成日時
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- データ更新日時
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- 最終ログイン時刻
    last_login_at TIMESTAMP WITH TIME ZONE NULL,
    -- アカウントをDISABLEにした時刻
    account_disabled_at TIMESTAMP WITH TIME ZONE NULL,
    -- アカウント削除時刻
    account_deleted_at TIMESTAMP WITH TIME ZONE NULL
);
CREATE INDEX ON shochu_club_user(uid);

CREATE TABLE IF NOT EXISTS provisional_registration (
    id UUID PRIMARY KEY,     -- 仮登録の一意のID
    uid UUID NULL REFERENCES system_uid(uid) ON DELETE CASCADE ON UPDATE CASCADE,                                         -- 関連するユーザーID（既存ユーザーの場合）
    email VARCHAR(255) NOT NULL UNIQUE ,                                         -- 仮登録中のメールアドレス（新規登録/変更後のアドレス）
    password_hash TEXT,                                                  -- パスワードのハッシュ（新規登録時のみ, ）
    registration_type INT NOT NULL,                                      -- 0: new, 1: change email
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,    -- 仮登録の作成日時
    CHECK ( registration_type IN (0, 1))
);
CREATE INDEX ON provisional_registration(uid);


CREATE TABLE IF NOT EXISTS shochu_maker(
    -- UUID v4(or v7)
    maker_id UUID PRIMARY KEY,
    -- メーカ名
    maker_name VARCHAR(64) NOT NULL,
    -- 詳細
    maker_description VARCHAR(512) NOT NULL,
    -- URL
    maker_url VARCHAR(255) NOT NULL,
    -- 住所
    maker_address VARCHAR(512) NOT NULL,
    -- 都道府県コード
    maker_area INT NOT NULL,
    -- データ作成日時
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- データ更新日時
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- 1:北海道 ...
    -- 47: 沖縄県
    check ( maker_area BETWEEN 1 and 47)
);

CREATE TABLE IF NOT EXISTS shochu_maker_image_resource(
    resource_id UUID PRIMARY KEY,
    resource_url VARCHAR(512) NOT NULL,
    related_maker UUID NOT NULL REFERENCES shochu_maker(maker_id) ON DELETE CASCADE ON UPDATE CASCADE ,
    -- データ作成日時
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX ON shochu_maker_image_resource(related_maker);


CREATE TABLE IF NOT EXISTS shochu_brand(
    -- ブランドID
    brand_id UUID PRIMARY KEY,
    -- ブランド名
    brand_name VARCHAR(64) NOT NULL,
    -- ブランド詳細
    brand_description VARCHAR(512) NOT NULL,
    -- メーカー
    maker_id UUID NOT NULL REFERENCES shochu_maker(maker_id) ON DELETE CASCADE ON UPDATE CASCADE,
    -- データ作成日時
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- データ更新日時
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shochu_brand_image_resource(
    resource_id UUID PRIMARY KEY,
    resource_url VARCHAR(512) NOT NULL,
    related_brand UUID NOT NULL REFERENCES shochu_brand(brand_id) ON DELETE CASCADE ON UPDATE CASCADE ,
    -- データ作成日時
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP 
);
CREATE INDEX ON shochu_brand_image_resource(related_brand);

CREATE TABLE IF NOT EXISTS user_session(
    session_id UUID PRIMARY KEY , -- セッションID
    uid UUID NOT NULL REFERENCES system_uid(uid) ON DELETE CASCADE ON UPDATE CASCADE, -- セッションを持つユーザ
    refresh_token_hash bit(32) NOT NULL, -- HMAC-SHA256
    created_at TIMESTAMP WITH TIME ZONE NOT NULL, -- セッション発行時刻
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX ON user_session(uid);
-- CREATE INDEX ON user_session(created_at);
-- CREATE INDEX ON user_session(expires_at);