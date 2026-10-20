package cc17.chickenjoy.cidetra.data

import androidx.compose.ui.graphics.Color
import de.tobiasschuerg.weekview.model.Event
import de.tobiasschuerg.weekview.model.LocalDateRange
import de.tobiasschuerg.weekview.model.TimeSpan
import de.tobiasschuerg.weekview.model.WeekData
import java.time.LocalTime
import cc17.chickenjoy.cidetra.data.*

object DefencesM306 {
    fun create(dateRange: LocalDateRange): WeekData {
        val days = dateRange.toList()
        val events = mutableListOf<Event>()

        val mon = days[0]
        val tue = days.getOrNull(1)
        val wed = days.getOrNull(2)
        val thu = days.getOrNull(3)
        val fri = days.getOrNull(4)
        val sat = days.getOrNull(5)

        var nextId = 1L

        // Monday
        events.add(
            Defence(
                id = (nextId++),
                date = mon,
                projTitle = "Tabarnak Tracker",
                authors = "Carney M, Poilievre P",
                period = 5,
                colour = dayColours[0],
            )
        )

        // Tuesday
        tue?.let { d ->
            events.add(
                Defence( id = (nextId++), date = d, projTitle = "Tabarnak Tracker", authors = "Carney M, Poilievre P", period = 5, colour = dayColours[1], )
            )
        }

        // Wednesday
        wed?.let { d ->
            events.add(
                Defence( id = (nextId++), date = d, projTitle = "Tabarnak Tracker", authors = "Carney M, Poilievre P", period = 5, colour = dayColours[2], )
            )
        }

        // Thursday
        thu?.let { d ->
            events.add(
                Defence( id = (nextId++), date = d, projTitle = "Tabarnak Tracker", authors = "Carney M, Poilievre P", period = 5, colour = dayColours[3], )
            )
        }

        // Friday
        fri?.let { d ->
            events.add(
                Defence( id = (nextId++), date = d, projTitle = "Tabarnak Tracker", authors = "Carney M, Poilievre P", period = 5, colour = dayColours[4], )
            )
        }

        // Saturday
        sat?.let { d ->
            events.add(
                Defence( id = (nextId++), date = d, projTitle = "Tabarnak Tracker", authors = "Carney M, Poilievre P", period = 5, colour = dayColours[5], )
            )
        }


        return WeekData(dateRange, LocalTime.of(7, 30), LocalTime.of(19, 30), events)
    }
}