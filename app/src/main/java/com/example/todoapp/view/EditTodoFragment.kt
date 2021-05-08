package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentEditTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

class EditTodoFragment : Fragment(), RadioClickListener, SaveChangesListerner {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentEditTodoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        dataBinding.radioListener = this
        dataBinding.listener = this
        observeViewModel()
//        btnAdd.setOnClickListener {
//            val radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
//            viewModel.update(
//                txtTitle.text.toString(),
//                txtNotes.text.toString(),
//                radio.tag.toString().toInt(),
//                0,
//                uuid
//            )
//            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
//        }

        txtJudulTodo.text = "Edit Todo"
        btnAdd.text = "Save Changes"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentEditTodoBinding>(
            inflater,
            R.layout.fragment_edit_todo,
            container,
            false
        )
        return dataBinding.root
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
//            when (it.priority) {
//                1 -> radioLow.isChecked = true
//                2 -> radioMedium.isChecked = true
//                else -> radioHigh.isChecked = true
//            }
            dataBinding.todo = it
//            txtTitle.setText(it.title)
//            txtNotes.setText(it.notes)
        })
    }

    override fun onRadioClick(v: View, obj: Todo) {
        obj.priority = v.tag.toString().toInt()
    }

    override fun onSaveChanges(v: View, obj: Todo) {
        viewModel.update(
            obj.title, obj.notes, obj.priority, obj.is_done, obj.uuid
        )
        Toast.makeText(v.context, "Todo updated", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(v).popBackStack()
    }
}