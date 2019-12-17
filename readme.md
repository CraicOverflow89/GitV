Git Version Tool
================

Lightweight command line tool that handles versioning of git repositories.

### Installation

Add a script to your PATH, replacing _x.y.z_ with version.

```
# *nix shell script
java -jar /path/to/gitv-x.y.z.jar "$@"

# windows batch file
@java -jar /path/to/gitv-x.y.z.jar %*
```

### Usage

You can create a new git repository with version file using the **init** command (initial version is 0.1.0). This command also creates a `.gitignore` file for convenience.

```
$ gitv init
Version is now 0.1.0
```

You can increment **major** / **minor** / **patch** version like this;

```
$ gitv minor
Version is now 0.2.0

$ gitv major
Version is now 1.0.0

$ gitv patch
Version is now 1.0.1
```