package com.example.mykonjang

// 번호, 장학 이름, 장학 분류, 서류 목록, 즐겨찾기, 성적, 장학 월
data class ScholarData (var pId:Int, var scholarName:String, var scholarType:String, var scholarList:String, var like:Boolean, var grade:Float, var scholarMonth:String) {
}