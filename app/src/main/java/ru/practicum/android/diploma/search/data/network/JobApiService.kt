package ru.practicum.android.diploma.search.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.filter.data.dto.components.CountryDto
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.vacancy.data.dto.VacancyResponse

const val USER_AGENT_AUTHORIZATION = "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
const val USER_AGENT_APP_NAME = "HH-User-Agent: Find Your Job (snt_mail@bk.ru)"

interface JobApiService {

    @Headers(USER_AGENT_AUTHORIZATION, USER_AGENT_APP_NAME)
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: Int): VacancyResponse

    @Headers(USER_AGENT_AUTHORIZATION, USER_AGENT_APP_NAME)
    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): SearchResponse

    @GET("areas/countries")
    suspend fun getCountries(): Response<List<CountryDto>>
}
