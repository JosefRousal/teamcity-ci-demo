import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.pipelines.*

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2026.1"

project {

    pipeline(TeamcityCiDemo)
}


/*
Branch demo: this file is versioned per branch. With versioned settings set to
"use settings from the current branch", each branch's copy of this script
defines what TeamCity shows for that branch. Change BRANCH_MARKER (and the jobs)
on another branch to see the difference in the UI.
*/
const val BRANCH_MARKER = "claude/cool-mccarthy-o7nzc2"

object TeamcityCiDemo : Pipeline({
    name = "Teamcity Ci Demo"

    repositories {
        repository(DslContext.settingsRoot)
    }

    job(TeamcityCiDemo_Build)
    job(TeamcityCiDemo_Test) {
        dependsOn(TeamcityCiDemo_Build)
    }
    job(TeamcityCiDemo_BranchInfo) {
        dependsOn(TeamcityCiDemo_Test)
    }
})

object TeamcityCiDemo_Build : Job({
    id("Build")
    name = "Build"
    steps {
        script {
            name = "Build"
            scriptContent = "echo Building from settings on branch $BRANCH_MARKER"
        }
    }
})

object TeamcityCiDemo_Test : Job({
    id("Test")
    name = "Test"
    steps {
        script {
            name = "Test"
            scriptContent = "echo Running tests"
        }
    }
})

object TeamcityCiDemo_BranchInfo : Job({
    id("BranchInfo")
    name = "Branch Info"
    steps {
        script {
            name = "Print branch"
            scriptContent = "echo Branch: %teamcity.build.branch%"
        }
    }
})
