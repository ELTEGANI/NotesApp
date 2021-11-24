package com.example.notesapp.feature_note.presentation.add_edit_notes

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.feature_note.domain.model.Note
import com.example.notesapp.feature_note.presentation.add_edit_notes.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor:Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
){
   val titleState = viewModel.noteTitle.value
   val contentState = viewModel.noteContent.value
   val scaffoldState = rememberScaffoldState()
   val noteBackgroundAnimatable = remember {
       Animatable(
           Color(if(noteColor != -1) noteColor else viewModel.noteColor.value)
       )
   }
    val scope = rememberCoroutineScope()
     LaunchedEffect(key1 = true){
         viewModel.eventFlow.collectLatest {event->
             when(event){
               is AddEditNoteViewModel.UiEvent.ShowSnackbar ->{
                   scaffoldState.snackbarHostState.showSnackbar(
                       message = event.message
                   )
               }

               is AddEditNoteViewModel.UiEvent.SaveNote ->{
                   navController.navigateUp()
               }

             }
         }
     }
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNoteEvent.saveNote)
            }
                ,backgroundColor = MaterialTheme.colors.primary) {
                Icon(imageVector = Icons.Default.Save, contentDescription ="Save Note")
            }
        },scaffoldState = scaffoldState
            ){
          Column (
              modifier = Modifier
                  .fillMaxSize()
                  .background(noteBackgroundAnimatable.value)
                  .padding(16.dp)
                  ){
                  Row(
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(8.dp),
                      horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                     Note.noteColors.forEach {color->
                         val colorInt = color.toArgb()
                         Box(
                             modifier = Modifier
                                 .size(50.dp)
                                 .shadow(15.dp, CircleShape)
                                 .clip(CircleShape)
                                 .background(color)
                                 .border(
                                     width = 3.dp,
                                     color = if (viewModel.noteColor.value == colorInt) {
                                         Color.Black
                                     } else Color.Transparent,
                                     shape = CircleShape
                                 )
                                 .clickable {
                                     scope.launch {
                                         noteBackgroundAnimatable.animateTo(
                                             targetValue = Color(colorInt),
                                             animationSpec = tween(
                                                 durationMillis = 500
                                             )
                                         )
                                     }
                                     viewModel.onEvent(AddEditNoteEvent.changeColor(colorInt))
                                 }
                         )
                     }
                  }
              Spacer(modifier = Modifier.height(16.dp))
              TransparentHintTextField(text =
                  titleState.text, hint =titleState.hint,
                  onValueChange = {
                   viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
              }, onFocusChange = {
                  viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
              },
              isHintVisible = titleState.isHintVisible,
              singleLine = true,
              textStyle = MaterialTheme.typography.h5)
              Spacer(modifier = Modifier.height(16.dp))
              TransparentHintTextField(text =
              contentState.text, hint =contentState.hint,
                  onValueChange = {
                      viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                  }, onFocusChange = {
                      viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                  },
                  isHintVisible = titleState.isHintVisible,
                  singleLine = true,
                  textStyle = MaterialTheme.typography.body1,
                  modifier = Modifier.fillMaxHeight()
              )
          }
    }
}
