package com.example.launcher.ui.common

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Snackbar(
    message: String,
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null
) {
    Snackbar(
        modifier = modifier,
        action = action,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun SnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = { snackbarData ->
            Snackbar(
                message = snackbarData.visuals.message,
                action = snackbarData.visuals.actionLabel?.let { actionLabel ->
                    {
                        TextButton(
                            onClick = { snackbarData.performAction() },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(actionLabel)
                        }
                    }
                }
            )
        }
    )
} 