package com.tech.deliverydetailspage.core.service

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceFactory<K,V,S> @Inject constructor(
        val dataSource: S
): DataSource.Factory<K,V>() {

    var dataSourceLiveData: MutableLiveData<S> = MutableLiveData()

    override fun create(): DataSource<K, V> {
        dataSourceLiveData.postValue(dataSource)
        return dataSource as DataSource<K, V>
    }

}