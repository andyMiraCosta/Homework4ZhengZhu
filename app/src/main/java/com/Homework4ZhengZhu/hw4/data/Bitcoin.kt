package com.Homework4ZhengZhu.hw4.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bitcoin(
    @SerialName("createdAt")
    val createdAt: String = "",
    @SerialName("_id")
    val id: String,
    @SerialName("name")
    val owner: String = "",
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("updatedAt")
    val updatedAt: String = ""
)