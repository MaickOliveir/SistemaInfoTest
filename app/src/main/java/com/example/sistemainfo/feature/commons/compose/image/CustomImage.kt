package com.example.sistemainfo.feature.commons.compose.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.sistemainfo.R
import com.example.sistemainfo.feature.commons.dimen.Size

@Composable
fun CustomImage() {
    Image(
        painter = painterResource(id = R.drawable.logo_sistema_info),
        contentDescription = stringResource(id = R.string.logo_sistema_info_description),
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(Size.Size18XLG)
    )
}