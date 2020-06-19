package rs.raf.projekat2.darko_dimitrijevic_rn9418.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource.LocationDatabase
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.repository.LocationRepository
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.repository.LocationRepositoryImpl
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.viewmodels.LocationViewModel

val locationModule = module {

    viewModel { LocationViewModel(repository = get()) }

    single<LocationRepository> { LocationRepositoryImpl(dataSource = get()) }

    single { get<LocationDatabase>().getDao() }
}