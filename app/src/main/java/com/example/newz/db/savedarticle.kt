package com.example.newz.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("NEWSARTICLE")
data class savedarticle(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("author")
    val author: String,

    @ColumnInfo("content")
    val content: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("publishedAt")
    val publishedAt: String,

    @ColumnInfo("source")
    val source: Source,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("url")
    val url: String,

    @ColumnInfo("urlToImage")
    val urlToImage: String

)
