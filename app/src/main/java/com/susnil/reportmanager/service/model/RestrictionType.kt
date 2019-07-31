package com.susnil.reportmanager.service.model

enum class RestrictionType(val message: String) {
    TEXT_EXPORT("nazwa exportu"), DATE("data"), TIME("czas"), TEXT_USER("użytkownik"), TEXT_LOCAL("lokal")
}