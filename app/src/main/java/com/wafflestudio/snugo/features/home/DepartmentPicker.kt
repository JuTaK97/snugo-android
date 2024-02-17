package com.wafflestudio.snugo.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.flow.filter

enum class Department {
    MAIN_GATE,
    BUSINESS,
    SOCIAL,
    AGRICULTURE,
    DOWN_ENG_1,
    DOWN_ENG_2,
    UP_ENG,
    NATURE,
    EDUCATION,
    DORMITORY,
    RESEARCH_PARK,
}

val polygonMap =
    mapOf(
        Department.MAIN_GATE to
            listOf(
                LatLng(37.46552990680509, 126.94851529307664),
                LatLng(37.46685649206763, 126.94829128055403),
                LatLng(37.4672141678519, 126.94929128991294),
                LatLng(37.468117135341096, 126.95096699383384),
                LatLng(37.46894203347994, 126.95183955312535),
                LatLng(37.469377148594916, 126.95207553753386),
                LatLng(37.46908136878915, 126.95342317441344),
                LatLng(37.467311601467074, 126.95434041526113),
                LatLng(37.465815883956296, 126.95595103485175),
                LatLng(37.46572137175329, 126.95595358987629),
                LatLng(37.46519539873736, 126.95552881811085),
                LatLng(37.46525097722294, 126.95518500452124),
                LatLng(37.46577255457508, 126.95496387851716),
                LatLng(37.46615914662623, 126.95436679586567),
                LatLng(37.466164228712245, 126.95352613964042),
                LatLng(37.46598445637039, 126.95141598596575),
                LatLng(37.4655182477145, 126.94853226503295),
            ),
        Department.SOCIAL to
            listOf(
                LatLng(37.46550393766937, 126.94862272167563),
                LatLng(37.46284322714311, 126.94913063595618),
                LatLng(37.46264556180635, 126.95058414017626),
                LatLng(37.46214297887874, 126.95085885404865),
                LatLng(37.46121784192627, 126.95196899476656),
                LatLng(37.46185539808476, 126.9525440416661),
                LatLng(37.46250281046299, 126.95241458913029),
                LatLng(37.46355787369484, 126.9520140564955),
                LatLng(37.46448138793245, 126.95134663761326),
                LatLng(37.465841161424706, 126.95138930776051),
                LatLng(37.465554119758785, 126.94854867306856),
            ),
        Department.BUSINESS to
            listOf(
                LatLng(37.46272689437007, 126.94921977213022),
                LatLng(37.46267233549381, 126.95063492363101),
                LatLng(37.4611329377432, 126.95210621537814),
                LatLng(37.460666366974024, 126.9514921441841),
                LatLng(37.460260821776295, 126.95190028197754),
                LatLng(37.45971319386872, 126.9529935888184),
                LatLng(37.45936676684041, 126.9531020289349),
                LatLng(37.45960117725688, 126.95437855838105),
                LatLng(37.4592208883945, 126.954686279989),
                LatLng(37.458693896200295, 126.95322411253738),
                LatLng(37.45803039409386, 126.95254866063794),
                LatLng(37.457092928366826, 126.95206643864452),
                LatLng(37.45750923737552, 126.95180858221113),
                LatLng(37.45758198724619, 126.95151687296806),
                LatLng(37.45718002690582, 126.95133148686017),
                LatLng(37.45741725213598, 126.95047980954382),
                LatLng(37.457343704504986, 126.9492631561161),
                LatLng(37.45950600536093, 126.94871207765101),
                LatLng(37.46168527834936, 126.94940380019966),
                LatLng(37.4626441047029, 126.94922880233702),
            ),
        Department.AGRICULTURE to
            listOf(
                LatLng(37.46063507801415, 126.94893279427652),
                LatLng(37.45963759355272, 126.94859216323528),
                LatLng(37.45593190604245, 126.94950560843279),
                LatLng(37.45574848131875, 126.94872788268867),
                LatLng(37.45692866929351, 126.94783762189127),
                LatLng(37.458331949358936, 126.947746530769),
                LatLng(37.45958460078438, 126.9474604592852),
                LatLng(37.46067351574821, 126.94858431928805),
                LatLng(37.46062048072302, 126.94894352315151),
            ),
        Department.DOWN_ENG_1 to
            listOf(
                LatLng(37.45732726440422, 126.94921685264671),
                LatLng(37.45333538541419, 126.9503329183608),
                LatLng(37.453375921272496, 126.95116319497987),
                LatLng(37.4538630875726, 126.95112338878772),
                LatLng(37.45365542699258, 126.95199322941357),
                LatLng(37.4552833263511, 126.95175115576978),
                LatLng(37.45631206171104, 126.95237223846306),
                LatLng(37.45698734614672, 126.9522729320409),
                LatLng(37.45740320131659, 126.95186543786082),
                LatLng(37.45753837714707, 126.95155908885425),
                LatLng(37.457267852361014, 126.9513734954009),
                LatLng(37.45746343415796, 126.95056232380347),
                LatLng(37.457385770581865, 126.95043231784962),
                LatLng(37.45733280065077, 126.94923312500424),
            ),
        Department.DOWN_ENG_2 to
            listOf(
                LatLng(37.45368291934703, 126.95192964050955),
                LatLng(37.45527763880004, 126.95181006659504),
                LatLng(37.456214723640514, 126.95237503577533),
                LatLng(37.457029178804085, 126.95223367330942),
                LatLng(37.458049576445724, 126.95252226903057),
                LatLng(37.45869315839593, 126.95319296208226),
                LatLng(37.45769361607655, 126.95433069131457),
                LatLng(37.45687686104439, 126.95504034896567),
                LatLng(37.45624230714513, 126.95483626385482),
                LatLng(37.45600415944916, 126.95513474935944),
                LatLng(37.45377059578522, 126.9535264961475),
                LatLng(37.453519300182464, 126.95341229869058),
                LatLng(37.453309783065635, 126.95324237780636),
                LatLng(37.45321482187525, 126.95270294616961),
                LatLng(37.45369716325049, 126.95200528158739),
            ),
        Department.UP_ENG to
            listOf(
                LatLng(37.44797946939286, 126.94914767646054),
                LatLng(37.44761663787207, 126.94923805953505),
                LatLng(37.44733394810605, 126.94955179301877),
                LatLng(37.447047919498424, 126.95090423836632),
                LatLng(37.447604416755055, 126.95192267231596),
                LatLng(37.44807826773021, 126.952059817524),
                LatLng(37.448180418060446, 126.95272816475273),
                LatLng(37.44855242724977, 126.9530664303299),
                LatLng(37.45010905205322, 126.95287365540133),
                LatLng(37.450784742504545, 126.95302751522598),
                LatLng(37.45139609601515, 126.95314845439361),
                LatLng(37.45170863137236, 126.95274017144249),
                LatLng(37.45197847795291, 126.9528020050385),
                LatLng(37.452152276890104, 126.95224004766078),
                LatLng(37.451676615578926, 126.95208000813977),
                LatLng(37.451682713840434, 126.9515907963085),
                LatLng(37.4513884732326, 126.9512325258097),
                LatLng(37.451121673589086, 126.95133618778397),
                LatLng(37.450811006420686, 126.95142426217149),
                LatLng(37.45013713993529, 126.95044492901951),
                LatLng(37.45010054995602, 126.9499302561619),
                LatLng(37.449269650475465, 126.94970608408323),
                LatLng(37.447971387154446, 126.9491675270686),
            ),
        Department.NATURE to
            listOf(
                LatLng(37.457096612707986, 126.95216112075667),
                LatLng(37.458141093762485, 126.95263379667136),
                LatLng(37.45882847864982, 126.95337822912313),
                LatLng(37.45922880940416, 126.95466139367943),
                LatLng(37.45953553151674, 126.95453346895147),
                LatLng(37.459705128942325, 126.95423598546415),
                LatLng(37.45927995783446, 126.95303632239154),
                LatLng(37.45977029082625, 126.95296763427524),
                LatLng(37.460302685959476, 126.95183118949609),
                LatLng(37.46060008008605, 126.95166258405504),
                LatLng(37.4608610022552, 126.95125064716626),
                LatLng(37.4600346924496, 126.95053259636938),
                LatLng(37.46058815792194, 126.94914663499367),
                LatLng(37.45963274582578, 126.94881502169761),
                LatLng(37.45738063503449, 126.94929673306768),
                LatLng(37.457333253632875, 126.95043094642318),
                LatLng(37.45733329046021, 126.9510341583483),
                LatLng(37.457294637371476, 126.9514052880009),
                LatLng(37.4574355340238, 126.95180616713975),
                LatLng(37.45710137201327, 126.95214829071023),
            ),
        Department.EDUCATION to
            listOf(
                LatLng(37.46063662604612, 126.9515894656115),
                LatLng(37.46119434077295, 126.95216482569515),
                LatLng(37.461078736739815, 126.95282660404757),
                LatLng(37.46117559737644, 126.95341047911666),
                LatLng(37.461181228061875, 126.95480067020287),
                LatLng(37.460814141460055, 126.9560039096367),
                LatLng(37.460289527590064, 126.95680852731226),
                LatLng(37.45886302330611, 126.95530271263499),
                LatLng(37.459149734477585, 126.95483440789536),
                LatLng(37.45975444935516, 126.95426809530875),
                LatLng(37.45923941728038, 126.95292017978801),
                LatLng(37.45973605370042, 126.95297195107798),
                LatLng(37.4599807544344, 126.95261098982974),
                LatLng(37.460274511228235, 126.95189132787152),
                LatLng(37.46061202692875, 126.95164397584654),
            ),
        Department.DORMITORY to
            listOf(
                LatLng(37.461306773616705, 126.95632925876555),
                LatLng(37.46275587751286, 126.95766786299924),
                LatLng(37.463599330729764, 126.95796888077916),
                LatLng(37.46462532790013, 126.95792688442617),
                LatLng(37.46480921101344, 126.95865956545668),
                LatLng(37.464436240244034, 126.95955782435908),
                LatLng(37.464050236453446, 126.96019676824534),
                LatLng(37.46254791822291, 126.95937508915182),
                LatLng(37.46157029824846, 126.95937281688083),
                LatLng(37.46059461561376, 126.95895252007358),
                LatLng(37.460128772132784, 126.95857836830163),
                LatLng(37.45978322047525, 126.95732094277463),
                LatLng(37.46124040191861, 126.95635400769498),
            ),
        Department.RESEARCH_PARK to
            listOf(
                LatLng(37.46462393308708, 126.95798924876897),
                LatLng(37.464891351282915, 126.95899202263149),
                LatLng(37.46437070229753, 126.959269502641),
                LatLng(37.46366687634022, 126.959747642396),
                LatLng(37.463971054358595, 126.96015823345073),
                LatLng(37.46435071973735, 126.95985625038497),
                LatLng(37.46487545166515, 126.96057513141415),
                LatLng(37.46518628534698, 126.96117048845952),
                LatLng(37.46564203085301, 126.96133128111774),
                LatLng(37.46705521337053, 126.96097165995866),
                LatLng(37.46817398524804, 126.96039808045879),
                LatLng(37.46849226499827, 126.95813799100841),
                LatLng(37.46851900652348, 126.95776895789817),
                LatLng(37.46883093511327, 126.95778286497426),
                LatLng(37.46863390938722, 126.95674331240895),
                LatLng(37.467951215409045, 126.95676317973721),
                LatLng(37.46770477777375, 126.95645324973998),
                LatLng(37.467117543046314, 126.95660291680235),
                LatLng(37.46660076803793, 126.95719899156751),
                LatLng(37.466606318573085, 126.95762084064893),
                LatLng(37.46595974308336, 126.95769550502501),
                LatLng(37.46592310962071, 126.95699154020593),
                LatLng(37.4647042022523, 126.95694518314275),
                LatLng(37.46465757710585, 126.95794318420383),
            ),
    )

fun Department.color(): Color =
    when (this) {
        Department.DOWN_ENG_2 -> Color(247, 199, 62)
        Department.SOCIAL -> Color(102, 104, 172)
        Department.BUSINESS -> Color(113, 197, 95)
        Department.DORMITORY -> Color(126, 194, 162)
        Department.AGRICULTURE -> Color(240, 121, 180)
        Department.EDUCATION -> Color(177, 219, 84)
        Department.DOWN_ENG_1 -> Color(238, 98, 68)
        Department.RESEARCH_PARK -> Color(210, 208, 198)
        Department.UP_ENG -> Color(242, 127, 68)
        Department.NATURE -> Color(176, 141, 189)
        Department.MAIN_GATE -> Color(159, 222, 226)
    }

@Composable
fun DepartmentPicker(
    initialIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        lazyListState.scrollToItem(initialIndex)
        snapshotFlow { lazyListState.isScrollInProgress }
            .filter { it.not() }
            .collect {
                val selectedIndex =
                    lazyListState.layoutInfo.visibleItemsInfo.find {
                        it.offset == 0
                    }?.index ?: 0
                onItemSelected(selectedIndex)
            }
    }

    Box {
        LazyColumn(
            state = lazyListState,
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(DepartmentPickerConstants.ITEM_HEIGHT_DP.dp * 3),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
            contentPadding = PaddingValues(vertical = DepartmentPickerConstants.ITEM_HEIGHT_DP.dp),
        ) {
            items(Department.entries.map { it.name }) {
                Box(
                    modifier =
                        Modifier
                            .height(DepartmentPickerConstants.ITEM_HEIGHT_DP.dp),
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.align(Alignment.Center),
                        style =
                            TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            ),
                    )
                }
            }
        }
        Box(
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(DepartmentPickerConstants.ITEM_HEIGHT_DP.dp)
                    .background(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(10.dp),
                    ),
        )
    }
}
