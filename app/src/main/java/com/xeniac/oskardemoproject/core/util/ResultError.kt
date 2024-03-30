package com.xeniac.oskardemoproject.core.util

abstract class ResultError {
    object BlankField : ResultError()
    object UncheckedCheckBox : ResultError()
}