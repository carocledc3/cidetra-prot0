package cc17.chickenjoy.cidetra

import androidx.compose.material3.*
import androidx.compose.ui.text.style.TextOverflow
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cc17.chickenjoy.cidetra.ui.theme.CIDETRATheme
import de.tobiasschuerg.weekview.EventConfig
import de.tobiasschuerg.weekview.WeekLayout
import de.tobiasschuerg.weekview.WeekView
import de.tobiasschuerg.weekview.WeekViewActions
import de.tobiasschuerg.weekview.WeekViewConfig
import de.tobiasschuerg.weekview.content.TintedEventContent
import de.tobiasschuerg.weekview.model.Event
import de.tobiasschuerg.weekview.model.LocalDateRange
import de.tobiasschuerg.weekview.model.TimeSpan
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.enums.enumEntries
import cc17.chickenjoy.cidetra.data.*
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CIDETRATheme {

                       CidetraMainScreen(
                           context = LocalContext.current,
                            events = events
                        )

            }
        }
    }
}


// Timed event
val meeting = Event.Single(
    id = "1",
    date = LocalDate.of(2026, 10, 21),
    title = "Team Meeting",
    shortTitle = "Meeting",
    timeSpan = TimeSpan.of(LocalTime.of(8, 50), Duration.ofMinutes(80)),
    backgroundColor = Color.Blue,
    textColor = Color.White,
)

// All-day event
val holiday = Event.AllDay(
    id = "2",
    date = LocalDate.of(2026, 10, 20),
    title = "National Holiday",
    shortTitle = "Holiday",
    backgroundColor = Color.Green,
    textColor = Color.White,
)


// Multi-day event (renders as a spanning bar)
val conference = Event.MultiDay(
    id = "3",
    date = LocalDate.of(2026, 10, 22),
    title = "Tech Conference",
    shortTitle = "Conf",
    lastDate = LocalDate.of(2026, 10, 25),
    backgroundColor = Color.Magenta,
    textColor = Color.White,
)

val events = listOf<Event>()

fun TBAToast(context: Context) {
    Toast.makeText(context, "Don't know how to implement this yet :(", Toast.LENGTH_SHORT).show();
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CidetraMainScreen(events: List<Event>, context: Context) {
    var currentTimetable by remember { mutableStateOf(SampleTimetables.Timetable.M301) }
    var calStartDate by remember { mutableStateOf(LocalDate.of(2026, 10, 19)) }
    var calEndDate by remember { mutableStateOf(LocalDate.of(2026, 10, 24)) }
    var dropDownExpanded by remember { mutableStateOf(false) }
    val menuItemData = enumEntries<SampleTimetables.Timetable>()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    @Composable
    fun PreviousWeek() {
        TBAToast(context)
    }

    @Composable
    fun MyWeekView(events: List<Event>) {

        val dateRange = LocalDateRange(
            calStartDate,
            calEndDate
        )
        var weekData by remember(currentTimetable) { mutableStateOf(SampleTimetables.create(currentTimetable, dateRange)) }
        var nextId = 1200L

        WeekView(
            weekData = weekData,
            weekViewConfig = WeekViewConfig(
                locale = Locale(/* language = */ "EN-GB")
            ),
            eventConfig = EventConfig(
                showTimeEnd = false, showTimeStart = false,
            ),
            layout = WeekLayout.Periods(periods),
            actions = WeekViewActions(
                    onEventClick = { event ->
                        Toast.makeText(context, "Clicked: ${event.title}", Toast.LENGTH_SHORT).show()
                    },
                    onEventLongPress = { event ->
                        Toast.makeText(context, "Long press: ${event.title}", Toast.LENGTH_SHORT).show()
                    },


            ),
            eventContent = { TintedEventContent(it)
            },
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())) {
                    Spacer(Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(onClick = { scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        } }) {
                            Icon(
                                painter = painterResource(R.drawable.menu_24px),
                                contentDescription = "Localized description"
                            )
                        }
                        Text("Cidetra Prototype", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                    }



                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Image(
                        painter = painterResource(R.drawable.hamilton),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)// clip to the circle shape
                            .border(2.dp, Color.Gray, CircleShape) // add a border (optional)
                    )

                    Text("Welcome", modifier = Modifier.align(Alignment.CenterHorizontally))
                    Text("ALEXANDER HAMILTON", modifier = Modifier.align(Alignment.CenterHorizontally), style = MaterialTheme.typography.titleLarge)
                    NavigationDrawerItem(
                        label = { Text("Logout") },
                        selected = false,
                        icon = { Icon(painterResource(R.drawable.logout_24px), contentDescription = null) },
//                        badge = { Text("20") }, // Placeholder
                        onClick = {throw (RuntimeException("This is a crash")) as Throwable; }
                    )
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        icon = { Icon(painterResource(R.drawable.settings_24px), contentDescription = null) },
//                        badge = { Text("20") }, // Placeholder
                        onClick = {TBAToast(context) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Help") },
                        selected = false,
                        icon = { Icon(painterResource(R.drawable.help_24px), contentDescription = null) },
//                        badge = { Text("20") }, // Placeholder
                        onClick = {TBAToast(context) }
                    )


                    Spacer(Modifier.height(12.dp))
                }
            }
        },
    ) {

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Cidetra [${currentTimetable.toString()}]",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    } }) {
                        Icon(
                            painter = painterResource(R.drawable.menu_24px),
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        IconButton(onClick = { dropDownExpanded = !dropDownExpanded }) {
                            Icon(painter = painterResource(R.drawable.meeting_room_24px), contentDescription = "More options")
                        }
                        DropdownMenu(
                            expanded = dropDownExpanded,
                            onDismissRequest = { dropDownExpanded = false }
                        ) {
                            menuItemData.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.toString()) },
                                    onClick = {
                                        currentTimetable = option;
//                                        Toast.makeText(context, "Current Room is ${option.toString()}", Toast.LENGTH_SHORT).show()
                                        dropDownExpanded = false;
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier.padding(bottom=16.dp).fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { TBAToast(context) }) {
                    Icon(painter = painterResource(R.drawable.arrow_back_24px), contentDescription = "More options")
                }
                Text("${calStartDate.format(DateTimeFormatter.ISO_LOCAL_DATE)} to ${
                    calEndDate.format(
                        DateTimeFormatter.ISO_LOCAL_DATE
                    )
                }")
                IconButton(onClick = { TBAToast(context) }) {
                    Icon(painter = painterResource(R.drawable.arrow_forward_24px), contentDescription = "More options")
                }
            }

            Box(
                modifier = Modifier.height(600.dp)
            ) {
                MyWeekView(events = events)
            }
        }
    } }


}



@Preview
@Composable
fun CalPrev() {
    CidetraMainScreen(
        context = LocalContext.current,
        events = events
    )
}
