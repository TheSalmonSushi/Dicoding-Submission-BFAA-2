package com.salmonboy.dicodingevent.ui.detail

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.salmonboy.dicodingevent.R
import com.salmonboy.dicodingevent.data.Result
import com.salmonboy.dicodingevent.data.local.entity.EventDetailEntity
import com.salmonboy.dicodingevent.databinding.ActivityEventDetailBinding
import com.salmonboy.dicodingevent.ui.viewmodel.ViewModelFactory
import java.util.Locale

class EventDetailActivity : AppCompatActivity() {

    private var _binding: ActivityEventDetailBinding? =null
    private val binding get() = _binding

    private val eventDetailViewModel: EventDetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val eventId = intent.getStringExtra("EVENT_ID")?.toIntOrNull()
        if (eventId == null) {
            Toast.makeText(this, "Invalid Event ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupUI(eventId)
        setupObservers(eventId)

        ViewCompat.setOnApplyWindowInsetsListener(binding?.main!!) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupUI(eventId: Int) {
        binding?.ivBookmark?.setOnClickListener {
            val isBookmarked = binding?.ivBookmark?.tag as? Boolean ?: false
            toggleBookmark(eventId, isBookmarked)
        }
    }

    private fun setupObservers(eventId: Int) {
        eventDetailViewModel.getDetailDicodingEvent(eventId.toString()).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    bindItem(result.data)
                }
                is Result.Error -> {
                    binding?.progressBar?.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error: ${result.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun toggleBookmark(eventId: Int, isBookmarked: Boolean) {
        eventDetailViewModel.toggleBookmark(eventId, !isBookmarked)
        val newBookmarkState = !isBookmarked
        val bookmarkDrawable = if (newBookmarkState) {
            R.drawable.favorite_fill_24dp
        } else {
            R.drawable.favorite_24dp
        }

        binding?.ivBookmark?.setImageDrawable(
            ContextCompat.getDrawable(this, bookmarkDrawable)
        )
        binding?.ivBookmark?.tag = newBookmarkState

        val message = if (newBookmarkState) {
            "Added to bookmarks!"
        } else {
            "Removed from bookmarks!"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun bindItem(eventDetail: EventDetailEntity) {
        // Title and Organizer
        binding?.tvEventTitle?.text = eventDetail.name
        val text = "Diselenggarakan Oleh \n${eventDetail.ownerName}"
        binding?.tvEventOrganiser?.text = text

        // Description
        binding?.tvEventDesc?.text = HtmlCompat.fromHtml(
            eventDetail.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        // Date, Quota, Registrants, and Image
        val formattedDate = formatDate(eventDetail.beginTime)
        binding?.tvEventDate?.text = formattedDate

        val formattedQuotaLeft = "Sisa Kuota: ${eventDetail.quota - eventDetail.registrants}"
        binding?.tvEventQuota?.text = formattedQuotaLeft

        Glide.with(this).load(eventDetail.imageLogo).into(binding?.ivItemPhoto!!)

        // Bookmark Icon
        val bookmarkDrawable = if (eventDetail.isBookmarked) {
            R.drawable.favorite_fill_24dp
        } else {
            R.drawable.favorite_24dp
        }
        binding?.ivBookmark?.setImageDrawable(
            ContextCompat.getDrawable(this, bookmarkDrawable)
        )
        binding?.ivBookmark?.tag = eventDetail.isBookmarked

        // Link Button
        binding?.linkEventButton?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventDetail.link))
            startActivity(intent)
        }
    }

    private fun formatDate(apiDate: String): String {
        val apiDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val desiredDateFormat = SimpleDateFormat("dd MMMM yyyy\n HH:mm", Locale.getDefault())

        return try {
            val date = apiDateFormat.parse(apiDate)
            desiredDateFormat.format(date)
        } catch (e: Exception) {
            apiDate
        }
    }
}