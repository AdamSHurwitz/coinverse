package app.coinverse.feed.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.coinverse.R.id.*
import app.coinverse.databinding.CellContentBinding
import app.coinverse.feed.models.Content
import app.coinverse.feed.models.FeedViewEventType.*
import app.coinverse.feed.models.FeedViewEvents
import app.coinverse.feed.viewmodel.FeedViewModel

private val LOG_TAG = FeedAdapter::class.java.simpleName

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Content>() {
    override fun areItemsTheSame(oldContent: Content, newContent: Content): Boolean =
            oldContent.id == newContent.id

    override fun areContentsTheSame(oldContent: Content, newContent: Content): Boolean =
            oldContent == newContent
}

class FeedAdapter(val viewModel: FeedViewModel, val viewEvents: FeedViewEvents)
    : PagedListAdapter<Content, FeedAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private var binding: CellContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: FeedViewModel, content: Content, onClickListener: OnClickListener) {
            binding.viewModel = viewModel
            binding.data = content
            binding.clickListener = onClickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CellContentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { content ->
            holder.bind(viewModel, content, createOnClickListener(content, position))
        }
    }

    private fun createOnClickListener(content: Content, position: Int) = OnClickListener { view ->
        when (view.id) {
            preview, contentTypeLogo -> {
                val contentSelected = ContentSelected(content, position)
                viewEvents.contentSelected(contentSelected)
            }
            share -> viewEvents.contentShared(ContentShared(content))
            openSource -> viewEvents.contentSourceOpened(ContentSourceOpened(content.url))
        }
    }

    fun getContent(position: Int) = getItem(position)
}