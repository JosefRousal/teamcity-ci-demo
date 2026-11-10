# teamcity-ci-demo

Demo of TeamCity versioned settings (Kotlin DSL, pipelines) across branches.

## Layout
- `.teamcity/settings.kts` – pipeline definition: Build -> Test -> Branch Info
- `.teamcity/pom.xml` – DSL Maven project

## Multi-branch demo
In the TeamCity project: Settings > Versioned Settings, set
"Settings format" to Kotlin and "Use settings from VCS" to
**Use settings from the current branch (or default if absent)**.

Then on any branch, edit `.teamcity/settings.kts` (e.g. `BranchDemo.MARKER`, add or
remove a `job(...)`) and push. Each branch's build shows its own pipeline
definition in the UI.

```bash
git checkout -b feature/extra-job
# edit .teamcity/settings.kts, add a job
git commit -am "Add extra job on feature branch" && git push -u origin feature/extra-job
```
