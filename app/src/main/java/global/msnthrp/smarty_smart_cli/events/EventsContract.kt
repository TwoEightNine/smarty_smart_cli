package global.msnthrp.smarty_smart_cli.events

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

interface EventsContract {

    interface View : MvpLceView<ArrayList<Event>>

    interface Presenter : MvpPresenter<View>
}