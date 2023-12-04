package com.ssoft.common.filter


data class ProviderSelectClass(
    val data:ArrayList<Int>
)

data class PlugSelectClass(
    val data:ArrayList<Int>
)

data class BrandSelectClass(
    val data:ArrayList<Int>
)




class Filter {


    var comming = true
    var close = true
    var update = true

    var chargeRate = 0
    var facRate = 0
    var troiletRate = 0

}

