create table if not exists post(
    record_id binary(16) primary key,
    text text not null,
    post_owner binary(16) not null,
    brand_id binary(16) not null,
    -- データ作成日時
    created_at datetime not null default cast(current_timestamp as DATETIME),
    -- データ更新日時
    updated_at datetime not null default cast(current_timestamp as DATETIME),
    constraint foreign key fk_post_owner(post_owner) references shochu_club_user(user_id) on delete cascade ,
    constraint foreign key fk_brand_id(brand_id) references brand(brand_id) on delete cascade,
    index idx_post_owner(post_owner)
);

create table if not exists post_image_mapping(
  mapping_id binary(16) primary key,
  post_id binary(16) not null,
  resource_id binary(16) not null,
  constraint foreign key fk_post_id(post_id) references post(record_id) on delete cascade ,
  constraint foreign key fk_resource_id(resource_id) references image_resources(resource_id) on delete cascade
);