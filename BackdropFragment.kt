package com.example.settings


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

lateinit var ll_notification:LinearLayout
lateinit var ll_about:LinearLayout
lateinit var ll_profile:LinearLayout
lateinit var ll_settings:LinearLayout
lateinit var ll_sharing:LinearLayout

open class RoundedBottomSheetDialogFragment : BottomSheetDialogFragment()  {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(
        requireContext(),
        theme
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {


        // get the views and attach the listener
        val view = inflater.inflate(R.layout.fragment_backdrop, container, false)
        ll_notification = view.findViewById(R.id.ll_notification)
        ll_settings = view.findViewById(R.id.ll_settings)
        ll_profile = view.findViewById(R.id.ll_profile)
        ll_about = view.findViewById(R.id.ll_about)
        ll_sharing = view.findViewById(R.id.ll_sharing)

        ll_notification.setOnClickListener {
            Toast.makeText(activity as Context, "Clicked on notification", Toast.LENGTH_SHORT).show()
        }
        ll_settings.setOnClickListener{
            val intent=Intent(view.context, SettingsActivity::class.java)
            startActivity(intent)
        }
        ll_profile.setOnClickListener{
            val intent=Intent(view.context, ProfileActivity::class.java)
            startActivity(intent)
        }
        ll_about.setOnClickListener{
            val intent=Intent(view.context, About::class.java)
            startActivity(intent)
        }
        ll_sharing.setOnClickListener{
            Toast.makeText(activity as Context,"Clicked on Sharing",Toast.LENGTH_SHORT).show()
        }


        return  view
}

    companion object {
        fun newInstance(): RoundedBottomSheetDialogFragment {
            return RoundedBottomSheetDialogFragment()

        }

    }

}
