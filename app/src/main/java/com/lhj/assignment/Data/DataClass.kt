package com.lhj.assignment.Data

import com.google.gson.annotations.SerializedName

class DataClass {

    data class ResponseData(
        @SerializedName("msg")
        var msg: String,
        @SerializedName("data")
        var data: Data,
        @SerializedName("code")
        var code: Int
    )

    data class Data(
        @SerializedName("totalCount")
        var totalCount: Int,
        @SerializedName("product")
        var product: List<MainData>
    )

    data class MainData(
        @SerializedName("id")
        var id: Int,                // 게시글 ID
        @SerializedName("name")
        var name: String,          // 게시글 이름
        @SerializedName("rate")
        var rate: Double,          // 평점
        @SerializedName("thumbnail")
        var thumbnail: String,          // 썸네일 경로
        @SerializedName("description")
        var description: Description,          // 상세정보
        var favorites: String
    )

    data class Description(
        @SerializedName("imagePath")
        var imagePath: String,       // 이미지 경로
        @SerializedName("subject")
        var subject: String,        // 설명
        @SerializedName("price")
        var price: Int              // 가격
    )

}