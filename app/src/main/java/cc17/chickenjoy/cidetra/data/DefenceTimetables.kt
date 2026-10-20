package cc17.chickenjoy.cidetra.data

import androidx.compose.ui.graphics.Color
import de.tobiasschuerg.weekview.model.Event
import de.tobiasschuerg.weekview.model.LocalDateRange
import de.tobiasschuerg.weekview.model.TimeSpan
import de.tobiasschuerg.weekview.model.WeekData
import java.time.LocalDate
import java.time.LocalTime
import cc17.chickenjoy.cidetra.data.*
import de.tobiasschuerg.weekview.model.Period

val dayColours = listOf(
    Color(0xFF0D47A1),
    Color(0xFFB71C1C),
    Color(0xFF1B5E20),
    Color(0xFFFF6F00),
    Color(0xFF005564),
    Color(0xFF4A148C),
    Color(0xFF7D8492),
)

enum class DefenceRoom {
    M301, M303, M304, M305, M306, M307, S311, S312
}
/**
 * Entry point for the demo app's selectable sample timetables. Each timetable's data
 * lives in its own `SampleTimetable*` object; this just dispatches to them.
 */
object SampleTimetables {


    enum class Timetable(
        val days: Int = 6,
    ) {
        M301, M303, M304, M305, M306, M307, S311, S312
    }

    fun create(
        timetable: Timetable,
        dateRange: LocalDateRange,
    ): WeekData {
        return when (timetable) {
            Timetable.M301 -> DefencesM301.create(dateRange)
            Timetable.M303 -> DefencesM303.create(dateRange)
            Timetable.M304 -> DefencesM304.create(dateRange)
            Timetable.M305 -> DefencesM305.create(dateRange)
            Timetable.M306 -> DefencesM306.create(dateRange)
            Timetable.M307 -> DefencesM307.create(dateRange)
            Timetable.S311 -> DefencesS311.create(dateRange)
            Timetable.S312 -> DefencesS312.create(dateRange)
        }
    }
}

val periods = listOf(
    Period("1", TimeSpan(LocalTime.of(7, 30), LocalTime.of(8, 50))),
    Period("2", TimeSpan(LocalTime.of(8, 50), LocalTime.of(10, 10))),
    Period("3", TimeSpan(LocalTime.of(10, 10), LocalTime.of(11, 30))),
    Period("4", TimeSpan(LocalTime.of(11, 30), LocalTime.of(12, 50))),
    Period("5", TimeSpan(LocalTime.of(12, 50), LocalTime.of(14, 10))),
    Period("6", TimeSpan(LocalTime.of(14, 10), LocalTime.of(15, 30))),
    Period("7", TimeSpan(LocalTime.of(15, 30), LocalTime.of(16, 50))),
    Period("8", TimeSpan(LocalTime.of(16, 50), LocalTime.of(18, 10))),
    Period("9", TimeSpan(LocalTime.of(18, 10), LocalTime.of(19, 30))),
)

/** Shared helper for the compact `lesson(...)` call style used by [SampleTimetableSchool], [SampleTimetableSpecialCases] and [SampleTimetableShortDay]. */

internal fun Defence(
    id: Long,
    date: LocalDate,
    projTitle: String,
    authors: String,
    period: Int,
    colour: Color,
    textColour: Color = Color.White,
): Event.Single =
    Event.Single(
        id = id.toString(),
        date = date,
        title = "$authors: $projTitle",
        shortTitle = authors,
        timeSpan = periods.elementAt(period-1).timeSpan,
        textColor = textColour,
        backgroundColor = colour,
    )