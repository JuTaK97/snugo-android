package com.wafflestudio.snugo.features.records

import com.naver.maps.geometry.LatLng

enum class SortMethod{
        BASIC, TOP, RECOMMEND
    }

enum class RecordState{
    BOX, MAP
}
data class Record(
    val id: String,
    val nickname: String,
    val buildings: List<Building>,
    val path: List<Pair<Long, LatLng>>, // 타임스탬프 + 위경도
    val startTime: Long,
    val duration: Long,
)

data class Building(
    val id: Int
)