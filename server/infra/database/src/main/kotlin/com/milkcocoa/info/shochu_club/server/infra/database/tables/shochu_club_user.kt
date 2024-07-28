package com.milkcocoa.info.shochu_club.server.infra.database.tables

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.uuidValueObject
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

class Birthday(
    value: String,
) : ValueObject<String>(value)

class NickName(
    value: String,
) : ValueObject<String>(value)

class FirebaseUid(
    value: String,
) : ValueObject<String>(value)

class ShochuClubUserId(
    value: UUID,
) : ValueObject<UUID>(value),
    Comparable<ShochuClubUserId> {
    override fun compareTo(other: ShochuClubUserId) = value.compareTo(other.value)
}

class Comment(
    value: String,
) : ValueObject<String>(value)

class ProfileIconUrl(
    value: String,
) : ValueObject<String>(value)

@SuppressWarnings("ktlint:standard:class-naming", "standard:class-naming")
object shochu_club_user : IdTable<ShochuClubUserId>("shochu_club_user") {
    override val id: Column<EntityID<ShochuClubUserId>> =
        uuidValueObject<ShochuClubUserId>("user_id")
            .clientDefault {
                ShochuClubUserId(value = UUID.randomUUID())
            }.uniqueIndex()
            .entityId()
    val birthday = datetime("birthday") // stringValueObject<Birthday>("birthday")
    val nickname = stringValueObject<NickName>("nickname")
    val firebase_uid = stringValueObject<FirebaseUid>("firebase_uid")
    val comment = stringValueObject<Comment>("comment")
    val icon_url = stringValueObject<ProfileIconUrl>("icon_url")
    val created_at = datetime("created_at")
    val updated_at = datetime("updated_at")

    override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
}
