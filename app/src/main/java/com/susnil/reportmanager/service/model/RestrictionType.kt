package com.susnil.reportmanager.service.model

import com.susnil.reportmanager.R

enum class RestrictionType(val resId: Int) {
    TEXT_EXPORT(R.string.export),
    DATE(R.string.date),
    TIME(R.string.time),
    TEXT_USER(R.string.user),
    TEXT_LOCAL(R.string.place)
}