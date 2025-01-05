package com.plcoding.bookpedia.book.presentation.book_list.components

import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.favourites
import cmp_bookpedia.composeapp.generated.resources.search_results
import org.jetbrains.compose.resources.StringResource

enum class TabItems(val index: Int, val resPath: StringResource) {
    SearchResults(0, Res.string.search_results),
    Favorites(1, Res.string.favourites)
}
