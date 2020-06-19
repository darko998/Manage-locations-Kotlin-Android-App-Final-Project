package rs.raf.projekat2.darko_dimitrijevic_rn9418.modules

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource.LocationDatabase

val coreModule = module {

    single { Room.databaseBuilder(androidContext(), LocationDatabase::class.java, "LocationDB")
        .fallbackToDestructiveMigration()
        .build() }

}