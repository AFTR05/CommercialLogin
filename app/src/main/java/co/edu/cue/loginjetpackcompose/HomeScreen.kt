package co.edu.cue.loginjetpackcompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(email: String, navigateBack: () -> Unit) {
    val db = Firebase.firestore
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    val productosPrueba = listOf(
        Product(
            name = "Auriculares Bluetooth",
            price = 59.99,
            description = "Auriculares inalámbricos con cancelación de ruido y batería de larga duración",
            imageUrl = "https://www.steren.com.co/media/catalog/product/cache/295a12aacdcb0329a521cbf9876b29e7/image/22545a64c/audifonos-bluetooth-touch-true-wireless-con-active-noise-cancelling-y-enviromental-noise-cancelling.jpg"
        ),
        Product(
            name = "Laptop Dell XPS 13",
            price = 1299.99,
            description = "Ultrabook con pantalla InfinityEdge, procesador Intel i7 y 16GB RAM",
            imageUrl = "https://m.media-amazon.com/images/I/61d2qqdNq3L._AC_SL1001_.jpg"
        ),
        Product(
            name = "Smartwatch Samsung Galaxy Watch",
            price = 199.99,
            description = "Reloj inteligente con monitoreo de salud, GPS y resistencia al agua",
            imageUrl = "https://www.alkosto.com/medias/8806095660943-001-750Wx750H?context=bWFzdGVyfGltYWdlc3wyMTk2MnxpbWFnZS93ZWJwfGFHUmtMMmcyTUM4eE5EYzROak16TmpVNE56Z3dOaTg0T0RBMk1EazFOall3T1RRelh6QXdNVjgzTlRCWGVEYzFNRWd8NmNhY2I5ZTk3Mzg4ODM1NTAzNjNlNjA1NjZkMGRmOWU4MjI1OTllZmVlZTAyMjIyOGRmODg1MGM3MDU2OTEwNg"
        ),
        Product(
            name = "Teclado Mecánico Redragon",
            price = 89.90,
            description = "Teclado retroiluminado con switches rojos y diseño ergonómico",
            imageUrl = "https://m.media-amazon.com/images/I/71cngLX2xuL._AC_SL1500_.jpg"
        ),
        Product(
            name = "Silla Gamer Corsair",
            price = 250.00,
            description = "Silla ergonómica con cojín lumbar, reclinación total y materiales premium",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRk4QhpwOLedp7PFV3z_mTgw0X_9tVTXajpOQ&s"
        ),
        Product(
            name = "Monitor LG UltraWide 34\"",
            price = 499.99,
            description = "Monitor panorámico con resolución QHD y tasa de refresco de 144Hz",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUzfgxSrnIBsBgRfnAhG10HH-rAM4HjlQHRQ&s"
        ),
        Product(
            name = "Mouse Logitech MX Master 3",
            price = 99.99,
            description = "Ratón inalámbrico ergonómico con múltiples botones y carga rápida USB-C",
            imageUrl = "https://www.alkosto.com/medias/097855175359-001-750Wx750H?context=bWFzdGVyfGltYWdlc3wxNDQ4NnxpbWFnZS93ZWJwfGFHSm1MMmczWmk4eE5EUTNOREV6TlRZek16azFNQzh3T1RjNE5UVXhOelV6TlRsZk1EQXhYemMxTUZkNE56VXdTQXwyNjVjYjk1OGIzNDNkMzI0YTY5NjdkNGNmOThhZjgwYTRhYTE0OGVmNjBmZWRmNzhiYjAyN2M5ZGY5MWJlMDNj"
        ),
        Product(
            name = "Cámara Canon EOS M50",
            price = 649.00,
            description = "Cámara mirrorless 4K ideal para fotografía y video de alta calidad",
            imageUrl = "https://eoatecnologia.com/cdn/shop/files/1602634176_1598385.jpg?v=1689635615"
        ),
        Product(
            name = "iPad Pro 11\"",
            price = 799.99,
            description = "Tablet con chip M1, pantalla Liquid Retina y compatibilidad con Apple Pencil",
            imageUrl = "https://co.tiendasishop.com/cdn/shop/files/IMG-13190872_dc24e7b2-3ad6-4fe8-9ada-d86ecf9de6df.jpg?v=1723511794&width=1000"
        ),
        Product(
            name = "Disco Duro SSD Samsung 1TB",
            price = 129.99,
            description = "Unidad de estado sólido ultrarrápida con interfaz NVMe",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfnptC40fDsBANXsGx08XKJx0QA1IZh5cqhg&s"
        ),
    )

    LaunchedEffect(Unit) {
        db.collection("productos").get().addOnSuccessListener { result ->
            products = result.documents.mapNotNull { it.toObject(Product::class.java) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo - $email") },
                actions = {
                    Button(onClick = navigateBack) {
                        Text("Cerrar sesión")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(productosPrueba) { product ->
                ProductCard(product = product)
            }
        }
    }
}

data class Product(
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val imageUrl: String = ""
)

@Composable
fun ProductCard(product: Product) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (product.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Precio: $${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (expanded) product.description else product.description.take(60) + "...",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable { expanded = !expanded },
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
