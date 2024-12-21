package com.milkcocoa.info.shochu_club.server.infra.database.tables.feed

import com.milkcocoa.info.shochu_club.server.domain.model.type.FeedCategoryType
import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject

class FeedCategory(value: FeedCategoryType):ValueObject<Int>(value.type) {
    constructor(value: Int): this(FeedCategoryType.valueOf(value))
}