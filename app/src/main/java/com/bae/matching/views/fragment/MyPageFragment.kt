package com.bae.matching.views.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.bae.matching.R
import com.bae.matching.databinding.FragmentMyPageBinding
import com.bae.matching.utils.Dlog
import com.bae.matching.utils.MyPageItem
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
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_select_theme, null)

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Select Theme")
            .setCancelable(true)

        val alertDialog = alertDialogBuilder.create()

        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        val themeRadioGroup = dialogView.findViewById<RadioGroup>(R.id.themeRadioGroup)

        confirmButton.setOnClickListener {
            val selectedTheme = when (themeRadioGroup.checkedRadioButtonId) {
                R.id.lightThemeRadioButton -> THEME_LIGHT_MODE
                R.id.darkThemeRadioButton -> THEME_DARK_MODE
                else -> THEME_DEFAULT_MODE
            }

            ThemeUtil.applyTheme(selectedTheme)
            alertDialog.dismiss()
        }

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}