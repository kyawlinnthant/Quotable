import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

private val ktlint = libs.plugins.ktlint.get().pluginId
private val detekt = libs.plugins.detekt.get().pluginId
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.google.hilt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.secrets) apply false
}

buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin)
    }
}

subprojects {
    apply(plugin = ktlint)
    configure<KtlintExtension> {
        debug.set(true)
        verbose.set(true)
        android.set(true)
    }
    apply(plugin = detekt)
    configure<DetektExtension> {
        parallel = true
        allRules = true
        autoCorrect = true
        buildUponDefaultConfig = true
        config.setFrom(file("${rootProject.rootDir}/config/detekt/detekt.yml"))
    }
    dependencies {
        // todo : this is not working, version conflict with ktlint pinterest and compose.
        //  we should wait compose rule release version
//        "ktlintRuleset"(ruleKtlint)
//        "detektPlugins"(ruleDetekt)
    }
}
