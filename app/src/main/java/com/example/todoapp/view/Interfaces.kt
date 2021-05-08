package com.example.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.example.todoapp.model.Todo

interface TodoCheckedChangeListener{
    fun onTodoCheckedChangeListener(cb:CompoundButton, isChecked:Boolean, obj: Todo)
}
interface  TodoEditClickListerner{
    fun onTodoEditClick(v:View)
}
interface RadioClickListener{
    fun onRadioClick(v:View, obj:Todo)
}
interface SaveChangesListerner{
    fun onSaveChanges(v:View, obj:Todo)
}