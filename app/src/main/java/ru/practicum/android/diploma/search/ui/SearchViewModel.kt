package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.VacanciesResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.utils.Options
import ru.practicum.android.diploma.search.domain.utils.ResponseData
import ru.practicum.android.diploma.utils.debounce

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    private val _screenState = MutableLiveData<SearchState>(SearchState.Empty)
    val screenState: LiveData<SearchState> = _screenState

    private var latestSearchText: String? = null

    private var currentPage = -1
    private var maxPages = 0
    private val vacanciesList = mutableListOf<Vacancy>()

    private val searchDebounce = debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_DELAY_MILLIS,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText ->
        currentPage = 0
        maxPages = 0
        vacanciesList.clear()
        searchRequest(changedText)
    }

    private val pageErrorDebounce = debounce<ResponseData.ResponseError>(
        delayMillis = PAGE_LOADING_ERROR_DELAY_MILLIS,
        coroutineScope = viewModelScope,
        useLastParam = false,
    ) { responseError ->
        _screenState.postValue(SearchState.Error(responseError, true))
    }

    private var isNextPageLoading = false

    private var searchJob: Job? = null

    private var cancelJob = false

    fun onLastItemReached() {
        if (isNextPageLoading) {
            return
        }

        if (currentPage < maxPages) {
            currentPage++
            latestSearchText?.let {
                isNextPageLoading = true
                searchRequest(it)
            }
        }
    }

    fun search(searchText: String?) {
        if (searchText.isNullOrEmpty()) {
            cancelJob = true
            searchJob?.cancel()
            _screenState.postValue(SearchState.Empty)
        } else if (latestSearchText != searchText) {
            latestSearchText = searchText
            cancelJob = false
            searchDebounce(searchText)
        }
    }

    private fun searchRequest(searchText: String) {
        if (searchText.isNotEmpty() && !cancelJob) {
            _screenState.postValue(SearchState.Loading(currentPage > 0))
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                val options = Options(
                    searchText = searchText,
                    itemsPerPage = VACANCIES_PER_PAGE,
                    page = currentPage,
                )
                searchInteractor.search(options).collect(::processResponse)
            }
        }
    }

    private fun processResponse(vacanciesData: ResponseData<VacanciesResponse>) {
        when (vacanciesData) {
            is ResponseData.Data -> {
                val vacanciesResponse = vacanciesData.value
                with(vacanciesResponse) {
                    currentPage = page
                    vacanciesList += results
                    maxPages = pages
                    _screenState.postValue(SearchState.Content(vacanciesList, foundVacancies))
                }
            }

            is ResponseData.Error -> {
                if (isNextPageLoading) {
                    pageErrorDebounce(vacanciesData.error)
                } else {
                    _screenState.postValue(SearchState.Error(vacanciesData.error, false))
                }
            }
        }

        isNextPageLoading = false
    }

    private companion object {
        const val VACANCIES_PER_PAGE = 20
        const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        const val PAGE_LOADING_ERROR_DELAY_MILLIS = 4000L
    }
}
