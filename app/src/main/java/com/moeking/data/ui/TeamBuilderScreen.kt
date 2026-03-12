package com.moeking.data.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moeking.data.models.Character
import com.moeking.data.models.ElementType
import com.moeking.data.models.Team
import com.moeking.data.models.TeamRecommendation
import com.moeking.data.models.WeaponType
import com.moeking.data.utils.CharacterViewModel
import com.moeking.data.utils.TeamViewModel

/**
 * 队伍搭配屏幕
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamBuilderScreen(
    viewModel: CharacterViewModel = viewModel(),
    teamViewModel: TeamViewModel = viewModel(),
    navController: NavController
) {
    val characters by viewModel.characters.collectAsState()
    val teams by teamViewModel.teams.collectAsState()

    var currentTeam by remember { mutableStateOf<Team?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("队伍搭配") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "添加队伍")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 已有队伍列表
            Text(
                text = "我的队伍",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(teams) { team ->
                    TeamItem(
                        team = team,
                        onClick = { currentTeam = team },
                        onDelete = {
                            teamViewModel.deleteTeam(team)
                        }
                    )
                }
            }
        }
    }

    // 添加队伍对话框
    if (showAddDialog) {
        AddTeamDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, characters ->
                teamViewModel.createTeam(name, characters)
                showAddDialog = false
            }
        )
    }

    // 当前队伍编辑器
    currentTeam?.let { team ->
        TeamEditorDialog(
            team = team,
            characters = characters,
            onDismiss = { currentTeam = null },
            onSave = { updatedTeam ->
                teamViewModel.updateTeam(updatedTeam)
                currentTeam = null
            },
            onRemoveCharacter = { character ->
                val updated = team.copy(characters = team.characters.filter { it.id != character.id })
                teamViewModel.updateTeam(updated)
            }
        )
    }
}

/**
 * 队伍项
 */
@Composable
fun TeamItem(
    team: Team,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // 显示队伍元素
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    team.getUsedElements().take(4).forEach { element ->
                        Text(
                            text = element.displayName,
                            color = element.color,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            IconButton(onClick = { onDelete() }) {
                Icon(Icons.Default.Delete, contentDescription = "删除", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

/**
 * 添加队伍对话框
 */
@Composable
private fun AddTeamDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, List<Character>) -> Unit
) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加队伍") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("队伍名称") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, emptyList()) },
                enabled = name.isNotBlank()
            ) {
                Text("添加")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

/**
 * 队伍编辑器对话框
 */
@Composable
private fun TeamEditorDialog(
    team: Team,
    characters: List<Character>,
    onDismiss: () -> Unit,
    onSave: (Team) -> Unit,
    onRemoveCharacter: (Character) -> Unit
) {
    var teamName by remember { mutableStateOf(team.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("编辑队伍") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 队伍名称
                OutlinedTextField(
                    value = teamName,
                    onValueChange = { teamName = it },
                    label = { Text("队伍名称") },
                    singleLine = true
                )

                // 角色选择
                Text(
                    text = "选择角色 (${characters.size}/4)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(300.dp)
                ) {
                    items(characters) { character ->
                        CharacterItem(
                            character = character,
                            selected = team.characters.any { it.id == character.id },
                            onClick = {},
                            onRemoveClick = {
                                if (team.characters.any { it.id == character.id }) {
                                    onRemoveCharacter(character)
                                }
                            }
                        )
                    }
                }

                // 队伍信息
                if (team.characters.isNotEmpty()) {
                    Card {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "队伍信息",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text("角色数: ${team.getCharacterCount()}")
                            Text("总攻击: ${team.getTotalAtk()}")
                            Text("总生命: ${team.getTotalHp()}")
                            Text("元素: ${team.getUsedElements().joinToString { it.displayName }}")
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val updatedTeam = team.copy(name = teamName)
                    onSave(updatedTeam)
                },
                enabled = teamName.isNotBlank()
            ) {
                Text("保存")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}
