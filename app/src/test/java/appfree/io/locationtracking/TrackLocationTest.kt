package appfree.io.locationtracking

import appfree.io.locationtracking.di.managerInjection
import appfree.io.locationtracking.modules.location.TrackLocation
import appfree.io.locationtracking.modules.location.TrackLocationManager
import appfree.io.locationtracking.modules.map.TrackMapManager
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.android.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

/**
 * Created By Ben on 7/14/20
 */
class TrackLocationTest: KoinTest {

    val locationA = TrackLocation(
        uid = 1,
        longitude = 106.7068249,
        latitude = 10.7760217,
        duration = 0,
        sessionId = "321321",
        updatedAt = 1312731892234
    )

    val locationB = TrackLocation(
        uid = 2,
        longitude = 106.7068249,
        latitude = 10.7760217,
        duration = 100,
        sessionId = "321321",
        updatedAt = 1312731892234
    )

    private val trackMapManager: TrackMapManager by inject()

    @Before
    fun before() {
        startKoin { modules(managerInjection) }
    }
    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun approximateDistanceOfMeterTest() {
        trackMapManager.approximateDistanceOfMeter(locationA, locationB).let { distance ->
            print(distance)
        }
    }

}