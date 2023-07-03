package com.bae.matching.utils

import androidx.annotation.StringRes
import com.bae.matching.R

enum class MyPageItem(@StringRes val stringRes: Int)
{
    MY_INFORMATION(R.string.my_page_my_information),
    THEME(R.string.my_page_theme)
}