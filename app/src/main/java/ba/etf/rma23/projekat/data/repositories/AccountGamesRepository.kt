package ba.etf.rma23.projekat.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountGamesRepository {

    private var aid: String? = null

    fun setHash(acHash: String): Boolean {
        // Set the account hash locally
        this.aid= acHash
        return true
    }


    fun getHash(aid: String): String{
        return aid;
    }

}