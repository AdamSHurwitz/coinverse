package app.carpecoin.content

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import app.carpecoin.Enums
import app.carpecoin.Enums.FeedType
import app.carpecoin.Enums.FeedType.*
import app.carpecoin.Enums.Timeframe
import app.carpecoin.Enums.UserActionType
import app.carpecoin.content.models.Content
import app.carpecoin.utils.Constants.PAGE_SIZE
import app.carpecoin.utils.Constants.PREFETCH_DISTANCE
import app.carpecoin.utils.DateAndTime.getTimeframe
import com.google.firebase.auth.FirebaseUser


class ContentViewModel(application: Application) : AndroidViewModel(application) {

    var contentRepository: ContentRepository
    var feedType = NONE.name
    //TODO: Add isRealtime Boolean for paid feature.
    var timeframe = MutableLiveData<Timeframe>()
    var mainFeedEmptied = MutableLiveData<Boolean>()
    val pagedListConfiguration = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setPageSize(PAGE_SIZE)
            .build()

    init {
        contentRepository = ContentRepository(application)
        timeframe.value = Enums.Timeframe.WEEK
    }

    fun initializeMainContent(isRealtime: Boolean) {
        contentRepository.initializeMainRoomContent(isRealtime, timeframe.value!!)
    }

    fun initializeCategorizedContent(feedType: String, userId: String) {
        contentRepository.initializeCategorizedRoomContent(feedType, userId)
    }

    fun getMainContentList(): LiveData<PagedList<Content>> {
        return LivePagedListBuilder(
                contentRepository.getMainContent(getTimeframe(timeframe.value)),
                pagedListConfiguration).build()
    }

    fun getCategorizedContentList(feedType: FeedType): LiveData<PagedList<Content>> {
        return LivePagedListBuilder(contentRepository.getCategorizedContent(feedType),
                pagedListConfiguration).build()
    }

    fun getToolbarVisibility(): Int {
        when (feedType) {
            SAVED.name, ARCHIVED.name -> return View.VISIBLE
            else -> return View.GONE
        }
    }

    var contentSelected = MutableLiveData<Content>()

    fun contentClicked(content: Content) {
        contentSelected.value = content
    }

    fun organizeContent(feedType: String, actionType: UserActionType, user: FirebaseUser,
                        content: Content?, mainFeedEmptied: Boolean) {
        contentRepository.organizeContent(feedType, actionType, content, user, mainFeedEmptied)
    }

    fun updateActions(actionType: UserActionType, content: Content, user: FirebaseUser) {
        contentRepository.updateActionsStatusCheck(actionType, content, user)
    }

}