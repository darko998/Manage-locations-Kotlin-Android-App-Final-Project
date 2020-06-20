package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.Resource
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.repository.LocationRepository
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.contracts.LocationContract
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states.LocationState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LocationViewModel(private val repository: LocationRepository) : ViewModel(), LocationContract.ViewModel {


    private val subscriptions = CompositeDisposable()
    override val state: MutableLiveData<LocationState> = MutableLiveData()

    private val publishSubject : PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                repository
                    .getFilteredData(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> state.value = LocationState.FetchFilteredSuccess(it.data)
                        is Resource.Error -> state.value = LocationState.FetchFilteredError("Error occurred while fetching filtered data.")
                    }
                },
                {
                    state.value = LocationState.FetchFilteredError("Error occurred while fetching filtered data.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun saveLocation(locationAndNote: LocationAndNote) {
        val subscription = repository
            .saveLocation(locationAndNote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    state.value = LocationState.SaveSuccess
                },
                {
                    state.value = LocationState.SaveError("Error occurred while saving this location and note.")
                }
            )

        subscriptions.add(subscription)
    }

    override fun getSavedLocations() {
        val subscription = repository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> state.value = LocationState.FetchSuccess(it.data)
                        is Resource.Loading -> state.value = LocationState.FetchLoading("Loading...")
                        is Resource.Error -> state.value = LocationState.FetchError("Error occurred while fetching data from database.")
                    }
                },
                {
                    state.value = LocationState.FetchError("Error occurred while fetching data from database.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getFilteredData(filter: String) {
        publishSubject.onNext(filter)
    }

    override fun update(locationAndNote: LocationAndNote) {
        val subscription = repository
            .update(locationAndNote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    state.value = LocationState.EditSuccess
                },
                {
                    state.value = LocationState.EditError("Error occurred while updating this instance.")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getDataOrderedByDateDesc(filter: String) {
        val subscription = repository
            .getDataOrderedByDateDesc(filter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> state.value = LocationState.FetchFilteredSuccess(it.data)
                        is Resource.Error -> state.value = LocationState.FetchFilteredError("Error occurred while fetching filtered data.")
                    }

                },
                {
                    state.value = LocationState.FetchFilteredError("Error occurred while fetching filtered data.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getDataOrderedByDateAsc(filter: String) {
        val subscription = repository
            .getDataOrderedByDateAsc(filter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> state.value = LocationState.FetchFilteredSuccess(it.data)
                        is Resource.Error -> state.value = LocationState.FetchFilteredError("Error occurred while fetching filtered data.")
                    }

                },
                {
                    state.value = LocationState.FetchFilteredError("Error occurred while fetching filtered data.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)    }


    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}