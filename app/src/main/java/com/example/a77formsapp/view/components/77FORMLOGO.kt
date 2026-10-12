package com.example.a77formsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a77formsapp.ui.theme.BrandRed
import com.example.a77formsapp.ui.theme.BrandRedDark

// Placeholder "77" badge. When the real logo is ready, replace the body with:
//   Image(painterResource(R.drawable.logo_77), contentDescription = "77FORMS", modifier = modifier.size(size))
@Composable
fun BrandLogo(modifier: Modifier = Modifier, size: Dp = 36.dp) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.3f))
            .background(Brush.linearGradient(listOf(BrandRed, BrandRedDark))),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "77",
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = (size.value * 0.42f).sp
        )
    }
}