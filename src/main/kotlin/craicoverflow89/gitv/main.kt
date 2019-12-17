package craicoverflow89.gitv

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    // Invalid Arguments
    if(args.size != 1 || !args[0].matches("^(init|major|minor|patch)$".toRegex())) {
        println("Invalid arguments!")
        println(" gitv [init|major|minor|patch]")
        exitProcess(-1)
    }

    // File Paths
    val cwd = System.getProperty("user.dir")
    val versionFile = File("$cwd/version")

    // Initialise Version
    if(args[0] == "init") Version(0, 1, 0).let {
        // NOTE: could have optional starting value from input

        // Create File
        versionFile.writeText(it.toString())

        // Initialise Repository
        File("$cwd/.gitignore").writeText(".gitignore")
        with(Runtime.getRuntime()) {
            exec("git init")
            exec("git add version")
            exec("git commit -m \"Version ${it}\"")
        }

        // Echo Version
        println("Version is now $it")

        // Done
        exitProcess(0)
    }

    // Not Repository
    if(!File("$cwd/.git").exists()) {
        println("Current directory is not a git repository!")
        exitProcess(-1)
        // NOTE: could offer to do git init here
    }

    // Missing File
    if(!versionFile.exists()) {
        println("No version file found!")
        exitProcess(-1)
    }

    // Read File
    val versionData = versionFile.readText().let {

        // Corrupt Contents
        if(!it.matches("^[0-9]+\\.[0-9]+\\.[0-9]+$".toRegex())) {
            println("Corrupt version file!")
            exitProcess(-1)
            // NOTE: maybe offer to delete or overwrite (with specific version)?
        }

        // Parse Values
        val (major, minor, patch) = it.split(".").map {
            it.toInt()
        }

        // Return Version
        Version(major, minor, patch)
    }

    // Invoke Bump
    when(args[0]) {
        "major" -> versionData.bumpMajor()
        "minor" -> versionData.bumpMinor()
        "patch" -> versionData.bumpPatch()
        else -> {}
    }

    // Update File
    versionFile.writeText(versionData.toString())

    // Update Repository
    with(Runtime.getRuntime()) {
        exec("git add version")
        exec("git commit -m \"Version ${versionData}\"")
    }

    // Echo Version
    println("Version is now $versionData")

}

class Version(private var major: Int, private var minor: Int, private var patch: Int) {

    fun bumpMajor() {
        major += 1
        minor = 0
        patch = 0
    }

    fun bumpMinor() {
        minor += 1
        patch = 0
    }

    fun bumpPatch() {
        patch += 1
    }

    override fun toString() = listOf(major, minor, patch).joinToString(".")

}