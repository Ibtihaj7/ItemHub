package com.example.itemhub.model

data class Post(
    private val userId:Int,
    private val id:Int,
    private val title:String,
    private val body:String
) {
    fun getId():Int = id
    fun getUserId():Int = userId
    fun getTitle():String = title
    fun getBody():String = body
    fun getPhoto():String = "https://www.creativefabrica.com/wp-content/uploads/2020/03/08/post-it-with-line-icon-Graphics-3393743-1.jpg"
}