package com.example.sistemainfo.feature.commons.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.sistemainfo.R

object Size {

    val Size1: Dp @Composable get() = dimensionResource(id = R.dimen.size_1)
    val SizeXSM: Dp @Composable get() = dimensionResource(id = R.dimen.size_xsm)
    val SizepXSM: Dp @Composable get() = dimensionResource(id = R.dimen.size_pxsm)
    val SizeSM: Dp @Composable get() = dimensionResource(id = R.dimen.size_sm)
    val SizeMD: Dp @Composable get() = dimensionResource(id = R.dimen.size_md)
    val Size8XLG: Dp @Composable get() = dimensionResource(id = R.dimen.size_8xlg)
    val Size18XLG: Dp @Composable get() = dimensionResource(id = R.dimen.size_18xlg)
}