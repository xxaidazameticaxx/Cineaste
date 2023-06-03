package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GetSwaggerGameResponse(
    @SerializedName("igdb_id") val igdb_id: Int?,
    @SerializedName("name") val name: String?

)




