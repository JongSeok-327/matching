package com.bae.matching.views.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bae.matching.R
import com.bae.matching.databinding.DialogSelectThemeBinding
import com.bae.matching.databinding.FragmentMyPageBinding
import com.bae.matching.utils.Dlog
import com.bae.matching.utils.MyPageItem
import com.bae.matching.utils.SharedPreferencesHelper
import com.bae.matching.utils.THEME_DARK_MODE
import com.bae.matching.utils.THEME_DEFAULT_MODE
import com.bae.matching.utils.THEME_LIGHT_MODE
import com.bae.matching.utils.ThemeUtil
import com.bae.matching.views.adapter.MyPageListAdapter

class MyPageFragment : Fragment()
{
    private var _binding: FragmentMyPageBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recycler.apply {
                adapter = MyPageListAdapter {
                    when (it) {
                        MyPageItem.MY_INFORMATION -> {
                            Dlog.d("My Information selected")
                        }
                        MyPageItem.THEME -> {
                            Dlog.d("Theme selected")
                            showThemeSelectionDialog()
                        }
                    }

                }
            }
        }
    }

    private fun showThemeSelectionDialog() {
        context?.let { mContext ->
            val dialogView = DialogSelectThemeBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(context)
                .setView(dialogView.root)
                .setCancelable(true)
            val dialog = alertDialogBuilder.create()

            dialogView.apply {
                // Checked Radio Button
                when (SharedPreferencesHelper(mContext).getTheme()) {
                    THEME_LIGHT_MODE -> radioGroup.check(R.id.radio_light_theme)
                    THEME_DARK_MODE -> radioGroup.check(R.id.radio_dark_theme)
                    THEME_DEFAULT_MODE -> radioGroup.check(R.id.radio_default_theme)
                }

                btnConfirm.setOnClickListener {
                    val selectedTheme = when (radioGroup.checkedRadioButtonId) {
                        R.id.radio_light_theme -> THEME_LIGHT_MODE
                        R.id.radio_dark_theme -> THEME_DARK_MODE
                        else -> THEME_DEFAULT_MODE
                    }

                    ThemeUtil.applyTheme(selectedTheme)
                    SharedPreferencesHelper(mContext).saveTheme(selectedTheme)
                    dialog.dismiss()
                }

                btnCancel.setOnClickListener {
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}