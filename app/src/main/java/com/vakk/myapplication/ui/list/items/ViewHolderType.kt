package com.vakk.myapplication.ui.list.items

private var viewHolderType: Int = 0
    get() = ++field

enum class ViewHolderType(val type: Int) {
    HISTORY(viewHolderType);

    companion object {
        operator fun get(type: Int): ViewHolderType = values().first { it.type == type }
    }
}
