package com.tubespbdandroid.majika.retrofit.branch

import com.tubespbdandroid.majika.data.Branch
import retrofit2.Call
import retrofit2.http.GET

interface BranchService {
    @GET("branch")
    fun getBranches(): Call<List<Branch>>
}