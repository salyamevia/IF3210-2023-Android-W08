package com.tubespbdandroid.majika.retrofit

import com.tubespbdandroid.majika.data.Branch
import retrofit2.Call
import retrofit2.http.GET

interface BranchAPI {
    @GET("branch")
    fun getBranches(): Call<List<Branch>>
}