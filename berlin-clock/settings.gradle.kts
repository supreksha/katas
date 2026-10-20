rootProject.name = "berlin-clock"
include("src:test")
findProject(":src:test")?.name = "test"
