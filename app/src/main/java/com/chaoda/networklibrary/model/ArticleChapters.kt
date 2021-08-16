package com.chaoda.networklibrary.model

class Articles : ArrayList<Articles.ArticlesItem>() {
    data class ArticlesItem(
        val children: List<Any?>?,
        val courseId: Int,
        val id: Int,
        val name: String?,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
    )

    override fun toString(): String {
        return "name: ${get(0).name ?: "Empty"}"
    }
}