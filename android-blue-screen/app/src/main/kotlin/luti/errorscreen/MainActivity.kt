package luti.errorscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 启用边缘到边缘布局
        enableEdgeToEdge()
        
        // 获取 WindowInsetsController
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        
        // 1. 隐藏状态栏和导航栏
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        
        // 2. 设置行为
        windowInsetsController.systemBarsBehavior = 
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        
        // 3. 设置状态栏和导航栏背景为透明
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        
        setContent { 
            BlueScreenOfDeath()
        }
    }
}

@Composable
fun BlueScreenOfDeath() {
    // 进度状态
    var progress by remember { mutableIntStateOf(12) }
    // 是否闪退
    var isCrashing by remember { mutableStateOf(false) }
    
    // 启动进度更新协程
    LaunchedEffect(Unit) {
        while (progress < 99) {
            delay(300) // 0.03秒 = 30毫秒
            progress += 1
        }
        // 到达99%时触发闪退
        isCrashing = true
        delay(500) // 闪退前显示0.5秒
        android.os.Process.killProcess(android.os.Process.myPid())
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0078D4))
            .padding(32.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = ": (",
                fontSize = 96.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "你的手机遇到问题，需要重启。\n我们只展示某些错误信息，然后你可以重新启动。",
                fontSize = 18.sp,
                color = Color.White,
                lineHeight = 32.sp
            )

            // 动态进度显示
            Text(
                text = if (isCrashing) "99% 完成" else "$progress% 完成",
                fontSize = 24.sp,
                color = if (isCrashing) Color.Red else Color.White
            )

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .border(BorderStroke(2.dp, Color.White), RectangleShape)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "有关此问题的详细信息和可能的解决方法，请访问",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "https://molaos.rth1.xyz/niulai.html",
                        color = Color.White,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "如果询问技术人员，请向他们提供以下信息",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Crazy Thursday,vivo 50",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BlueScreenPreview() {
    BlueScreenOfDeath()
}