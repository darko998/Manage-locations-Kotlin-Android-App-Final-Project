package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.repository.LocationRepository
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.contracts.LocationContract
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states.SaveLocationState

class LocationViewModel(private val repository: LocationRepository) : ViewModel(), LocationContract.ViewModel {

    override val saveDone: MutableLiveData<SaveLocationState> = MutableLiveData()

    private val subscriptions = CompositeDisposable()

    override fun saveLocation(locationAndNote: LocationAndNote) {
        val subscription = repository
            .saveLocation(locationAndNote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    saveDone.value = SaveLocationState.Success
                },
                {
                    saveDone.value = SaveLocationState.Error("Error occurred while saving this location and note.")
                }
            )

        subscriptions.add(subscription)
    }


    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}