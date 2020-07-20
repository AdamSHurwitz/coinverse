package app.coinverse.feedViewModel.tests

// Todo: Refactor with Model-View-Intent.

/*@ExperimentalCoroutinesApi
@ExtendWith(ContentTestExtension::class)
class NavigateContentTests(
        val testDispatcher: TestCoroutineDispatcher,
        val testScope: TestCoroutineScope
) {

    private fun NavigateContent() = navigateContentTestCases()
    private val repository = mockkClass(FeedRepository::class)
    private val analytics = mockkClass(Analytics::class)
    private lateinit var feedViewModel: FeedViewModel

    @ParameterizedTest
    @MethodSource("NavigateContent")
    fun `Navigate Content`(test: NavigateContentTest) = testDispatcher.runBlockingTest {
        mockComponents(test)
        feedViewModel = FeedViewModel(
                coroutineScopeProvider = testScope,
                feedType = test.feedType,
                timeframe = test.timeframe,
                isRealtime = test.isRealtime,
                repository = repository,
                analytics = analytics)
        println("NavigateContent: ${test.mockContent.contentType}")
        assertContentList(test)
        FeedViewState.ShareContent(test.mockContent).also { event ->
            feedViewModel.shareContent(event)
            assertThat(feedViewModel.effect.shareContentIntent.getOrAwaitValue().contentRequest.getOrAwaitValue())
                    .isEqualTo(test.mockContent)
        }
        FeedViewState.OpenContentSource(test.mockContent.url).also { event ->
            feedViewModel.openContentSource(event)
            assertThat(feedViewModel.effect.openSourceIntent.getOrAwaitValue())
                    .isEqualTo(OpenSourceIntentEffect(test.mockContent.url))
        }
        // Occurs on Fragment 'onViewStateRestored'
        FeedViewState.UpdateAds().also { event ->
            feedViewModel.updateAds(event)
            assertThat(feedViewModel.effect.updateAds.getOrAwaitValue().javaClass).isEqualTo(UpdateAdsEffect::class.java)
        }
        verifyTests(test)
    }

    private fun mockComponents(test: NavigateContentTest) {
        // Coinverse - ContentRepository
        coEvery {
            repository.getMainFeedNetwork(test.isRealtime, any())
        } returns mockGetMainFeedList(test.mockFeedList, SUCCESS)
        every {
            repository.getLabeledFeedRoom(test.feedType)
        } returns mockQueryMainContentListFlow(test.mockFeedList)
        every { repository.getContent(test.mockContent.id) } returns mockGetContent(test)
    }

    private fun assertContentList(test: NavigateContentTest) {
        feedViewModel.state.feedList.getOrAwaitValue().also { pagedList ->
            assertThat(pagedList).isEqualTo(test.mockFeedList)
        }
    }

    private fun verifyTests(test: NavigateContentTest) {
        coVerify {
            when (test.feedType) {
                MAIN -> repository.getMainFeedNetwork(test.isRealtime, any())
                SAVED, DISMISSED -> repository.getLabeledFeedRoom(test.feedType)
            }
        }
    }
}*/
