package com.kyawlinnthant.quotable.presentation.onboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kyawlinnthant.quotable.domain.vo.Quote

@Composable
fun OnBoardDataView(
    modifier: Modifier = Modifier,
    quotes: List<Quote>,
    onItemClicked: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { quotes.size })
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxSize(),
        ) { page ->
            val currentQuote = quotes[page]
            QuoteItem(
                quote = currentQuote,
                onItemClicked = onItemClicked,
            )
        }
        Row(
            modifier =
                modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .navigationBarsPadding(),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val iterated = pagerState.currentPage == iteration
                Indicator(iterated = iterated)
            }
        }
    }
}
