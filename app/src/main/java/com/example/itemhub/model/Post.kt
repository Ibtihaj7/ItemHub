package com.example.itemhub.model

data class Post(
    private var userId:Int,
    private var id:Int,
    private var title:String,
    private var body:String,
    private var isFavorite:Boolean=false,
){
    fun getUserId():Int = userId
    fun getId():Int = id
    fun getTitle():String = title
    fun getBody():String = body
    fun getFavoriteState():Boolean = isFavorite
    fun getPhoto():String = "https://www.creativefabrica.com/wp-content/uploads/2020/03/08/post-it-with-line-icon-Graphics-3393743-1.jpg"
    fun setFavoriteState(isFavorite: Boolean) {
        this.isFavorite = isFavorite
    }
}