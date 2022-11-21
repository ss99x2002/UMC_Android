package com.example.assignment4.memo

class Nickname {

    companion object{
        private var nickname=""

        fun getNickName() :String{
            return nickname
        }

        fun setNickName(name:String){
            nickname = name
        }
    }
}