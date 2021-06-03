package com.example.todoapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCreateTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.util.NotificationHelper
import com.example.todoapp.util.TodoWorker
import com.example.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*
import kotlinx.android.synthetic.main.fragment_create_todo.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(), RadioClickListener, ButtonAddTodoClickListener,
    DateClickListener, TimeCLickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_todo, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.todo = Todo("", "", 3, 0, 0)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        dataBinding.listener = this
        dataBinding.radioListener = this
        dataBinding.dateListener = this
        dataBinding.timeListener = this

//        btnAdd.setOnClickListener {
//            var radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
//            var todo = Todo(
//                txtTitle.text.toString(),
//                txtNotes.text.toString(),
//                radio.tag.toString().toInt(),
//                0
//            )
//            val list = listOf(todo)
//            viewModel.addTodo(list)
//            NotificationHelper(view.context).createNotification(
//                "Todo Created",
//                "A new todo has been created! Stay Focus"
//            )
//            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//                .setInitialDelay(30, TimeUnit.SECONDS)
//                .setInputData(
//                    workDataOf(
//                        "title" to "Todo Created",
//                        "message" to "A new todo has been created! Stay Focus"
//                    )
//                )
//                .build()
//            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
//            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
//            Navigation.findNavController(it).popBackStack()
//        }
    }

    override fun onButtonAddTodoClick(v: View) {
        val c = Calendar.getInstance()
        c.set(year,month,day,hour,minute,0)
        val today = Calendar.getInstance()
        val diff = (c.timeInMillis/1000L) - (today.timeInMillis/1000L)
        dataBinding.todo!!.todo_date = (c.timeInMillis/1000L).toInt()
        val list = listOf(dataBinding.todo!!)
        viewModel.addTodo(list)
        Toast.makeText(v.context, "Data added", Toast.LENGTH_LONG).show()
        val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
            .setInitialDelay(diff, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to "Todo Created",
                    "message" to "A new todo has been created! Stay Focus"
                )
            )
            .build()
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        Navigation.findNavController(v).popBackStack()
    }

    override fun onRadioClick(v: View, obj: Todo) {
        obj.priority = v.tag.toString().toInt()
    }

    override fun onDateClick(v: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        activity?.let { it1 ->
            DatePickerDialog(it1, this, year, month, day).show()
        }
    }

    override fun onTimeCLick(v: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Calendar.getInstance().let {
            it.set(year, month, dayOfMonth)
            dataBinding.root.txtDate.setText(
                day.toString()
                    .padStart(2, '0') + "-" + month.toString().padStart(2, '0') + "-" + year
            )
            this.year = year
            this.month = month
            this.day = dayOfMonth
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dataBinding.root.txtTime.setText(
            hourOfDay.toString().padStart(2, '0') + ":" + minute.toString().padStart(2, '0')
        )
        hour = hourOfDay
        this.minute = minute
    }
}