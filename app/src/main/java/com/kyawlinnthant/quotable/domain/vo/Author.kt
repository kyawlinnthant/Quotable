package com.kyawlinnthant.quotable.domain.vo

data class Author(
    val id: String,
    val bio: String,
    val dateAdded: String,
    val dateModified: String,
    val description: String,
    val link: String,
    val name: String,
    val quoteCount: Int,
    val slug: String,
    val quotes: List<Quote>
) {

    companion object {
        val EMPTY = Author(
            id = "",
            bio = "",
            dateAdded = "",
            dateModified = "",
            description = "",
            link = "",
            name = "",
            quoteCount = 0,
            slug = "",
            quotes = emptyList()
        )

        val MOCK = Author(
            id = "76ISAUD3P5",
            name = "14th Dalai Lama",
            bio = "The 14th Dalai Lama (né Lhamo Thondup), known as Gyalwa Rinpoche to the Tibetan people, is the current Dalai Lama, the highest spiritual leader and former head of state of Tibet. Born on 6 July 1935, or in the Tibetan calendar, in the Wood-Pig Year, 5th month, 5th day. He is considered a living Bodhisattva; specifically, an emanation of Avalokiteśvara in Sanskrit and Chenrezig in Tibetan.",
            dateAdded = "2022-07-06",
            dateModified = "2022-07-06",
            description = "Current foremost spiritual leader of Tibet",
            link = "",
            quoteCount = 0,
            slug = "14th-dalai-lama",
            quotes = listOf(Quote.MOCK)
        )
    }
}
