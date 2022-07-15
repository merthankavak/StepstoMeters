import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelText: String,
    leadingIcons: ImageVector,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    onActions: KeyboardActions = KeyboardActions.Default,

    ) {
    OutlinedTextField(
        modifier = modifier.padding(10.dp),
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = {
            Text(
                text = labelText
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcons,
                contentDescription = "buttonIcon"
            )
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground
        ),
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onActions
    )
}