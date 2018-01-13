package com.bigscreen.mangindo.content

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.databinding.FragmentMangaContentBinding
import com.bigscreen.mangindo.listener.OnContentImageClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uk.co.senab.photoview.PhotoViewAttacher


class MangaContentFragment : Fragment(), PhotoViewAttacher.OnViewTapListener {

    companion object {
        const val KEY_IMAGE_URL = "IMAGE_URL"
        @JvmStatic fun getInstance(imageUrl: String): MangaContentFragment {
            val fragment = MangaContentFragment()
            val bundle = Bundle()
            bundle.putString(KEY_IMAGE_URL, imageUrl)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentMangaContentBinding
    private var fragmentPosition = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_manga_content,
                container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageManga.setOnViewTapListener(this)
        val imageUrl = arguments.getString(KEY_IMAGE_URL)
        loadImage(imageUrl)
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>,
                                             isFirstResource: Boolean): Boolean {
                        binding.progressLoading.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>,
                                                 isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        binding.progressLoading.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.imageManga)
    }

    override fun onViewTap(view: View, x: Float, y: Float) {
        if (view.id == R.id.image_manga) {
            val listener = activity as OnContentImageClickListener
            listener.OnContentImageClick(fragmentPosition)
        }
    }

    fun setFragmentPosition(fragmentPosition: Int) {
        this.fragmentPosition = fragmentPosition
    }
}