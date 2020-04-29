package com.example.network.models

data class Article(
    val author:String,
    val source: Source,
    val title: String,
    val description: String,
    val url :String,
    val urlToImage:String,
    val content:String,
    val publishedAt:String
)

data class Source(
    val id: String,
    val name: String
)

data class ArticleResult(
    val status:String,
    val totalResults:Int,
    val articles:List<Article>
) {

}