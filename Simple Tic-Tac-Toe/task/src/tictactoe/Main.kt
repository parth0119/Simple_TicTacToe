package tictactoe

var isGameWon: Boolean = false
var isGameFinished: Boolean = false
var loopNeeded: Boolean = false
var countX: Int = 0
var countO: Int = 0
var spaces: Int = 0
var gameWonXCount: Int = 0
var gameWonOCount: Int = 0
var userInput: String = ""
var modifiedInput: MutableList<Char> = mutableListOf()
var coordinates: String = ""
var count = 0
var switchPlayer = false
var updatedInput = "         "

fun main() {

    getCellInput()
    modifiedInput = userInput.toMutableList()

    while (!isGameFinished) {
        countX = 0
        countO = 0
        spaces = 0
        getCoordinates()

        val rowOne: MutableList<Char>
        val rowTwo: MutableList<Char>
        val rowThree: MutableList<Char>
        var inputInMatrixForm: MutableList<MutableList<Char>> = mutableListOf()

        if (userInput.length != 9) {
            println("Invalid Input")
        } else {
            rowOne = updatedInput.substring(0, 3).toMutableList()
            rowTwo = updatedInput.substring(3, 6).toMutableList()
            rowThree = updatedInput.substring(6, 9).toMutableList()
            inputInMatrixForm = mutableListOf(rowOne, rowTwo, rowThree)
            for (i in updatedInput) {
                when (i) {
                    'X' -> {
                        countX++
                    }
                    'O' -> {
                        countO++
                    }
                    else -> {
                        spaces++
                    }
                }
            }
        }
        checkIfGameIsWon(inputInMatrixForm)
        checkGameState()
    }
}

fun getCoordinates() {
    count = 0
    loopNeeded = false
    print("Enter the coordinates: ")
    coordinates = readLine()!!.toString().split(" ").joinToString("")
    modifyCells()
    updatedInput = modifiedInput.joinToString("")
    printingBox(updatedInput)
}

fun modifyCells() {
    val coInInt = coordinates.toIntOrNull()
    if (coInInt == null) {
        println("You should enter numbers!")
        loopNeeded = true
    } else {
        if (coInInt == 11 || coInInt == 12 || coInInt == 13 ||
            coInInt == 21 || coInInt == 22 || coInInt == 23 ||
            coInInt == 31 || coInInt == 32 || coInInt == 33
        ) {
            outer@ for (i in 1..3) {
                inner@ for (j in 1..3) {
                    if (coordinates == "$i$j") {
                        if (updatedInput[count].isWhitespace()) {
                            if (switchPlayer) {
                                modifiedInput[count] = 'O'
                                switchPlayer = !switchPlayer
                            } else {
                                modifiedInput[count] = 'X'
                                switchPlayer = !switchPlayer
                            }
                            loopNeeded = true
                            break@outer
                        } else {
                            println("This cell is occupied! Choose another one!")
                            loopNeeded = true
                            break@outer
                        }
                    }
                    count++
                }
            }
        } else {
            println("Coordinates should be from 1 to 3!")
            loopNeeded = true
        }
    }
}

fun getCellInput() {
//    Initial input as 9 blanks
    userInput = "         "
    printingBox(userInput)
}

fun checkIfGameIsWon(inputInMatrixForm: MutableList<MutableList<Char>>) {
    var countXs = 0
    var countOs = 0
    var countBlanks = 0

    for (i in 0..2) {
        for (j in 0..2) {
            when (inputInMatrixForm[i][j]) {
                'X' -> countXs++
                'O' -> countOs++
                else -> countBlanks++
            }
            if (countXs == 3) {
                gameWonXCount++
            } else if (countOs == 3) {
                gameWonOCount++
            }
        }
        countXs = 0
        countOs = 0
        countBlanks = 0
    }

    for (i in 0..2) {
        for (j in 0..2) {
            when (inputInMatrixForm[j][i]) {
                'X' -> countXs++
                'O' -> countOs++
                else -> countBlanks++
            }
            if (countXs == 3) {
                gameWonXCount++
            } else if (countOs == 3) {
                gameWonOCount++
            }
        }
        countXs = 0
        countOs = 0
        countBlanks = 0
    }

    for (i in 0..2) {
        when (inputInMatrixForm[i][i]) {
            'X' -> countXs++
            'O' -> countOs++
            else -> countBlanks++
        }
        if (countXs == 3) {
            gameWonXCount++
        } else if (countOs == 3) {
            gameWonOCount++
        }
    }

    if (inputInMatrixForm[0][2] == inputInMatrixForm[1][1] && inputInMatrixForm[1][1] == inputInMatrixForm[2][0]) {
        when (inputInMatrixForm[0][2]) {
            'X' -> gameWonXCount++
            'O' -> gameWonOCount++
        }
    }
}

fun checkGameState() {
    if (gameWonXCount + gameWonOCount >= 1) {
        if (gameWonOCount == 1) {
            gameWon('O')
            isGameFinished = true
        } else if (gameWonXCount == 1) {
            gameWon('X')
            isGameFinished = true
        }
    } else if (!isGameWon && spaces == 0) {
        println("Draw")
        isGameFinished = true
    } else if (!isGameWon && spaces > 0) {
        isGameFinished = false
    }
}

fun gameWon(c: Char) {
    isGameWon = true
    println("$c wins")
}

fun printingBox(userInput: String) {
    var count = 0
    topLine()
    for (i in userInput) {
        if (count == 0) {
            columnLine()
        }
        if (count <= 3) {
            print("$i ")
        }
        count++
        if (count == 3) {
            columnLine()
            println()
            count = 0
        }
    }
    topLine()
}

fun columnLine() {
    print("| ")
}

fun topLine() {
    repeat(9) {
        print("-")
    }
    println()
}