package dev.openfeature.contrib.providers.client

import dev.openfeature.sdk.MutableStructure
import dev.openfeature.sdk.Structure
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class AppliedFlag(
    val flag: String,
    @Contextual
    val applyTime: Instant
)

@Serializable
data class ResolveFlagsRequest(
    val flags: List<String>,
    @Contextual
    val evaluationContext: Structure,
    val clientSecret: String,
    val apply: Boolean
)

@Serializable
data class ResolveFlags(
    @Contextual
    val resolvedFlags: Flags,
    val resolveToken: String
)

data class Flags(
    val list: List<ResolvedFlag>
)

data class ResolvedFlag(
    val flag: String,
    val variant: String,
    val value: Structure = MutableStructure(mutableMapOf()),
    val reason: ResolveReason
)

sealed interface SchemaType {
    data class SchemaStruct(
        var schema: Map<String, SchemaType>
    ) : SchemaType
    object IntSchema : SchemaType
    object DoubleSchema : SchemaType
    object StringSchema : SchemaType
    object BoolSchema : SchemaType
}