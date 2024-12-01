package com.milkcocoa.info.shochu_club.server.infra.database.tables.common

import com.milkcocoa.info.shochu_club.server.domain.model.type.DeleteReasonValue
import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject


class DeleteReason(value: DeleteReasonValue): ValueObject<Int>(value.value){
    constructor(value: Int): this(DeleteReasonValue.valueOf(value))
}