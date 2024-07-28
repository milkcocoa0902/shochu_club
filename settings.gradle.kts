rootProject.name = "Shochu_Club"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":server")
include(":shared")
include("server:domain")
findProject(":server:domain")?.name = "domain"
include("server:service")
findProject(":server:service")?.name = "service"
include("server:domain:model")
findProject(":server:domain:model")?.name = "model"
include("server:domain:repository")
findProject(":server:domain:repository")?.name = "repository"
include("server:domain:usecase")
findProject(":server:domain:usecase")?.name = "usecase"
include("server:infra")
findProject(":server:infra")?.name = "infra"
include("server:infra:database")
findProject(":server:infra:database")?.name = "database"
include("server:infra:di")
findProject(":server:infra:di")?.name = "di"
include("server:application")
findProject(":server:application")?.name = "application"
include("server:application:presenter")
findProject(":server:application:presenter")?.name = "presenter"
include("server:application:controller")
findProject(":server:application:controller")?.name = "controller"
