package com.milkcocoa.info.shochu_club.server.domain.model.type

enum class FeedCategoryType(val type: Int) {
    NormalFeed(0);

    companion object {
        fun valueOf(type: Int): FeedCategoryType = entries.find { it.type == type } ?: error("Unknown type $type")
    }
}