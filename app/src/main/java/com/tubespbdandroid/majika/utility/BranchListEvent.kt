package com.tubespbdandroid.majika.utility

sealed class BranchListEvent{
    data class OnBranchItemClick(val position: Int) : BranchListEvent()
    object OnStart : BranchListEvent()
}
