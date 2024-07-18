package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.ui.FavouritesViewModel
import ru.practicum.android.diploma.filter.ui.country.CountryViewModel
import ru.practicum.android.diploma.filter.ui.filter.FilterViewModel
import ru.practicum.android.diploma.filter.ui.location.LocationViewModel
import ru.practicum.android.diploma.filter.ui.region.RegionViewModel
import ru.practicum.android.diploma.filter.ui.sector.SectorViewModel
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        FavouritesViewModel(favouritesInteractor = get())
    }

    viewModel {
        VacancyViewModel(
            vacancyInteractor = get(),
            favouritesInteractor = get(),
            sharingInteractor = get()
        )
    }

    viewModel {
        FilterViewModel(get())
    }

    viewModel {
        LocationViewModel()
    }

    viewModel {
        CountryViewModel(filterInteractor = get())
    }

    viewModel {
        RegionViewModel()
    }

    viewModel {
        SectorViewModel()
    }
}
