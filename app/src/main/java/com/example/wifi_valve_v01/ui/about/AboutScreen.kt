package com.example.wifi_valve_v01.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifi_valve_v01.R

@Composable
fun AboutScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vortexlog),
                    contentDescription = "Vortex Labs",
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(16f/9f),
                    contentScale = ContentScale.Fit
                )
            }
        }

        item {
            AboutSectionCard(
                title = "About Us",
                content = "Vortex Labs specializes in cutting-edge automation and AI solutions for agriculture and industry. Our flagship product, the Vortex Smart Valve, is an IoT-enabled valve with sensor integration capabilities. We also offer services in 3D printing, PCB manufacturing, CAD design, website creation and hosting, and custom vending machine development.",
                icon = Icons.Default.Info
            )
        }

        item {
            AboutSectionCard(
                title = "Our Mission",
                content = "Our mission is to uplift Sri Lanka's economy by advancing the local production industry through IoT and related technologies, reducing the need for imports.",
                icon = Icons.Default.Build
            )
        }

        item {
            AboutSectionCard(
                title = "Our Vision",
                content = "To become a leading force in technological innovation, empowering Sri Lanka and beyond through sustainable, locally-developed automation and AI solutions that drive self-reliance and industrial growth.",
                icon = Icons.Default.ThumbUp
            )
        }

        item {
            AboutSectionCard(
                title = "Our Values",
                content = "Innovation, Local empowerment, Technical excellence, Versatility, Sustainability, Integrity, Customer focus, Engineering-driven leadership.",
                icon = Icons.Default.Star
            )
        }
    }
}

@Composable
fun AboutSectionCard(
    title: String,
    content: String,
    icon: ImageVector? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    MaterialTheme {
        AboutScreen()
    }
}