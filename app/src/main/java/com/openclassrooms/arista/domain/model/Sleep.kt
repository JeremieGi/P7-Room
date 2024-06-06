package com.openclassrooms.arista.domain.model


import java.time.LocalDateTime

data class Sleep(
    @JvmField var startTime: LocalDateTime, // l'annotation @JvmField est utilisée en combinaison avec la déclaration d'une propriété dans une classe Kotlin pour indiquer au compilateur Kotlin de ne pas générer de getters ou de setters pour cette propriété lorsqu'elle est exposée à Java. Cela permet d'exposer la propriété en tant que champ Java public, ce qui simplifie l'accès à cette propriété depuis le code Java.
    var duration: Int,
    var quality: Int
)
