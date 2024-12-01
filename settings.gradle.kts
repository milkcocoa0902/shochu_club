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
include("server:domain:model")
findProject(":server:domain:model")?.name = "model"
include("server:domain:repository")
findProject(":server:domain:repository")?.name = "repository"
include("server:domain:usecase")
findProject(":server:domain:usecase")?.name = "usecase"
include("server:domain:service")
findProject(":server:domain:service")?.name = "service"

include("server:infra")
findProject(":server:infra")?.name = "infra"
include("server:infra:database")
findProject(":server:infra:database")?.name = "database"
include("server:infra:di")
findProject(":server:infra:di")?.name = "di"
include("server:infra:firebase")
findProject(":server:infra:firebase")?.name = "firebase"

include("server:application")
findProject(":server:application")?.name = "application"
include("server:application:usecase")
findProject(":server:application:usecase")?.name = "usecase"
include("server:application:service")
findProject(":server:application:service")?.name = "service"
include("server:application:controller")
findProject(":server:application:controller")?.name = "controller"

include("server:presentation")
findProject(":server:presentation")?.name = "presentation"
include("server:presentation:routing")
findProject(":server:presentation:routing")?.name = "routing"
include("server:presentation:controller")
findProject(":server:presentation:controller")?.name = "controller"

include(":composeApp:domain")
include(":composeApp:domain:model")
include(":composeApp:app")
include("server:infra:mail")
findProject(":server:infra:mail")?.name = "mail"
include("server:infra:cache")
findProject(":server:infra:cache")?.name = "cache"
