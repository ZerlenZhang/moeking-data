package com.moeking.data.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moeking.data.ui.CharacterDetailScreen
import com.moeking.data.ui.CharacterListScreen
import com.moeking.data.ui.TeamBuilderScreen
import com.moeking.data.ui.ReactionDemoScreen

/**
 * 导航配置
 */
@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "characters"
    ) {
        // 角色列表
        composable("characters") {
            CharacterListScreen(
                onCharacterClick = { characterId ->
                    navController.navigate("character_detail/$characterId")
                },
                onNavigateToReactionDemo = {
                    navController.navigate("reaction_demo")
                }
            )
        }

        // 角色详情
        composable(
            route = "character_detail/{characterId}",
            arguments = listOf(
                navArgument("characterId") { type = androidx.navigation.NavType.StringType }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId") ?: ""
            CharacterDetailScreen(
                characterId = characterId,
                navController = navController
            )
        }

        // 队伍搭配
        composable("team_builder") {
            TeamBuilderScreen(
                navController = navController,
                onNavigateToReactionDemo = {
                    navController.navigate("reaction_demo")
                }
            )
        }

        // 元素反应演示 - 惊喜功能！🎉
        composable("reaction_demo") {
            ReactionDemoScreen(
                navController = navController
            )
        }
    }
}
